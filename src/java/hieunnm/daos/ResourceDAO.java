/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieunnm.daos;

import hieunnm.dtos.CategoryDTO;
import hieunnm.dtos.ResourceDTO;
import hieunnm.util.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author PC
 */
public class ResourceDAO implements Serializable {

    private Connection conn = null;
    private PreparedStatement prStm = null;
    private ResultSet rs = null;

    private void closeConn() throws SQLException {
        try {

            if (rs != null) {
                rs.close();
            }
            if (prStm != null) {
                prStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
        }
    }
    
    public List<ResourceDTO> search(String name, String category, String usingDate, int paging, int pageSize) throws SQLException, NamingException {
        List<ResourceDTO> listResource = new ArrayList<>();
        try {
            conn = MyConnection.getMyConnection();
            //search resource by name (category = all)
            String sql = "SELECT r.resourceID , r.resourceName ,r.usingDate ,r.categoryID ,r.quantity, r.color, c.categoryName AS categoryName FROM dbo.tblResources AS r JOIN dbo.tblCategories AS c ON c.categoryID = r.categoryID "
                    + "WHERE (r.resourceName LIKE ? COLLATE Latin1_General_CI_AI) AND r.isDeleted = 0 AND (? IS NULL OR c.categoryName = ?) AND (? IS NULL OR r.usingDate = ?) AND r.quantity > 0 "
                    + "ORDER BY r.resourceName OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY OPTION (RECOMPILE)";
            prStm = conn.prepareStatement(sql);
            prStm.setNString(1, "%" + name + "%");
            prStm.setObject(2, category, Types.NVARCHAR);
            prStm.setObject(3, category, Types.NVARCHAR);
            prStm.setString(4, usingDate);
            prStm.setString(5, usingDate);
            prStm.setInt(6, paging);
            prStm.setInt(7, pageSize);
            rs = prStm.executeQuery();
            while (rs.next()) {
                ResourceDTO resource = new ResourceDTO();
                resource.setId(rs.getNString("resourceID"));
                resource.setName(rs.getNString("resourceName"));
                resource.setUsingDate(rs.getDate("usingDate"));
                resource.setCategory(new CategoryDTO(rs.getNString("categoryName")));
                resource.setQuantity(rs.getInt("quantity"));
                resource.setColor(rs.getString("color"));
                listResource.add(resource);

            }
        } finally {
            closeConn();
        }
        return listResource;
    }
    
    public List<ResourceDTO> search(String name, String category, String usingDate) throws SQLException, NamingException {
        List<ResourceDTO> listResource = new ArrayList<>();
        try {
            conn = MyConnection.getMyConnection();
            //search resource by name (category = all)
            String sql = "SELECT r.resourceID , r.resourceName ,r.usingDate ,r.categoryID ,r.quantity, r.color, c.categoryName AS categoryName FROM dbo.tblResources AS r JOIN dbo.tblCategories AS c ON c.categoryID = r.categoryID "
                    + "WHERE (r.resourceName LIKE ? COLLATE Latin1_General_CI_AI) AND r.isDeleted = 0 AND (? IS NULL OR c.categoryName = ?) AND (? IS NULL OR r.usingDate = ?) AND r.quantity > 0 ORDER BY r.resourceName";
            prStm = conn.prepareStatement(sql);
            prStm.setNString(1, "%" + name + "%");
            prStm.setObject(2, category, Types.NVARCHAR);
            prStm.setObject(3, category, Types.NVARCHAR);
            prStm.setString(4, usingDate);
            prStm.setString(5, usingDate);
            rs = prStm.executeQuery();
            while (rs.next()) {
                ResourceDTO resource = new ResourceDTO();
                resource.setId(rs.getNString("resourceID"));
                resource.setName(rs.getNString("resourceName"));
                resource.setUsingDate(rs.getDate("usingDate"));
                resource.setCategory(new CategoryDTO(rs.getNString("categoryName")));
                resource.setQuantity(rs.getInt("quantity"));
                resource.setColor(rs.getString("color"));
                listResource.add(resource);

            }
        } finally {
            closeConn();
        }
        return listResource;
    }
    
    public int countResource() throws SQLException, NamingException {
        int count = 0;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "SELECT COUNT(resourceID) as count FROM tblResources";
            prStm = conn.prepareStatement(sql);
            rs = prStm.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } finally {
            closeConn();
        }
        return count;
    }
    

}
