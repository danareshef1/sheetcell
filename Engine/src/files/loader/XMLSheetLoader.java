package files.loader;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class XMLSheetLoader {

    public static void loadSheetFromXML(String filePath) {
        File xmlFile = new File(filePath);

        // Check if the file exists and if it's an XML file
        XmlValidator.validateXmlFile(filePath);

        try {
            // Prepare the file for loading
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            // Validate and load data
            if (validateSheet(doc)) {
                System.out.println("Sheet loaded successfully from " + filePath);
                // Load the data here and set the initial version
            } else {
                System.out.println("Error: Invalid sheet data.");
            }
        } catch (Exception e) {
            System.out.println("Error: An error occurred while loading the XML file.");
        }
    }

    private static boolean validateSheet(Document doc) {
        // Validate the file according to the specified requirements
        int numRows = Integer.parseInt(doc.getDocumentElement().getAttribute("rows"));
        int numCols = Integer.parseInt(doc.getDocumentElement().getAttribute("cols"));

        if (numRows < 1 || numRows > 50 || numCols < 1 || numCols > 20) {
            System.out.println("Error: Sheet size is out of valid range.");
            return false;
        }

        // Additional checks for cells, functions, etc.

        return true; // Return false if there is a problem with the data
    }
}
