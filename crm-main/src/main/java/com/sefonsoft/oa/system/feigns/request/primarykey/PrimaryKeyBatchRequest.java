package com.sefonsoft.oa.system.feigns.request.primarykey;

/**
 * @author Aron
 * @version 1.0.0
 */
public class PrimaryKeyBatchRequest {
    private Integer quantity = 10;

    public PrimaryKeyBatchRequest() {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
