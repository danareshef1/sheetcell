package files.loader;

import files.jaxb.schema.generated.STLSheet;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.*;


public class XmlFileLoader {

    private final static String XML_PACKAGE_NAME = "files.jaxb.schema.generated";

    public static STLSheet validateLoadXmlFile(String filePath) throws IOException, JAXBException {
        try {
            SheetValidator.validateXmlFile(filePath);
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

