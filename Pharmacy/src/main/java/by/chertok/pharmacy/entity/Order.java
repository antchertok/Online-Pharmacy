package by.chertok.pharmacy.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order extends BaseEntity {

    private LocalDateTime orderDate;
    private long customerId;
    private Map<Long, Integer> drugs;
    private double total;

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

    public int getDrugsOrdered(){
        return drugs.size();
    }

    public Map<Long, Integer> getDrugs() {
        return drugs;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return customerId == order.customerId
                && total == order.total
                && orderDate == null ? order.orderDate == null
                                     : orderDate.equals(order.orderDate);
    }

    @Override
    public int hashCode() {

        return (int) (3 * getId() +  17 * customerId + 7 * orderDate.hashCode()
                + 3 * total);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + super.toString()
                + "orderDate=" + orderDate + ", customerId=" + customerId + "}";
    }
}
