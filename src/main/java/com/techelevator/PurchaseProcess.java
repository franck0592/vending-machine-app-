package com.techelevator;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class PurchaseProcess {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static double currentBalance;
    private final Inventory inventory;
    private Logger logger;

    public PurchaseProcess(Inventory inventory, Logger logger) {
        this.inventory = inventory;
        this.logger = logger;
        currentBalance = 0;
    }


    public void selectProduct(String slotIdentifier) {
        Map<String, Product> products = inventory.getProductInventory();

        if (products.containsKey(slotIdentifier)) {
            Product product = products.get(slotIdentifier);
            if (product.getQuantity() > 0 && currentBalance >= product.getPrice()) {
                System.out.println(ANSI_YELLOW+"\nCurrent Balance: $" + currentBalance+ANSI_RESET);

                product.purchaseProduct();
                currentBalance -= product.getPrice();

                System.out.println(ANSI_BLUE+"Dispensing " + product.getName() + "."+ANSI_RESET);
                if (slotIdentifier.startsWith("A")) {
                    System.out.println(ANSI_BLUE+"Crunch Crunch, Yum!"+ANSI_RESET);
                } else if (slotIdentifier.startsWith("B")) {
                    System.out.println(ANSI_YELLOW+"Munch Munch, Yum!"+ANSI_RESET);
                } else if (slotIdentifier.startsWith("C")) {
                    System.out.println(ANSI_RED+"Glug Glug, Yum!"+ANSI_RESET);
                } else if (slotIdentifier.startsWith("D")) {
                    System.out.println(ANSI_GREEN+"Chew Chew, Yum!"+ANSI_RESET);
                }
                String label="Remaining Balance: $";
                System.out.printf("%s%.2f",label,currentBalance);
               // System.out.println("Remaining Balance: $" + currentBalance);

                logger.transactionStory(product.getName(),product.getPrice(),currentBalance);

            } else {
                if (product.getQuantity() == 0) {
                    System.out.println("Sorry, this product is sold out.");
                } else {
                    System.out.println("Insufficient funds.");
                }
            }
        } else {
            System.out.println("Invalid product code.");
        }
    }

    public void finishTransaction() {
        double change = currentBalance;

        int dollars = (int) change;
        int cents = (int) Math.round((change - dollars) * 100);

        System.out.println("Dispensing change: " + dollars + " dollars and " + cents + " cents");

        int quarters = cents / 25;
        cents %= 25;

        int dimes = cents / 10;
        cents %= 10;

        int nickels = cents / 5;
        if(dollars > 0) {
            quarters += (dollars * 4);
            //System.out.println(dollars + " dollar(s)");
        }
        if (quarters > 0) {
            System.out.println(quarters + " quarter(s)");
        }
        if (dimes > 0) {
            System.out.println(dimes + " dime(s)");
        }
        if (nickels > 0) {
            System.out.println(nickels + " nickel(s)");
        }

        currentBalance = 0;

        logger.transactionStory("GIVE CHANGE",change,currentBalance);
//        logger.log("Transaction finished. Change dispensed: " + dollars + " dollars and " + cents + " cents");
    }

    public void startPurchaseProcess() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 3) {
            System.out.println(ANSI_BLUE+"\nPurchase Process Menu:"+ANSI_RESET);
            System.out.println(ANSI_GREEN+"(1) Feed Money"+ANSI_RESET);
            System.out.println(ANSI_GREEN+"(2) Select Product"+ANSI_RESET);
            System.out.println(ANSI_GREEN+"(3) Finish Transaction"+ANSI_RESET);
            System.out.print(ANSI_YELLOW+"Enter your choice>>>"+ANSI_RESET);
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please input a number.");
                scanner.next();
                continue;
            }

            while (choice < 1 || choice > 3) {
                System.out.println("Invalid choice. Please input (1), (2), or (3).");
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please input a number.");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1:
                    System.out.println("Enter amount to feed: ");
                    try {
                        double amount = scanner.nextDouble();
                        feedMoney(amount);
                        System.out.println("Current Balance: $" + currentBalance);
                        logger.transactionStory("FEED MONEY",amount,currentBalance);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid amount.");
                        scanner.next();
                    }
                    break;
                case 2:
                    inventory.displayInventory();
                    System.out.print(ANSI_BLUE+"Enter product code>>> "+ANSI_RESET);
                    selectProduct(scanner.next().toUpperCase());

                    break;
                case 3:
                    System.out.println("Finishing transaction...");
                    finishTransaction();
                    break;
                default:
                    System.out.println("Invalid choice. Please input (1), (2), or (3).");
            }
        }
    }

    public double feedMoney(double amount){
       return currentBalance += amount;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }
}
