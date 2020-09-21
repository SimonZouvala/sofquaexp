package fi.muni.sqe;

import fi.muni.sqe.entity.DataToPrint;
import fi.muni.sqe.exceptions.CommandLineException;
import fi.muni.sqe.exceptions.DatabaseException;
import fi.muni.sqe.output.Output;
import fi.muni.sqe.output.OutputPlainText;
import fi.muni.sqe.output.OutputType;
import fi.muni.sqe.output.OutputXML;
import picocli.CommandLine;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;

import static fi.muni.sqe.output.OutputType.PLAIN;
import static fi.muni.sqe.output.OutputType.XML;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

@CommandLine.Command(name = "da-tool",
        description = "Analysis of simple datasets containing information about eshop orders",
        mixinStandardHelpOptions = true)
public class Main implements Callable<Integer> {

    @CommandLine.Option(names = "-d", arity = "1",
            description = "DATASET LOCATION : path to remote or local CSV file with dataset ")
    String datasetLocation;

    @CommandLine.Option(names = "-m", arity = "1..*",
            description = "Contain list of methods names that are going to be applied to the data. " +
                    "The list can contain both filtering and analytical methods. " +
                    "The methods are executed in the provided order.")
    String[] manipulationMethods;

    @CommandLine.Option(names = "-o", arity = "1",
            description = "Specifies type of output. The prototype should support either JSON," +
                    "XML or plain text with corresponding values for this argument - ’json’, ’xml’, ’plain’.")
    String outputType;

    @CommandLine.Parameters(arity = "1", description = "Path of the output file.")
    File outputFile;


    @Override
    public Integer call() {
        if (outputFile != null) {

            try {
                Manipulation manipulation = new Manipulation(datasetLocation);
                Collection<DataToPrint> dataToPrints = manipulation.doMethods(
                        Arrays.asList(manipulationMethods));

                Output output = giveDataToOutput(dataToPrints);

                output.saveResults();

            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }

        } else throw new
                CommandLineException("Missing output file.");

        return 0;
    }


    private Output giveDataToOutput(Collection<?> dataToPrints) {
        OutputType outputTypeFile = OutputType.valueOf(outputType.toUpperCase());
        Output output;

        if (outputTypeFile == XML) {
            output = new OutputXML(outputFile, dataToPrints);
        } else if (outputTypeFile == PLAIN) {
            output = new OutputPlainText(outputFile, dataToPrints);
        } else {
            output = new OutputPlainText(outputFile, dataToPrints);
        }

        return output;
    }



    public static void main(String[] args) {
        System.exit(new CommandLine(new Main()).execute(args));
    }


}
