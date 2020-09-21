package fi.muni.sqe;

import fi.muni.sqe.data.DataFromCSV;
import fi.muni.sqe.data.DataParse;
import fi.muni.sqe.entity.Customer;
import fi.muni.sqe.entity.DataToPrint;
import fi.muni.sqe.entity.Order;
import fi.muni.sqe.entity.OrderStatus;
import fi.muni.sqe.entity.Result;
import fi.muni.sqe.exceptions.DatabaseException;
import fi.muni.sqe.output.OutputCSV;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Class for manipulation with {@link Order}s and do filtering and analysis methods.
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

class Manipulation {
    private Collection<Order> orders;

    private Collection<DataToPrint> dataToPrints;
    private final File file;

    /**
     * Constructor for manipulation with orders
     *
     * @param file to load data and save data after filtering methods, if is remote file will be create file in
     *             directory outpuResults for local will be rewrite
     */
    Manipulation(String file) throws DatabaseException {
        DataParse dataParse = new DataFromCSV(file);
        this.file = getLocalFileIfInputIsRemote(file);
        this.orders = dataParse.prepareData();
        dataToPrints = new ArrayList<>();
    }

    private File getLocalFileIfInputIsRemote(String file) {
        if (file.contentEquals("http")) {
            String[] localFileName = file.split("/");
            return new File("outputResults/" + localFileName[localFileName.length - 1]);
        }
        return new File(file);

    }

    /**
     * Method that do every commands that was written in command line
     *
     * @param methodsString commands that was written in command line
     * @return collection of {@link DataToPrint} to print to output file
     * @throws DatabaseException if problem with save data after filtering methods
     */
    Collection<DataToPrint> doMethods(List<String> methodsString) throws DatabaseException {
        List<String[]> listOfMethodsAndYears = splitStringToManipulationMethodAndYears(methodsString);
        ManipulationMethod method;

        for (String[] methodAndYear : listOfMethodsAndYears) {
            List<Integer> years = getYearsFromMethodAndYear(methodAndYear);

            try {
                method = ManipulationMethod.valueOf(methodAndYear[0].toUpperCase());
                addDataToPrint(method, years);
            } catch (IllegalArgumentException e) {
                throw new DatabaseException("This is a misspelled method: " + methodAndYear[0]);
            }


        }
        return dataToPrints;
    }

    /**
     * Method that split string to {@link ManipulationMethod} and years.
     *
     * <p>
     * for example:
     * <p>
     * "total:2018,2019"
     * <p> will be split to [total] and [2018,2019]</p>
     * </p></p>
     *
     * @param methodsString string that is given from command line
     * @return method and years
     */
    private List<String[]> splitStringToManipulationMethodAndYears(List<String> methodsString) {
        List<String[]> listOfStrings = new ArrayList<>();

        for (String string : methodsString) {
            listOfStrings.add(string.split(":"));
        }

        return listOfStrings;

    }

    private List<Integer> getYearsFromMethodAndYear(String[] methodAndYear) {
        List<Integer> years;
        if (methodAndYear.length > 1) {
            years = Arrays.stream(methodAndYear[1].split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } else {
            years = getYears();
        }
        return years;
    }

    /**
     * Method that get all years from orders. It is used if in command line are not specified years
     *
     * @return list of years that are present in orders
     */
    private List<Integer> getYears() {
        int minYear = orders.stream().min(Comparator.comparing(Order::getDate)).orElseThrow(NoSuchElementException::new).getDate().getYear();
        int maxYear = orders.stream().max(Comparator.comparing(Order::getDate)).orElseThrow(NoSuchElementException::new).getDate().getYear();
        List<Integer> years = new ArrayList<>();

        for (int i = 0; i < ((maxYear - minYear) + 1); i++) {
            years.add(minYear + i);
        }

        return years;
    }

    private void addDataToPrint(ManipulationMethod method, List<Integer> years) throws DatabaseException {
        switch (method) {
            case EMPTY_CUSTOMER:
                removeEmptyItems();
                break;
            case AVERAGE:
                dataToPrints.add(new DataToPrint(method, years, getAveragePriceOfOrders(years)));
                break;
            case TOTAL:
                dataToPrints.add(new DataToPrint(method, years, getTotalPriceOfOrders(years)));
                break;
            case TOP3:
                dataToPrints.add(new DataToPrint(method, years, getTop3()));
                break;
            default:
                System.out.println("This is a misspelled method in switch: " + method.toString());
        }

    }


    /**
     * Method for removing items with empty customer’s email or empty customer’s address.
     *
     * @throws DatabaseException if problem with save data
     */
    private void removeEmptyItems() throws DatabaseException {
        Collection<Order> newOrders = new ArrayList<>();
        for (Order order : orders) {
            if (isNotEmptyCustomer(order.getCustomer())) {
                newOrders.add(order);
            }
        }
        orders.clear();
        orders.addAll(newOrders);
        new OutputCSV(file, orders).saveResults();

    }

    /**
     * Method that controls if {@link Customer} have email or address empty or not
     *
     * @param customer the {@link Customer} we want to check
     * @return true when both data are present, false when some missing
     */
    private boolean isNotEmptyCustomer(Customer customer) {
        if (customer.getEmail() == null || customer.getEmail().equals("")) {
            return false;
        }
        if (customer.getAddress() == null || customer.getAddress().equals("")) {
            return false;
        }
        return true;

    }

    /**
     * Method that get average price of orders per year (separately for paid and unpaid orders)
     *
     * @param years list of years that are required
     * @return list of {@link Result} with average prices
     */
    private List<Result> getAveragePriceOfOrders(List<Integer> years) {
        List<Result> results = new ArrayList<>();
        long countPaidOrder = orders.stream().filter(order -> order.getStatus().equals(OrderStatus.PAID)).count();
        long countUnpaidOrder = orders.size() - countPaidOrder;

        for (int year : years) {
            results.add(getResultByYear(year, OrderStatus.PAID, countPaidOrder));
            results.add(getResultByYear(year, OrderStatus.UNPAID, countUnpaidOrder));
        }

        return results;
    }

    private Result getResultByYear(int year, OrderStatus status, long countOrder) {
        return new Result(year, status,
                getTotalPriceOfOrders(status, year)
                        .divide(BigDecimal.valueOf(countOrder), RoundingMode.CEILING).toString());
    }

    /**
     * Method for get total prices of orders per years.
     *
     * @param years list of years that are required
     * @return list of {@link Result} with total prices
     */
    private List<Result> getTotalPriceOfOrders(List<Integer> years) {
        List<Result> results = new ArrayList<>();

        for (int year : years) {
            results.add(new Result(year, OrderStatus.PAID, getTotalPriceOfOrders(OrderStatus.PAID, year).toString()));
        }

        return results;
    }

    /**
     * Method for get total price of orders per year.
     *
     * @param orderStatus status if count paid or unpaid orders
     * @param year        year that are required
     * @return total price of orders per year
     */
    private BigDecimal getTotalPriceOfOrders(OrderStatus orderStatus, int year) {
        BigDecimal sumPrice = new BigDecimal(0);

        for (Order order : orders) {
            if ((order.getStatus() == orderStatus) && (order.getDate().getYear() == year)) {
                sumPrice = sumPrice.add(order.getTotalPrice());
            }
        }

        return sumPrice;
    }

    /**
     * Method for get top 3 customers with the highest number of orders.
     *
     * @return list of {@link Result}s with top 3 customers
     */
    private List<Result> getTop3() {
        List<String> listOfCustomer = getListOfCustomer();

        List<String> setOfCustomer = new ArrayList<>(new HashSet<>(listOfCustomer));

        List<Integer> listOfCustomerPresence = getListOfCustomersPresence(listOfCustomer, setOfCustomer);

        StringBuilder customersToString = getCustomersToString(listOfCustomerPresence, listOfCustomer);

        List<Result> results = new ArrayList<>();
        results.add(new Result(LocalDate.now().getYear(), OrderStatus.ALL, customersToString.toString()));
        return results;
    }

    private List<String> getListOfCustomer() {
        List<String> customers = new ArrayList<>();
        for (Order order : orders) {
            customers.add(order.getCustomer().getEmail());
        }
        Collections.sort(customers);
        return customers;

    }

    private List<Integer> getListOfCustomersPresence(List<String> listOfCustomer, List<String> setOfCustomer) {

        List<Integer> listOfCustomerPresence = new ArrayList<>();
        int count;

        for (String customerFromSet : setOfCustomer) {
            count = 0;

            for (String customerFromList : listOfCustomer) {
                if (customerFromList.equals(customerFromSet)) {
                    count++;
                }
            }

            listOfCustomerPresence.add(count);
        }
        return listOfCustomerPresence;
    }

    private StringBuilder getCustomersToString(List<Integer> listOfCustomerPresence, List<String> listOfCustomer) {
        StringBuilder customersToString = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int max = listOfCustomerPresence.get(0);

            for (Integer aListOfCustomerPresence : listOfCustomerPresence) {
                if (aListOfCustomerPresence > max) {
                    max = aListOfCustomerPresence;
                }
            }

            int index = listOfCustomerPresence.indexOf(max);
            customersToString.append(listOfCustomer.get(index)).append(", ");
            listOfCustomer.remove(index);
            listOfCustomerPresence.remove(index);
        }
        return customersToString;

    }


}
