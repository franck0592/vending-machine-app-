package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SalesReport {
    private Inventory inventory;
    public SalesReport(Inventory inventory) {
        this.inventory = inventory;
    }

    public void generateReport(String fileName) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        File salesFile;
        final int STARTING_INVENTORY_COUNT = 5;

        try {
            salesFile = new File(fileName);

            if (salesFile.createNewFile()) {
                try(PrintWriter dataOutput = new PrintWriter(salesFile)) {
                    Map<String, Product> productInventory = inventory.getProductInventory();
                    double totalSales = 0.0;

                    for(Product product : productInventory.values()) {
                        int remainingInventory = STARTING_INVENTORY_COUNT - product.getQuantity();
                        totalSales += product.getTotalSales();

                        dataOutput.println(product.getName() + "|" + remainingInventory);
                    }

                    dataOutput.println("");
                    dataOutput.println("**TOTAL SALES** $" + String.format( "%.2f", totalSales));
                } catch(FileNotFoundException e) {
                    System.err.println("Cannot open file for writing.");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
