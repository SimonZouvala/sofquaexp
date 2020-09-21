package fi.muni.sqe.output;

import fi.muni.sqe.exceptions.DatabaseException;

/**
 * Interface for save {@link fi.muni.sqe.entity.DataToPrint} and {@link fi.muni.sqe.entity.Result} to output file
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public interface Output {


    /**
     * Method for save data ({@link fi.muni.sqe.entity.DataToPrint}) to output file
     * or save csv file after filtering method ({@link fi.muni.sqe.entity.Order})
     *
     * @throws DatabaseException if problem with save data to file
     */
    void saveResults() throws DatabaseException;
}
