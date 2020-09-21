package fi.muni.sqe.output;

import fi.muni.sqe.entity.Order;
import fi.muni.sqe.exceptions.DatabaseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * Implementation interface {@link Output} for save {@link Order}s to CSV file after filtering method
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class OutputCSV implements Output {

    private final File file;
    private final Collection<Order> data;


    public OutputCSV(File file, Collection<?> data) {
        this.file = file;
        this.data = (Collection<Order>) data;
    }

    @Override
    public void saveResults() throws DatabaseException {

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file, false))) {

            StringBuilder writeLine = writeResults();

            writer.write(String.valueOf(writeLine));
            writer.flush();
            System.out.println("Rewrite to " + file.toString());

        } catch (IOException e) {
            throw new DatabaseException("Error with save results to CSV file.", e);
        }
    }

    private StringBuilder writeResults() {

        StringBuilder writeLine = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");

        for (Order order : data) {
            writeLine.append(order.getId()).append(",")
                    .append(order.getDate().format(formatter)).append(",")
                    .append(order.getCustomer().getEmail()).append(",")
                    .append(order.getCustomer().getAddress()).append(",")
                    .append(order.getTotalPrice()).append(",")
                    .append(order.getStatus())
                    .append(System.lineSeparator());
        }
        return writeLine;
    }
}



