/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieunnm.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author PC
 */
public class UserDTO implements Serializable {

    private String id;
    private String password;
    private String fullName;
    private String phone;
    private String address;
    private Date createDate;
    private int verifyCode;
    private String email;
    private RoleDTO role;
//    private boolean isDeleted;
    private String status;
    private int roleID;

    public UserDTO() {
    }

    public UserDTO(String email, int roleID) {
        this.email = email;
        this.roleID = roleID;
    }





    public UserDTO(String id, String password, String fullName, String phone, String address, Date createDate, int verifyCode, String email, RoleDTO role, String status) {
        this.id = id;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.createDate = createDate;
        this.verifyCode = verifyCode;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public UserDTO(String id, String password, String fullName, String phone, String address,
            Date createDate, int verifyCode, String email, String status, int roleID) {
        this.id = id;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.createDate = createDate;
        this.verifyCode = verifyCode;
        this.email = email;
        this.status = status;
        this.roleID = roleID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

}
