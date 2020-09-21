package fi.muni.sqe.entity;

import java.util.Objects;

/**
 * Class, that represents year for the result, {@link OrderStatus} for the result and result.
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class Result {
    private int year;
    private OrderStatus orderStatus;
    private String result;

    /**
     * @param year        year for the result
     * @param orderStatus order status for results (if result was count for paid or unpaid or all orders)
     * @param result      result that can be number or string
     */
    public Result(int year, OrderStatus orderStatus, String result) {
        this.year = year;
        this.orderStatus = orderStatus;
        this.result = result;
    }

    public int getYear() {
        return year;
    }

    public Result setYear(int year) {
        this.year = year;
        return this;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Result setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getResult() {
        return result;
    }

    public Result setResult(String result) {
        this.result = result;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result1 = (Result) o;
        return year == result1.year &&
                orderStatus == result1.orderStatus &&
                Objects.equals(result, result1.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, orderStatus, result);
    }
}
