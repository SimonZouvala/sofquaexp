package fi.muni.sqe.data;

import fi.muni.sqe.entity.Order;
import fi.muni.sqe.exceptions.DatabaseException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */


public class DataFromCSVTest {

    @Test
    public void testPrepareDataFromRemoteFile() {
        String remoteFile = "https://is.muni.cz/el/fi/jaro2020/PV260/um/seminars/java_groups/initial_" +
                "assignment/orders_data.csv";
        DataParse dataParse = new DataFromCSV(remoteFile);

        try {
            assertThat(dataParse.prepareData()).doesNotHaveSameClassAs(Order.class);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPrepareDataFromLocalFile() {
        String localFile = "orders_data.csv";
        DataParse dataParse = new DataFromCSV(localFile);
        try {
            assertThat(dataParse.prepareData()).doesNotHaveSameClassAs(Order.class);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}