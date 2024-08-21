package menu.menuItems.selectionOne;

import files.loader.XmlFileLoader;
import menu.MenuItem;
import menu.MenuItemListener;

import java.util.Scanner;

public class LoadFile implements MenuItemListener {

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
            XmlFileLoader.validateLoadXmlFile(fileAddress);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
