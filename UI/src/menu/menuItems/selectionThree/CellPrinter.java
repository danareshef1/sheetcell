package menu.menuItems.selectionThree;

import fromEngine.CellDTO;

import java.util.List;

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
        System.out.println("Effective Value: " + (cell.getContent() != null ? cell.getContent() : "None"));
        System.out.println("Last Version Changed: " + cell.getVersion());
        displayDependencies("Depends On:", cell.getDependsOnValues());
        displayDependencies("Affects:", cell.getInfluencingOnValues());
    }

    private static void displayDependencies(String label, List<CellDTO> dependencies) {
        System.out.print(label);
        if (!dependencies.iterator().hasNext()) {
            System.out.println(" None");
        } else {
            for (CellDTO cell : dependencies) {
                System.out.print(" " + cell.getCellId());
            }
            System.out.println();
        }
    }
}
