//package sheet.version;
//
//import sheet.Sheet;
//import java.util.HashMap;
//import java.util.Map;
//
//public class VersionManager {
//    private final Map<Integer, Sheet> versions = new HashMap<>();
//    private int currentVersion = 0;
//
//    // Method to save a new version
//    public void saveVersion(Sheet sheet) {
//        currentVersion++;
//        versions.put(currentVersion, copySheet(sheet));
//    }
//
//    // Method to get the sheet state at a specific version
//    public Sheet getSheetAtVersion(int versionNumber) {
//        if (!versions.containsKey(versionNumber)) {
//            throw new IllegalArgumentException("Invalid version number: " + versionNumber);
//        }
//        return versions.get(versionNumber);
//    }
//
//    // Method to get the number of cells changed in each version
//    public Map<Integer, Integer> getVersionsSummary() {
//        Map<Integer, Integer> summary = new HashMap<>();
//        for (Map.Entry<Integer, Sheet> entry : versions.entrySet()) {
//            summary.put(entry.getKey(), entry.getValue().getCellChangedNumber());
//        }
//        return summary;
//    }
//
//    private Sheet copySheet(Sheet sheet) {
//        // Implement a deep copy method for the sheet object
//        return sheet.copySheet();
//    }
//}
