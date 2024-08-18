package menu;

import java.util.Scanner;

public class MainMenu {
    private static final String EXIT_MAIN_MENU = "Exit";
    private static final String BACK_FROM_SUB_MENU = "Back";
    private static final int USER_CHOSE_FINISH_BLOCK = -1;
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
        String finishOptionText = updateFinishOptionText();

        while (!ifUserChoseFinish) {
            displayCurrentMenu(finishOptionText);
            int userChoice = getMenuChoiceFromUser();

            if (userChoice == USER_CHOSE_FINISH_BLOCK) {
                ifUserChoseFinish = handleUserFinish();
                finishOptionText = updateFinishOptionText();
            } else {
                navigateToSubMenu(userChoice);
                finishOptionText = updateFinishOptionText();
            }
        }
    }

    private void displayCurrentMenu(String finishOptionText) {
        currentMenuItem.show(finishOptionText);
    }

    private boolean handleUserFinish() {
        boolean isUserFinish = true;

        if (currentMenuItem.getPrevMenuItem() != null) {
            isUserFinish = false;
            currentMenuItem = currentMenuItem.getPrevMenuItem();
        }

        return isUserFinish;
    }

    private String updateFinishOptionText() {
        return (currentMenuItem.getPrevMenuItem() == null ? EXIT_MAIN_MENU : BACK_FROM_SUB_MENU);
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
        int userChoiceInt = 0;
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

    private int validateUserChoice(String userChoice) {
        try {
            int userChoiceInt = Integer.parseInt(userChoice);
            if (userChoiceInt == rootMenuItem.getFinishOption()) {
                return userChoiceInt;
            } else if (userChoiceInt < 1 || userChoiceInt > rootMenuItem.getSubMenuCount()) {
                throw new IllegalArgumentException(String.format("Invalid input, expected a number between (1,%d) or %c",
                        rootMenuItem.getSubMenuCount(), rootMenuItem.getFinishOption()));
            }
            return userChoiceInt;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input, you should enter a number", e);
        }
    }

}
