import jakarta.xml.bind.JAXBException;
import menu.BuildMenu;
import menu.MainMenu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws JAXBException, IOException {
        MainMenu menu = BuildMenu.buildMenu();
        menu.show();
    }
}
