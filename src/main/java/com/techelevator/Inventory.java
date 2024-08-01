package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Inventory {
    Scanner loadedFile;
    Map<String, Product> productInventory = new HashMap<>();

    public Map<String, Product> getProductInventory() {
        return productInventory;
    }

    public Scanner getLoadedFile() {
        return loadedFile;
    }

    public void loadFromFile(String fileName) {
        File inventoryFile = new File(fileName);
        try(Scanner scanner = new Scanner(inventoryFile)) {
            Product myproduct;
            while (scanner.hasNextLine()){
                String lineOfText = scanner.nextLine();
                String[] textParts = lineOfText.split("\\|");
                // C3|Mountain Melter|1.50|Drink, it's in 4 separate parts due to string being separated by '|'
                // C3, Mountain Melter, 1.50, Drink
                myproduct = new Product(textParts[0], textParts[1], Double.parseDouble(textParts[2]));
                this.productInventory.put(textParts[0], myproduct);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading from file.");
        }
    }


    public void displayInventory() {
        List<Map.Entry<String, Product>> sortedEntries = new ArrayList<>(productInventory.entrySet());
        sortedEntries.sort(Map.Entry.comparingByKey());

        for (Map.Entry<String, Product> entry : sortedEntries) {
            String slotIdentifier = entry.getKey();
            Product myProduct = entry.getValue();
            String quantityUnit=" pc(s) remaining";
            String currency="$";
            String format = "%-4s%-20s%s%-5.2f%s%s%n";
            System.out.printf(format, slotIdentifier, myProduct.getName(),currency, myProduct.getPrice(), myProduct.getQuantity(),quantityUnit);
        }
    }



}
