package fi.muni.sqe.output;

import fi.muni.sqe.entity.DataToPrint;
import fi.muni.sqe.entity.Result;
import fi.muni.sqe.exceptions.DatabaseException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Collection;

/**
 * Implementation interface {@link Output} for save {@link DataToPrint} to XML file after all methods
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class OutputXML implements Output {

    private Collection<DataToPrint> data;
    private File file;

    public OutputXML(File file, Collection<?> data) {
        this.file = file;
        this.data = (Collection<DataToPrint>) data;
    }

    @Override
    public void saveResults() throws DatabaseException {

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = writeResults(documentBuilder.newDocument());


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(String.valueOf(file)));

            transformer.transform(domSource, streamResult);

            System.out.println("Done save XML File: " + file.toString());

        } catch (ParserConfigurationException pce) {
            throw new DatabaseException("Error with DocumentBuilder in OutputXML.", pce);
        } catch (TransformerException tfe) {
            throw new DatabaseException("Error with transformer results to XML file.", tfe);
        }
    }

    private Document writeResults(Document document) {

        Element root = document.createElement("results");
        document.appendChild(root);

        for (DataToPrint dataToPrint : (Collection<DataToPrint>) data) {
            Element method = writeHeader(dataToPrint, document, root);


            for (Result result : dataToPrint.getResults()) {
                Attr attributeYear = document.createAttribute("year");
                Attr attributeStatus = document.createAttribute("status");
                Element element = document.createElement("result");
                method.appendChild(element);

                attributeYear.setValue(String.valueOf(result.getYear()));
                attributeStatus.setValue(result.getOrderStatus().toString());

                element.setAttributeNode(attributeYear);
                element.setAttributeNode(attributeStatus);

                element.appendChild(document.createTextNode(result.getResult()));
            }
        }
        return document;
    }

    private Element writeHeader(DataToPrint dataToPrint, Document document, Element root) {
        Element method = document.createElement("method");
        root.appendChild(method);

        Attr attributeName = document.createAttribute("name");
        attributeName.setValue(dataToPrint.getMethod().toString());
        method.setAttributeNode(attributeName);
        return method;
    }
}
