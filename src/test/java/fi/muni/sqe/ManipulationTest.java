package fi.muni.sqe;

import fi.muni.sqe.data.DataFromCSV;
import fi.muni.sqe.data.DataParse;
import fi.muni.sqe.entity.DataToPrint;
import fi.muni.sqe.entity.Order;
import fi.muni.sqe.exceptions.DatabaseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */


public class ManipulationTest {

    private String total = "total";
    private String total2018 = "total:2018";
    private String total201819 = "total:2018,2019";
    private List<String> manipulationMethods;
    private File file = new File("orders_data.csv");
    private Manipulation manipulation;
    private Collection<Order> orders;

    @Before
    public void setUp() throws Exception {

        DataParse dataParse = new DataFromCSV(file.toString());
        orders = dataParse.prepareData();
        manipulationMethods = new ArrayList<>();
        manipulation = new Manipulation(file.toString());


    }

    @After
    public void tearDown() throws Exception {
        InputStream in = new URL("https://is.muni.cz/el/fi/jaro2020/PV260/um/seminars/java_groups/initial_assignment/orders_data.csv").openStream();
        Files.copy(in, Paths.get("orders_data.csv"), StandardCopyOption.REPLACE_EXISTING);

    }

    @Test
    public void testTotalMethod() throws DatabaseException {
        manipulationMethods.add(total);
        assertThat(manipulation.doMethods(manipulationMethods)).doesNotHaveSameClassAs(DataToPrint.class);
    }

    @Test
    public void testTotalWithOneYearMethod() throws DatabaseException {
        manipulationMethods.add(total2018);
        assertThat(manipulation.doMethods(manipulationMethods)).doesNotHaveSameClassAs(DataToPrint.class);
    }

    @Test
    public void testTotalWithYearsMethod() throws DatabaseException {
        manipulationMethods.add(total201819);
        assertThat(manipulation.doMethods(manipulationMethods)).doesNotHaveSameClassAs(DataToPrint.class);
    }

    @Test
    public void testMultiplyMethods() throws DatabaseException {
        manipulationMethods.add(total2018);
        manipulationMethods.add(total201819);
        manipulationMethods.add(total);
        manipulationMethods.add("average");
        assertThat(manipulation.doMethods(manipulationMethods)).doesNotHaveSameClassAs(DataToPrint.class);
    }

    @Test
    public void testFilteringMethod() throws DatabaseException {
        Collection<Order> oldOrders = new ArrayList<>(orders);
        manipulationMethods.add("empty_customer");
        manipulation.doMethods(manipulationMethods);
        Collection<Order> newOrders = new DataFromCSV(file.toString()).prepareData();
        assertThat(oldOrders.size()).isNotEqualTo(newOrders.size());
    }

    @Test(expected = DatabaseException.class)
    public void testIllegalMethod() throws DatabaseException {
        manipulationMethods.add("tottal");
        manipulation.doMethods(manipulationMethods);
    }


}
