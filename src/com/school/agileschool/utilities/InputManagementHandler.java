package com.school.agileschool.utilities;

import java.util.*;
import java.util.concurrent.Callable;

public class InputManagementHandler {
    public static int getLineAsNumber(String prompt){
        return getLineAsNumber(new Scanner(System.in), prompt);
    }

    public static int getLineAsNumber(Scanner scanner, String prompt){
        System.out.print(stringToPrompt(prompt));
        return Integer.parseInt(scanner.nextLine());
    }

    public static String getLineAsString(String prompt){
        return getLineAsString(new Scanner(System.in), prompt);
    }

    public static String getLineAsString(Scanner scanner, String prompt){
        System.out.print(stringToPrompt(prompt));
        return scanner.nextLine();
    }

    public static String stringToPrompt(String prompt){
        return prompt + ": ";
    }

    public static String runMenuUntilQuit(Map<String, Runnable> optionMap) {
        return runMenuUntilQuit(new Scanner(System.in), optionMap);
    }

    public static String runMenuUntilQuit(Scanner scanner, Map<String, Runnable> optionMap) {
        boolean[] flag = new boolean[] { true };
        String chosen = null;
        Map<String, Runnable> optionMapCopy = new LinkedHashMap<>(optionMap);
        optionMapCopy.put("Quit", () -> flag[0] = false );
        while (flag[0]) {
            chosen = runMenu(scanner, optionMapCopy);
        }
        return chosen;
    }

    public static String runMenu(Map<String, Runnable> optionMap) {
        return runMenu(new  Scanner(System.in), optionMap);
    }

    public static String runMenu(Scanner scanner, Map<String, Runnable> optionMap) {
        String chosenMenuItem = null;
        while (true) {
            List<String> keys = new ArrayList<>(optionMap.keySet());
            // Print com.school.agileschool.menu options with indexes
            for (int i = 0; i < keys.size(); i++) {
                System.out.println(i + ": " + keys.get(i));
            }
            System.out.print("Enter a number:  ");
            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
                if (choice >= 0 && choice < keys.size()) {
                    // Execute corresponding function
                    optionMap.get(keys.get(choice)).run();
                    chosenMenuItem = keys.get(choice);
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.\n");
            }
        }
        return chosenMenuItem;
    }

    public static <T> T runMenuType(Map<String, Callable<T>> optionMap) {
        return runMenuType(new Scanner(System.in), optionMap);
    }

    public static <T> T runMenuType(Scanner scanner, Map<String, Callable<T>> optionMap) {
        List<String> keys = new ArrayList<>(optionMap.keySet());
        T chosenMenuItem = null;
        while (true) {
            // Print com.school.agileschool.menu options with indexes
            for (int i = 0; i < keys.size(); i++) {
                System.out.println(i + ": " + keys.get(i));
            }
            System.out.print("Enter a number:  ");
            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
                if (choice >= 0 && choice < keys.size()) {
                    // Execute corresponding function
                    chosenMenuItem = optionMap.get(keys.get(choice)).call();
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            } catch (Exception e) {
                System.out.println("Unable to process the request.");
            }
        }
        return chosenMenuItem;
    }
}