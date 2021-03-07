package com.lxtyp.basic.generator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Order implements Serializable {
    private String ORDER_ID;

    private String USER_ID;

    private String STATUS;

    private BigDecimal ORDER_PRICE;

    private String PAY_TYPE;

    private String ORDER_ADDRESS;

    private BigDecimal ORDER_DISCOUNT;

    private BigDecimal ORDER_PAY;

    private Date CREATE_TIME;

    private Date UPDATE_TIME;

    private static final long serialVersionUID = 1L;

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public void setORDER_ID(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID == null ? null : ORDER_ID.trim();
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID == null ? null : USER_ID.trim();
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS == null ? null : STATUS.trim();
    }

    public BigDecimal getORDER_PRICE() {
        return ORDER_PRICE;
    }

    public void setORDER_PRICE(BigDecimal ORDER_PRICE) {
        this.ORDER_PRICE = ORDER_PRICE;
    }

    public String getPAY_TYPE() {
        return PAY_TYPE;
    }

    public void setPAY_TYPE(String PAY_TYPE) {
        this.PAY_TYPE = PAY_TYPE == null ? null : PAY_TYPE.trim();
    }

    public String getORDER_ADDRESS() {
        return ORDER_ADDRESS;
    }

    public void setORDER_ADDRESS(String ORDER_ADDRESS) {
        this.ORDER_ADDRESS = ORDER_ADDRESS == null ? null : ORDER_ADDRESS.trim();
    }

    public BigDecimal getORDER_DISCOUNT() {
        return ORDER_DISCOUNT;
    }

    public void setORDER_DISCOUNT(BigDecimal ORDER_DISCOUNT) {
        this.ORDER_DISCOUNT = ORDER_DISCOUNT;
    }

    public BigDecimal getORDER_PAY() {
        return ORDER_PAY;
    }

    public void setORDER_PAY(BigDecimal ORDER_PAY) {
        this.ORDER_PAY = ORDER_PAY;
    }

    public Date getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(Date CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public Date getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(Date UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ORDER_ID=").append(ORDER_ID);
        sb.append(", USER_ID=").append(USER_ID);
        sb.append(", STATUS=").append(STATUS);
        sb.append(", ORDER_PRICE=").append(ORDER_PRICE);
        sb.append(", PAY_TYPE=").append(PAY_TYPE);
        sb.append(", ORDER_ADDRESS=").append(ORDER_ADDRESS);
        sb.append(", ORDER_DISCOUNT=").append(ORDER_DISCOUNT);
        sb.append(", ORDER_PAY=").append(ORDER_PAY);
        sb.append(", CREATE_TIME=").append(CREATE_TIME);
        sb.append(", UPDATE_TIME=").append(UPDATE_TIME);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}