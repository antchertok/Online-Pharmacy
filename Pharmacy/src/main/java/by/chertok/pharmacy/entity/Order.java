package by.chertok.pharmacy.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order extends BaseEntity {

    private LocalDateTime orderDate;
    private long pharmacistId;
    private long customerId;
    private Map<Long, Integer> drugs;

    public Order(long id) {
        super(id);
        drugs = new HashMap<>();
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public long getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(long pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public void addDrug(Long drugId, Integer amount) {
        drugs.put(drugId, amount);
    }

    public void clearOrder() {
        drugs.clear();
    }

    public Map<Long, Integer> getDrugs() {
        return drugs;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return pharmacistId == order.pharmacistId &&
                customerId == order.customerId &&
                orderDate == null ? order.orderDate == null : orderDate.equals(order.orderDate);
    }

    @Override
    public int hashCode() {

        return (int) (3 * getId() + 13 * pharmacistId + 17 * customerId + 7 * orderDate.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                super.toString() +
                "orderDate=" + orderDate +
                ", pharmacistId=" + pharmacistId +
                ", customerId=" + customerId +
                '}';
    }
}
