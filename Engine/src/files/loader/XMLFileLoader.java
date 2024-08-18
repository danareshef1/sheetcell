package files.loader;

import files.jaxb.schema.STLSheet;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class XMLFileLoader {

    private final static String XML_PACKAGE_NAME = "files.jaxb.schema";

    public static STLSheet loadXmlFile(String filePath) {
        try {
            XmlValidator.validateXmlFile(filePath);
            InputStream inputStream = new FileInputStream(new File(filePath));
            return deserializeFrom(inputStream);
        } catch (JAXBException e) {
            throw new RuntimeException("Error processing the XML file", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error while loading XML file", e);
        }
    }

    private static STLSheet deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (STLSheet) u.unmarshal(in);
    }
}

