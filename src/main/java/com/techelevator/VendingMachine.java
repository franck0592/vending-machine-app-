package com.techelevator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class VendingMachine {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private PurchaseProcess purchaseProcess;
	private Inventory inventory;
	private Logger logger;
	private SalesReport salesReport;

	public VendingMachine() {
		inventory = new Inventory();
		logger = new Logger();
		purchaseProcess = new PurchaseProcess(inventory, logger);
		salesReport = new SalesReport(inventory);

	}

	private void start() {
		loadInventory();
		// need menu exception handling here
		displayMainMenu();
	}

	private void loadInventory() {
		inventory.loadFromFile("vendingmachine.csv");
	}

	/*private void loadInventory() {
		try {
			inventory.loadFromFile("vendingmachine.csv");
		} catch (Exception *//*FileNotFoundException*//* e) {
			System.out.println("Error: Inventory file not found.");
		}
	}*/

	//needs menu exception handling
	private void displayMainMenu() {
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		System.out.println(ANSI_GREEN+"Welcome in Vending Machine:"+ANSI_RESET);
		System.out.println(ANSI_GREEN+"---------------------------"+ANSI_RESET);
		while (running) {
			System.out.println(ANSI_BLUE+"Main Menu:"+ANSI_RESET);
			System.out.println(ANSI_GREEN+"(1) Display Vending Machine Items"+ANSI_RESET);
			System.out.println(ANSI_GREEN+"(2) Purchase"+ANSI_RESET);
			System.out.println(ANSI_GREEN+"(3) Exit"+ANSI_RESET);
			System.out.print(ANSI_BLUE+"Enter your choice>>> "+ANSI_RESET);

			int choice = scanner.nextInt();

            switch (choice) {
                case 1: displayInventory();
                    break;
                case 2: purchaseProcess.startPurchaseProcess();
                    break;
                case 3:
					System.out.println("Thank you for using this vending machine.");
                    running = false;
                    break;
                case 4: generateSalesReport();
                    break;
                default:
					System.out.println("Invalid choice. Please input (1), (2), or (3).");
            }
		}
	}

	private void displayInventory() {
		System.out.println(ANSI_GREEN+"\nVending Machine Items:"+ANSI_RESET);
		inventory.displayInventory();
	}

	private void generateSalesReport() {
		String fileName = "SalesReport_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".txt";

		salesReport.generateReport(fileName);
	}

	/*private void generateSalesReport() {
		String fileName = "SalesReport_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".txt";

		try {
			salesReport.generateReport(fileName);
			System.out.println("Sales report generated: " + fileName);
		} catch (Exception *//*IOException*//* e) {
			System.out.println("Error generating sales report: " + e.getMessage());
		}
	}*/

	public static void main(String[] args) {
		VendingMachine vendingMachine = new VendingMachine();
		vendingMachine.start();
	}
}
