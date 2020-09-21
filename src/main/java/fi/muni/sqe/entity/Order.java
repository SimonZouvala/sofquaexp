package fi.muni.sqe.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Class that represents Order as id, date, {@link Customer}, total price and order status.
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class Order {
    private Long id;
    private LocalDate date;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private Customer customer;

    /**
     * @param id         order id
     * @param date       order date
     * @param totalPrice order total price
     * @param status     order status
     * @param customer   the customer who placed the order
     */
    public Order(Long id, LocalDate date, BigDecimal totalPrice, OrderStatus status, Customer customer) {
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.status = status;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public Order setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Order setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Order setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Order setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Order setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(date, order.date) &&
                status == order.status &&
                Objects.equals(customer, order.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, status, customer);
    }
}
