/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieunnm.dtos;

import java.io.Serializable;

/**
 *
 * @author PC
 */
public class RequestDetailDTO implements Serializable {

    private RequestDTO request;
    private ResourceDTO resource;
    private int quantity;

    public RequestDetailDTO() {
    }

    public RequestDetailDTO(RequestDTO request, ResourceDTO resource, int quantity) {
        this.request = request;
        this.resource = resource;
        this.quantity = quantity;
    }

    public RequestDTO getRequest() {
        return request;
    }

    public void setRequest(RequestDTO request) {
        this.request = request;
    }

    public ResourceDTO getResource() {
        return resource;
    }

    public void setResource(ResourceDTO resource) {
        this.resource = resource;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
