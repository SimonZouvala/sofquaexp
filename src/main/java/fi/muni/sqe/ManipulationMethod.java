package fi.muni.sqe;

/**
 * Enum that represents manipulation methods, that can be use.
 * <p>
 * <p>
 * EMPTY_CUSTOMER: methods for removing items with empty customer’s email or empty customer’s address.
 * </p>
 * <p>
 * AVERAGE: the average price of orders per year (separately for paid and unpaid orders).
 * </p>
 * <p>
 * TOTAL: the total price of orders per year (only paid ones).
 * </p>
 * <p>
 * TOP3: the top 3 customers with the highest number of orders.
 * </p>
 * </p>
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public enum ManipulationMethod {
    EMPTY_CUSTOMER, AVERAGE, TOTAL, TOP3
}
