package fi.muni.sqe.entity;

import java.util.Objects;

/**
 * Class, that represents Customer as his email and address
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class Customer {
    private String email;
    private String address;

    /**
     * @param email   customer email
     * @param address customer address
     */
    public Customer(String email, String address) {
        this.email = email;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Customer setAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(email, customer.email) &&
                Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, address);
    }
}
