package fi.muni.sqe.output;

import fi.muni.sqe.entity.DataToPrint;
import fi.muni.sqe.entity.Result;
import fi.muni.sqe.exceptions.DatabaseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * Implementation interface {@link Output} for save {@link DataToPrint} to plain text file after all methods
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class OutputPlainText implements Output {

    private final File file;
    private final Collection<DataToPrint> data;


    public OutputPlainText(File file, Collection<?> data) {
        this.file = file;
        this.data = (Collection<DataToPrint>) data;
    }

    @Override
    public void saveResults() throws DatabaseException {


        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file, false))) {

            StringBuilder writeLine = writeResults();

            writer.write(String.valueOf(writeLine));
            writer.flush();
            System.out.println("Done save TXT file: " + file.toString());

        } catch (IOException e) {
            throw new DatabaseException("Error with save results to plain text file.", e);
        }

    }

    private StringBuilder writeResults() {
        StringBuilder writeLine = new StringBuilder();

        for (DataToPrint dataToPrint : data) {
            writeLine = writeHeader(dataToPrint);

            for (Result result : dataToPrint.getResults()) {
                writeLine.append(result.getYear()).append(" ")
                        .append(result.getOrderStatus()).append(" : ")
                        .append(result.getResult())
                        .append(System.lineSeparator());
            }
        }
        return writeLine;
    }

    private StringBuilder writeHeader(DataToPrint dataToPrint) {
        StringBuilder writeLine = new StringBuilder();

        writeLine.append(dataToPrint.getMethod()).append(" for year(s): ");
        if (dataToPrint.getYears().indexOf(0) != dataToPrint.getYears().indexOf(dataToPrint.getYears().size() - 1)) {
            writeLine.append(dataToPrint.getYears().indexOf(0))
                    .append("-")
                    .append(dataToPrint.getYears().indexOf(dataToPrint.getYears().size() - 1));
        } else {
            writeLine.append(dataToPrint.getYears().get(0).toString());
        }
        writeLine.append(System.lineSeparator());
        return writeLine;
    }

}

