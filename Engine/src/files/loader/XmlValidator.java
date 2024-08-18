package files.loader;

import java.io.File;

public class XmlValidator {

    public static void validateXmlFile(String filePath) {
        File xmlFile = new File(filePath);

        // Check if file exists
        if (!xmlFile.exists()) {
            throw new IllegalArgumentException("The XML file does not exist: " + filePath);
        }

        // Check if file has .xml extension
        if (!filePath.endsWith(".xml")) {
            throw new IllegalArgumentException("The file is not an XML file: " + filePath);
        }

        // Additional validation logic can go here, e.g., schema validation, etc.
        // Example:
        // validateAgainstSchema(xmlFile);
    }
}
