//package menu.menuItems.selectionFive;
//
//import menu.MenuItem;
//import menu.MenuItemListener;
//import sheet.Sheet;
//import sheet.cell.Cell;
//import sheet.coordinate.Coordinate;
//import sheet.coordinate.CoordinateFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//public class VersionManager implements MenuItemListener {
//
//    private final Sheet sheet;
//    private final Map<Integer, Map<Coordinate, Cell>> versionsHistory;
//
//    public VersionManager(MenuItem menuItem) {
//        menuItem.addItemSelectedListener(this);
//        this.sheet = sheet;
//        this.versionsHistory = new HashMap<>();
//    }
//
//    @Override
//    public void reportItemSelectedFromMenu() {
//        displayVersions();
//    }
//
////    public void saveVersion() {
////        int versionNumber = sheet.getVersion();
////        Map<Coordinate, Cell> currentVersion = new HashMap<>(sheet.getActiveCells());
////        versionsHistory.put(versionNumber, currentVersion);
////    }
//
//    public void displayVersions() {
//        System.out.println("Version History:");
//        System.out.println("Version | Changed Cells");
//
//        for (Map.Entry<Integer, Map<Coordinate, Cell>> entry : versionsHistory.entrySet()) {
//            int versionNumber = entry.getKey();
//            int changedCells = entry.getValue().size();
//            System.out.println(versionNumber + "       | " + changedCells);
//        }
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter version number to view: ");
//        int versionToView = scanner.nextInt();
//
//        if (!versionsHistory.containsKey(versionToView)) {
//            System.out.println("Invalid version number. Please try again.");
//            return;
//        }
//
//        displaySheetAtVersion(versionToView);
//    }
//
//    private void displaySheetAtVersion(int versionNumber) {
//        Map<Coordinate, Cell> versionData = versionsHistory.get(versionNumber);
//
//        System.out.println("Sheet status at version " + versionNumber + ":");
//
//        for (int row = 0; row < sheet.getSheetSize().getNumRows(); row++) {
//            for (int col = 0; col < sheet.getSheetSize().getNumCols(); col++) {
//                Coordinate coordinate = CoordinateFactory.createCoordinate(row, col);
//                Cell cell = versionData.get(coordinate);
//
//                if (cell != null) {
//                    System.out.print(cell.getCellId() + ": " + cell.getEffectiveValue() + " | ");
//                } else {
//                    System.out.print("Empty | ");
//                }
//            }
//            System.out.println();
//        }
//    }
//}