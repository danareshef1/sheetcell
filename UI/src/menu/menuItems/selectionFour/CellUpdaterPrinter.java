//package menu.menuItems.selectionFour;
//
//import fromEngine.CellDTO;
//import fromEngine.SheetDTO;
//import fromUI.CellUpdateDTO;
//
//public class CellUpdaterPrinter {
//
//    private final SheetDTO sheetDTO;
//
//    public CellUpdaterPrinter(SheetDTO sheetDTO) {
//        this.sheetDTO = sheetDTO;
//    }
//
//    public void updateCell(CellDTO cellDTO, String newValue) {
//        while (true) {
//            if (newValue.isEmpty()) {
//                newValue = null; // Handle clearing the cell
//            }
//
//            try {
//                // Update the cell value and dependencies
//                cellDTO.updateCell(newValue);
//                updateDependencies(cellDTO);
//
//                // Convert cellDTO cellId to coordinates
//                int[] coordinates = parseCellId(cellDTO.getCellId());
//                int row = coordinates[0];
//                int col = coordinates[1];
//
//                // Refresh the cell in the SheetDTO
//                sheetDTO.updateCell(row, col, newValue);
//
//                // Optionally refresh or reprint the sheet
//                // SheetPrinter.printSheet(sheetDTO);
//
//                break; // Exit loop if successful
//            } catch (Exception e) {
//                System.out.println("Error: " + e.getMessage());
//                System.out.println("Please enter a valid value.");
//            }
//        }
//    }
//
//    private void updateDependencies(CellDTO cellDTO) {
//        for (CellDTO dependentCell : cellDTO.getDependsOnValues()) {
//            dependentCell.calculateEffectiveValue();
//            dependentCell.updateVersion();
//        }
//    }
//
//    // Convert cellId to row and column
//    private int[] parseCellId(String cellId) {
//        // Assuming cellId is in format like A1, B2, etc.
//        char colChar = cellId.charAt(0);
//        int row = Integer.parseInt(cellId.substring(1)) - 1; // Convert to zero-based index
//        int col = colChar - 'A'; // Convert column character to index
//
//        return new int[]{row, col};
//    }
//}
