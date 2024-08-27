package menu;

import java.text.ParseException;
import java.util.Scanner;

public class MainMenu {
    private static final String EXIT_MAIN_MENU = "Exit";
    private static final String BACK_FROM_SUB_MENU = "Back";
    private static final int USER_CHOSE_FINISH_BLOCK = 6;
    private final MenuItem rootMenuItem;
    private MenuItem currentMenuItem;

    public MainMenu(String title) {
        rootMenuItem = new MenuItem(title, null);
        currentMenuItem = rootMenuItem;
    }

    public MenuItem addSubMenuItem(String title) {
        return rootMenuItem.addSubMenuItem(title);
    }

    public void show() {
        boolean ifUserChoseFinish = false;

        while (!ifUserChoseFinish) {
            displayCurrentMenu();
            int userChoice = getMenuChoiceFromUser();

            if (userChoice == USER_CHOSE_FINISH_BLOCK) {
                ifUserChoseFinish = handleUserFinish();
            } else {
                navigateToSubMenu(userChoice);
            }
        }
    }

    private void displayCurrentMenu() {
        String finishOptionText = currentMenuItem.getPrevMenuItem() == null ? EXIT_MAIN_MENU : BACK_FROM_SUB_MENU;
        currentMenuItem.show(finishOptionText);    }


    private boolean handleUserFinish() {
        if (currentMenuItem.getPrevMenuItem() == null) {
            return true; // User chose to exit the main menu
        } else {
            currentMenuItem = currentMenuItem.getPrevMenuItem(); // Go back to the previous menu
            return false;
        }
    }

    private void navigateToSubMenu(int userChoice) {
        currentMenuItem = currentMenuItem.getSubMenuByIndex(userChoice - 1);
        if (currentMenuItem.getSubMenuCount() == 0) {
            currentMenuItem.selected();
            currentMenuItem = currentMenuItem.getPrevMenuItem();
        }
    }

    private int getMenuChoiceFromUser() {
        Scanner scanner = new Scanner(System.in);
        int userChoiceInt = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                String userChoiceStr = scanner.nextLine();
                userChoiceInt = validateUserChoice(userChoiceStr);
                validInput = true;
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }

        return userChoiceInt;
    }

    private int validateUserChoice(String userChoice) throws ParseException {
        try {
            int userChoiceInt = Integer.parseInt(userChoice);
            if (userChoiceInt == USER_CHOSE_FINISH_BLOCK) {
                return userChoiceInt;
            } else if (userChoiceInt < 1 || userChoiceInt > rootMenuItem.getSubMenuCount()) {
                throw new IllegalArgumentException(String.format("Invalid input, expected a number between (1,%d) or 0 to %s",
                        currentMenuItem.getSubMenuCount(), currentMenuItem.getPrevMenuItem() == null ? EXIT_MAIN_MENU : BACK_FROM_SUB_MENU));
            }
            return userChoiceInt;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input, you should enter a number", e);
        }
    }

}
