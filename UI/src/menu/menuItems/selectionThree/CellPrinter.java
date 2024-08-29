
package menu.menuItems.selectionThree;

import fromEngine.CellDTO;
import sheet.coordinate.Coordinate;
import java.util.Set;

import static menu.menuItems.selectionTwo.SheetPrinter.formatCellContent;

public class CellPrinter {

    public static void displayCellDetails(CellDTO cell) {
        if (cell == null) {
            System.out.println("This cell is empty.");
            return;
        }

        displayCellInfo(cell);
    }

    private static void displayCellInfo(CellDTO cell) {
        if (cell.getOriginalValue() == null) {
            System.out.println("This cell is empty.");
            System.out.println();
        }
        System.out.println("Cell details:");
        System.out.println("------------------");
        System.out.println("Cell ID: " + cell.getCellId());
        System.out.println("Original Value: " + (cell.getOriginalValue() != null ? cell.getOriginalValue() : "None"));
        System.out.println("Effective Value: " + (cell.getContent() != null ? formatCellContent(cell.getContent()) : "None"));
        System.out.println("Last Version Changed: " + (cell.getVersion() != 0 ? cell.getVersion() : "Not changed yet"));
        System.out.print("This cell depends on the following cells: ");
        if (!cell.getDependsOn().isEmpty()){
            printCells(cell.getDependsOn());
        }
        else {
            System.out.println("None");
        }
        System.out.print("This cell influence on the following cells: ");
        if (!cell.getInfluencingOn().isEmpty()){
            printCells(cell.getInfluencingOn());
        }
        else {
            System.out.println("None");
        }
    }

    private static void printCells(Set<Coordinate> cells) {
        if (!cells.isEmpty()) {
            for (Coordinate coordinate : cells) {
                System.out.print(coordinate + " ");
            }
        }
        System.out.println();
    }
}
