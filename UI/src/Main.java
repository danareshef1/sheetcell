import menu.BuildMenu;
import menu.MainMenu;

public class Main {
    public static void main(String[] args) {

        MainMenu menu = BuildMenu.buildMenu();
        menu.show();
    }
}
