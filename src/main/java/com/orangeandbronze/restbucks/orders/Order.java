package com.orangeandbronze.restbucks.orders;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "DRINK_ORDER")
public class Order {
    private @Id @GeneratedValue Long id;
    private Status status;
    private String productId;
    private String quantity;
    private String total;
    private String orderedDate;

    Order() {}

    public Order(Status status, String productId, String quantity, String total, String orderedDate) {
        this.status = status;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
        this.orderedDate = orderedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && status == order.status && Objects.equals(productId, order.productId) && Objects.equals(quantity, order.quantity) && Objects.equals(total, order.total) && Objects.equals(orderedDate, order.orderedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, productId, quantity, total, orderedDate);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", productId='" + productId + '\'' +
                ", quantity='" + quantity + '\'' +
                ", total='" + total + '\'' +
                ", orderedDate='" + orderedDate + '\'' +
                '}';
    }
}
