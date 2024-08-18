package menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuItem {
    private static final char FINISH_OPTION = '6';
    private final String title;
    private final MenuItem prevMenuItem;
    private final List<MenuItem> subMenuItems = new ArrayList<>();
    private final List<MenuItemListener> itemListeners = new ArrayList<>();

    public MenuItem(String title, MenuItem prevMenuItem) {
        this.title = title;
        this.prevMenuItem = prevMenuItem;
    }

    public int getSubMenuCount() {
        return subMenuItems.size();
    }

    public MenuItem getPrevMenuItem() {
        return prevMenuItem;
    }

    public char getFinishOption() {
        return FINISH_OPTION;
    }

    public MenuItem getSubMenuByIndex(int subMenuIndex) {
        return subMenuItems.get(subMenuIndex);
    }

    public MenuItem addSubMenuItem(String title) {
        MenuItem subItem = new MenuItem(title, this);
        subMenuItems.add(subItem);
        return subItem;
    }

    public void addItemSelectedListener(MenuItemListener listener) {
        itemListeners.add(listener);
    }

    public void notifyItemListeners() {
        for (MenuItemListener listener : itemListeners) {
            listener.reportItemSelectedFromMenu();
        }
    }

    public void selected() {
        System.out.println();
        notifyItemListeners();
        System.out.println("Please press Enter to continue.");
        new Scanner(System.in).nextLine();
    }

    private void printMenu(String finishWord) {
        System.out.println("~~~ " + title + " ~~~");
        System.out.println("============================");
        for (int i = 0; i < subMenuItems.size(); i++) {
            System.out.println("(" + (i + 1) + ") " + subMenuItems.get(i).title);
        }
        System.out.println("(" + FINISH_OPTION + ") " + finishWord);
        System.out.println("============================");
        System.out.println("Please choose an option between 1 - " + subMenuItems.size() + ", or " + FINISH_OPTION + " to " + finishWord);
    }

    public void show(String finishWord) {
        if (!subMenuItems.isEmpty()) {
            printMenu(finishWord);
        }
    }
}
