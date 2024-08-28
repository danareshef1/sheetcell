package menu.menuItems.selectionSix;

import engine.Engine;
import engine.EngineImpl;
import fromUI.LoadSheetDTO;
import menu.MenuItem;
import menu.MenuItemListener;
import java.util.Scanner;

public class SaveSystenState implements MenuItemListener {
    public final Engine engine = EngineImpl.getInstance();

    public SaveSystenState(MenuItem menuItem) {
        menuItem.addItemSelectedListener(this);
    }

    @Override
    public void reportItemSelectedFromMenu() {
        doAction();
    }

    public void doAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the full path and file name to save the system (without extension):");
        String filePath = scanner.nextLine();

        try {
            LoadSheetDTO data = new LoadSheetDTO(filePath);
            engine.saveSystemState(data);
            System.out.println("System state successfully saved to " + filePath + ".ser");
        } catch (Exception e) {
            System.out.println("Error: Failed to save the system state.");
            System.out.println("Details: " + e.getMessage());
        }
    }
}

