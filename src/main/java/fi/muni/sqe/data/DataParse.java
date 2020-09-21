package fi.muni.sqe.data;

import fi.muni.sqe.entity.Order;
import fi.muni.sqe.exceptions.DatabaseException;

import java.util.Collection;

/**
 * Interface for read file and save data as {@link Order}.
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public interface DataParse {

    /**
     * Method that read input and save data to collection of {@link Order}
     *
     * @return orders from input local file or remote file
     * @throws DatabaseException if problems with read file
     */
    Collection<Order> prepareData() throws DatabaseException;
}
