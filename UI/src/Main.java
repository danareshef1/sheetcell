import jakarta.xml.bind.JAXBException;
import menu.BuildMenu;
import menu.BuildSubMenu;
import menu.MainMenu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws JAXBException, IOException {
        System.out.println("Welcome to Sheet-Cell!");
        System.out.println("For you to know, you need to first load an Xml file (option 1 in the menu),");
        System.out.println("and then you would able to operate the other options in the menu.");
        System.out.println();
        MainMenu menu = BuildMenu.buildMenu();
        menu.show();
    }
}
