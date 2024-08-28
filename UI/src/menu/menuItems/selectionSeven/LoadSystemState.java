package menu.menuItems.selectionSeven;

import engine.Engine;
import engine.EngineImpl;
import fromEngine.SheetDTO;
import fromUI.LoadSheetDTO;
import menu.MenuItem;
import menu.MenuItemListener;
import java.util.Scanner;

public class LoadSystemState implements MenuItemListener {
    public final Engine engine = EngineImpl.getInstance();

    public LoadSystemState(MenuItem menuItem) {
        menuItem.addItemSelectedListener(this);
    }

    @Override
    public void reportItemSelectedFromMenu() {
        doAction();
    }

    public void doAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the full path and file name to load the system (without extension):");
        String filePath = scanner.nextLine();

        try {
            LoadSheetDTO data = new LoadSheetDTO(filePath);
            engine.loadSystemState(data);
            System.out.println("System state successfully loaded from " + filePath + ".ser");
        } catch (Exception e) {
            System.out.println("Error: Failed to load the system state.");
            System.out.println("Details: " + e.getMessage());
        }
    }
}
