package menu.menuItems.selectionOne;

import engine.Engine;
import engine.EngineImpl;
import fromEngine.SheetDTO;
import menu.MenuItem;
import menu.MenuItemListener;
import fromUI.LoadSheetDTO;
import java.util.Scanner;

public class LoadFile implements MenuItemListener {
    public final Engine engine = EngineImpl.getInstance();

    public LoadFile(MenuItem menuItem) {
        menuItem.addItemSelectedListener(this);
    }

    @Override
    public void reportItemSelectedFromMenu() {
        loadFile();
    }

    public void loadFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a full file path to the file that you want to load: ");
        String fileAddress = scanner.nextLine().trim();
        try {
            LoadSheetDTO loadSheetDTO = new LoadSheetDTO(fileAddress);
            engine.loadSheetFromFile(loadSheetDTO);
            System.out.println("File loaded successfully.");
        }catch (Exception e) {
            System.out.println("can't load file: " + e.getMessage());
        }
    }
}