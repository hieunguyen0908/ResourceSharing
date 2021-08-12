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
import javax.naming.NamingException;
import hieunnm.dtos.RoleDTO;
import hieunnm.dtos.UserDTO;
import hieunnm.util.MyConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

/**
 *
 * @author PC
 */
public class UserDAO implements Serializable {

    private Connection conn = null;
    private PreparedStatement prStm = null;
    private ResultSet rs = null;

    private void closeConn() {
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

    public UserDTO checkLogin(String id, String password) throws NamingException, SQLException, NoSuchAlgorithmException {
        UserDTO user = null;
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "SELECT u.userID, u.userName, r.roleName AS roleName FROM dbo.tblUsers AS u JOIN dbo.tblRoles AS r ON r.roleID = u.roleID "
                        + "WHERE u.userID = ? AND u.password = ? AND u.status = 'active'";
//                        + "WHERE u.userID='" + id + "' AND u.password='" + password + "' AND u.status='active'";
                prStm = conn.prepareStatement(sql);
                prStm.setString(1, id);
                prStm.setNString(2, password);
                rs = prStm.executeQuery();
                if (rs.next()) {
                    user = new UserDTO();
                    user.setId(rs.getString("userID"));
                    user.setFullName(rs.getString("userName"));
                    user.setRole(new RoleDTO(rs.getString("roleName")));
                }
            }
        } finally {
            closeConn();
        }
        return user;
    }

    public boolean checkDuplicate(String userID) throws SQLException {
        boolean check = false;
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "Select userID "
                        + " FROM tblUsers "
                        + " Where userID=?";
                prStm = conn.prepareStatement(sql);
                prStm.setString(1, userID.toLowerCase());
                rs = prStm.executeQuery();
                if (rs.next()) {
                    check = true;
                }
            }
        } catch (Exception e) {

        } finally {
            closeConn();
        }
        return check;
    }

    public void insertNew(UserDTO user) throws SQLException, ClassNotFoundException, NamingException, NoSuchAlgorithmException {

        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblUsers(userID, userName, password, roleID, phone, address, "
                        + "createDate, verifyCode, status, email) "
                        + " VALUES(?,?,?,?,?,?,?,?,?,?)"; // Default roleID = 3 <--> Employee
                prStm = conn.prepareStatement(sql);
                prStm.setString(1, user.getId().toLowerCase());
                prStm.setNString(2, user.getFullName());

                // MD5 hashing password
                String passwordToHash = user.getPassword(); // default password
                String generatedPassword = null; // password before hashing
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(passwordToHash.getBytes());
                byte[] bytes = md.digest();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
                prStm.setNString(3, generatedPassword);
                // MD5 hashing password

                //prStm.setNString(3, user.getPassword());
                prStm.setInt(4, user.getRoleID());
                prStm.setString(5, user.getPhone());
                prStm.setString(6, user.getAddress());
                prStm.setDate(7, (Date) user.getCreateDate());
                prStm.setInt(8, user.getVerifyCode());
                prStm.setString(9, user.getStatus());
                prStm.setString(10, user.getEmail());

                prStm.executeUpdate();
            }
        } finally {
            closeConn();
        }
    }
    
    public void setAccountActive(String userID) throws SQLException {
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "update tblUsers set status = ? where userID = ?";
                prStm = conn.prepareStatement(sql);
                prStm.setString(1, "active");
                prStm.setString(2, userID);
                prStm.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn();
        }
    }
}
