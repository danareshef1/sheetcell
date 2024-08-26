//package menu.menuItems.selectionFive;
//
//import engine.Engine;
//import engine.EngineImpl;
//import fromEngine.SheetDTO;
//import menu.MenuItem;
//import menu.MenuItemListener;
//
//import java.util.Map;
//import java.util.Scanner;
//
//public class VersionManager implements MenuItemListener {
//    private final Engine engine = EngineImpl.getInstance();
//
//    public VersionManager(MenuItem menuItem) {
//        menuItem.addItemSelectedListener(this);
//    }
//
//    @Override
//    public void reportItemSelectedFromMenu() {
//        displayVersions();
//    }
//
//    private void displayVersions() {
//        displayVersionsTable(engine.displaySheet().getVersion());
//        int version = getVersionNumber(engine.displaySheet().getVersion());
//        System.out.println("Displaying sheet for version " + version + ":");
//        System.out.println(engine.displaySheetVersions().toString());
//    }
//
//    private void displayVersionsTable(int version) {
//        System.out.println("Version number | Number of changes");
//        System.out.println("--------------- -------------------");
//        for (int i=0;i<version; i++){
//            int versionNumber = i+1;
//            int changesNumber = engine.;
//        }
//    }
//
//    private int getVersion(int numVersions){
//        Scanner scanner = new Scanner(System.in);
//        while (true){
//            System.out.println("Please Enter the version number of the sheet you would like to view: ");
//            try{
//                int versionNumber =
//            }
//        }
//    }
////    private void printVersionTable(Map<Integer, Integer> versionData) {
////        System.out.println("Version | Cells Changed");
////        for (Map.Entry<Integer, Integer> entry : versionData.entrySet()) {
////            System.out.printf("%7d | %12d%n", entry.getKey(), entry.getValue());
////        }
////    }
////
////    private void displaySheetAtVersion(int versionNumber) {
////        // Retrieve and display the sheet at the specified version
////        SheetDTO sheetDTO = engine.getSheetAtVersion(versionNumber);
////        printSheet(sheetDTO);
////    }
//
//    private void printSheet(SheetDTO sheetDTO) {
//        // Assuming the SheetDTO has a method toString or similar for printing
//        System.out.println(sheetDTO);
//    }
//}
