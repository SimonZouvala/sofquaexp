package fi.muni.sqe.data;

import fi.muni.sqe.entity.Customer;
import fi.muni.sqe.entity.Order;
import fi.muni.sqe.entity.OrderStatus;
import fi.muni.sqe.exceptions.DatabaseException;
import fi.muni.sqe.exceptions.FileTypeException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implementation interface {@link DataParse} for read CSV file
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class DataFromCSV implements DataParse {
    private String fileName;

    /**
     * Constructor for get data from CSV file
     *
     * @param fileName path to file
     */
    public DataFromCSV(String fileName) {
        this.fileName = fileName;

    }

    @Override
    public Collection<Order> prepareData() throws DatabaseException {
        try {
            return prepareDataRemote(isRemotePath());
        } catch (FileTypeException fte) {
            return prepareDataLocal(isNotLocalPath());
        }

    }


    private Collection<Order> prepareDataLocal(File file) throws DatabaseException {
        try {
            if (!file.canRead()) {
                throw new FileNotFoundException("Problems with reading file.");
            }

            InputStream input = new FileInputStream(file);
            return read(input);

        } catch (FileNotFoundException e) {
            throw new DatabaseException("Problem with find file.", e);
        }
    }


    private Collection<Order> prepareDataRemote(URL url) throws DatabaseException {
        try {
            InputStream input = url.openStream();
            return read(input);
        } catch (IOException e) {
            throw new DatabaseException("Problem with find or reading remote file.", e);
        }
    }

    /**
     * Method for read file
     *
     * @param is input stream to read
     * @return orders that are stored in a file
     * @throws DatabaseException if problems with read file
     */
    private Collection<Order> read(InputStream is) throws DatabaseException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        try {
            return getOrdersFromFile(br);

        } catch (IOException ioe) {
            throw new DatabaseException("Problem with read file.", ioe);
        }
    }

    private Collection<Order> getOrdersFromFile(BufferedReader br) throws IOException {
        Collection<Order> orders = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        String line;

        while ((line = br.readLine()) != null) {
            String[] splitData = line.split(",");

            Customer customer = new Customer(splitData[2], splitData[3]);
            orders.add(new Order(
                    Long.parseLong(splitData[0]),
                    LocalDate.parse(splitData[1], formatter),
                    new BigDecimal(splitData[4]),
                    OrderStatus.valueOf(splitData[5]), customer));
        }

        return orders;

    }

    /**
     * Method for determination if input path to file is local or remote
     *
     * @return true for remote, false for local
     * @throws FileTypeException if file type is not remote.
     */
    private URL isRemotePath() throws FileTypeException {
        URL url;

        try {
            url = new URL(fileName);

        } catch (final Exception e) {

            if (fileName.startsWith("//")) {
                throw new IllegalArgumentException("Relative context: " + fileName);
            } else {
                throw new FileTypeException();
            }
        }
        return url;
    }

    /**
     * Determinate if path to file is not local or is wrong path to file.
     *
     * @return false for local path or Exception if there is wrong path to file.
     */
    private File isNotLocalPath() {
        File file = new File(fileName);
        try {
            if (!file.canRead()) {
                throw new FileNotFoundException("File not exist");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Wrong string with path to file.", e);
        }

        return file;
    }

}




