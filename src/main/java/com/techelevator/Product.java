package com.techelevator;

public class Product {
    private String name;
    private String slotIdentifier;
    private double price;
    private int quantity = 5;
    private double totalSales;

    public Product(String slotIdentifier, String name, double price) {
        this.slotIdentifier = slotIdentifier;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlotIdentifier() {
        return slotIdentifier;
    }

    public void setSlotIdentifier(String slotIdentifier) {
        this.slotIdentifier = slotIdentifier;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public boolean purchaseProduct() {
        if(doesExist(slotIdentifier) && !isSoldOut()) {
            this.quantity--;
            totalSales += price;
            return true;
        }

        return false;
    }

    public boolean doesExist(String slotIdentifier) {
        return this.slotIdentifier.equals(slotIdentifier);
    }

    public boolean isSoldOut() {
        return !(this.quantity > 0);
    }
}