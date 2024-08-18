package files.loader;

import org.w3c.dom.Document;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class XmlValidator {

    public static File validateXmlFile(String filePath) {
        filePath = validateFilePath(filePath);

        // Check if file exists
        validateFileExists(filePath);

        // Check if file has .xml extension
        if (!filePath.endsWith(".xml")) {
            throw new IllegalArgumentException("The file is not an XML file: " + filePath);
        }

        return new File(filePath);
    }

    public static String validateFilePath(String filePath) {
        Path path = Paths.get(filePath);
        Optional.ofNullable(path.getParent())
                .flatMap(parent -> Stream.iterate(parent, Objects::nonNull, Path::getParent)
                        .filter(p -> !Files.exists(p))
                        .findFirst())
                .ifPresent(nonExistentDir -> {
                    throw new InvalidPathException(nonExistentDir.toString(), "Parent directory does not exist.");
                });
        return filePath;
    }

    public static void validateFileExists(String filePath) {
        Optional.of(Files.exists(Paths.get(filePath)))
                .filter(Boolean::booleanValue)
                .orElseThrow(() -> new IllegalArgumentException("File does not exist."));
    }

    private static boolean validateSheet(Document doc) {
        // Validate the file according to the specified requirements
        int numRows = Integer.parseInt(doc.getDocumentElement().getAttribute("rows"));
        int numCols = Integer.parseInt(doc.getDocumentElement().getAttribute("cols"));

        if (numRows < 1 || numRows > 50 || numCols < 1 || numCols > 20) {
            System.out.println("Error: Sheet size is out of valid range.");
            return false;
        }
//        גודל הגליון בשורות הוא מספר שלם בין 1 ל 50 ובעמודות הוא מספר שלם בין 1 ל 20
//        במסגרת הגדרת התאים, אין תא שמיקומו מוגדר להיות מחוץ לגבולות הגליון
//        התאים המגדירים שימוש בפונקציות מכווינים לתאים המכילים מידע שמתאים לארגומנטים של הפונקציה

        // Additional checks for cells, functions, etc.

        return true; // Return false if there is a problem with the data
    }
}
