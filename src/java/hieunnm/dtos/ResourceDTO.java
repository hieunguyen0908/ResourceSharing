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
public class ResourceDTO implements Serializable {

    private String id;
    private String name;
    private Date usingDate;
    private CategoryDTO category;
    private int quantity;
    private boolean isDeleted;
    private String color;

    public ResourceDTO() {
    }

    public ResourceDTO(String id, String name, Date usingDate, CategoryDTO category, int quantity, boolean isDeleted, String color) {
        this.id = id;
        this.name = name;
        this.usingDate = usingDate;
        this.category = category;
        this.quantity = quantity;
        this.isDeleted = isDeleted;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUsingDate() {
        return usingDate;
    }

    public void setUsingDate(Date usingDate) {
        this.usingDate = usingDate;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    
}
