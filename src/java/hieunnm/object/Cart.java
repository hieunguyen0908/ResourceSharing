/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieunnm.object;

import hieunnm.daos.RequestDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import hieunnm.dtos.ResourceDTO;
import hieunnm.dtos.UserDTO;

/**
 *
 * @author PC
 */
public class Cart extends HashMap<String, ResourceDTO> implements Serializable {

    private UserDTO owner;

    public Cart() {
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public boolean confirm() throws SQLException, NamingException {
        RequestDAO dao = new RequestDAO();
        if (dao.confirmRequest(this)) {
            this.clear();
            return true;
        }
        return false;
    }

    public Map<String, ResourceDTO> checkQuantity() throws NamingException, SQLException {
        RequestDAO dao = new RequestDAO();
        return dao.checkQuantity(this);
    }
}
