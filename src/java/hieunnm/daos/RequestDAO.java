/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieunnm.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import hieunnm.dtos.ResourceDTO;
import hieunnm.object.Cart;
import hieunnm.util.MyConnection;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PC
 */
public class RequestDAO implements Serializable {

    private Connection conn = null;
    private PreparedStatement prStm = null;
    private ResultSet rs = null;

    private void closeConn() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (prStm != null) {
            prStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public boolean confirmRequest(Cart cart) throws SQLException, NamingException {
        try {
            conn = MyConnection.getMyConnection();
//            if (conn != null) {
//                String sql = "";
//                prStm = conn.prepareStatement(sql);
//                prStm.setString(1, sql);
//                rs = prStm.executeUpdate();
//            }
            conn.setAutoCommit(false);
            String sqlRequest = "INSERT INTO dbo.tblRequests(idUserRequest, "
                    + "description, status) "
                    + "VALUES(?, null, 'new')";
            prStm = conn.prepareStatement(sqlRequest, new String[]{"requestID"});
            prStm.setString(1, cart.getOwner().getId());
            int rowEffect = prStm.executeUpdate();
            if (rowEffect == 0) {
                conn.rollback();
                return false;
            }
            rs = prStm.getGeneratedKeys();
            int requestID = -1;
            if (rs.next()) {
                // get requestID
                requestID = rs.getInt(1);
            } else {
                conn.rollback();
                return false;
            }
            // add request detail
            String sqlRequestDetail = "INSERT INTO dbo.tblRequestDetails(resourceID, quantity, "
                    + "requestID) "
                    + "VALUES (?, ?, ?)";
            for (Map.Entry<String, ResourceDTO> resoure : cart.entrySet()) {
                prStm = conn.prepareStatement(sqlRequestDetail);

                prStm.setNString(1, resoure.getValue().getId());
                prStm.setInt(2, resoure.getValue().getQuantity());
                prStm.setInt(3, requestID);
                rowEffect = prStm.executeUpdate();

                if (rowEffect == 0) {
                    conn.rollback();
                    return false;
                }
            }

            String sqlDecreaseQuantity = "UPDATE dbo.tblResources SET quantity = quantity - ? WHERE resourceID = ?";
            for (Map.Entry<String, ResourceDTO> resoure : cart.entrySet()) {
                prStm = conn.prepareStatement(sqlDecreaseQuantity);
                prStm.setInt(1, resoure.getValue().getQuantity());
                prStm.setNString(2, resoure.getKey());
                rowEffect = prStm.executeUpdate();
                if (rowEffect == 0) {
                    conn.rollback();
                    return false;
                }
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn();
        }

        return true;
    }

    public Map<String, ResourceDTO> checkQuantity(Cart cart) throws NamingException, SQLException {
        Map<String, ResourceDTO> invalidList = new HashMap();
        try {
            conn = MyConnection.getMyConnection();
            String sql = "SELECT quantity FROM dbo.tblResources WHERE resourceID = ? AND isDeleted = 0";
            for (Map.Entry<String, ResourceDTO> resoure : cart.entrySet()) {
                prStm = conn.prepareStatement(sql);
                prStm.setNString(1, resoure.getKey());
                rs = prStm.executeQuery();
                if (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    if (quantity < resoure.getValue().getQuantity()) {
                        ResourceDTO resource = new ResourceDTO();
                        resource.setId(resoure.getKey());
                        resource.setName(resoure.getValue().getName());
                        resource.setQuantity(quantity);
                        invalidList.put(resoure.getKey(), resource);
                    }
                } else {
                    ResourceDTO resource = new ResourceDTO();
                    resource.setId(resoure.getKey());
                    resource.setName(resoure.getValue().getName());
                    resource.setQuantity(-1);
                    invalidList.put(resoure.getKey(), resource);
                }
            }
        } finally {
            closeConn();
        }
        return invalidList;
    }
}
