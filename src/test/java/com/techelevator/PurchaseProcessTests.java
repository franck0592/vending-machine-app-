package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PurchaseProcessTests {
	private PurchaseProcess purchaseProcess;
	private double currentBalance;
	private Inventory inventory=new Inventory();
	private Logger logger=new Logger();
	@Before
	public void loadTestData(){
		purchaseProcess=new PurchaseProcess(inventory, logger);
	}

	@Test
	public void testMethod1() {
	}
	@Test
	public void test_feedMoney_shouldReturn_correctBalance(){
		double balance=0.0;
		balance=purchaseProcess.feedMoney(5);
		balance=purchaseProcess.feedMoney(2);
		double balanceExpected=7;

		Assert.assertEquals("wrong balance",balanceExpected,balance,0.0);
	}

	@Test
	public void test_selectProduct_validProductWithSufficientFunds() {
		Inventory testInventory = new Inventory();
		testInventory.loadFromFile("vendingmachine.csv");

		PurchaseProcess purchaseProcess = new PurchaseProcess(testInventory, new Logger());
		purchaseProcess.feedMoney(10);

		purchaseProcess.selectProduct("A1");

		double expectedBalance = 6.95;
		Assert.assertEquals("Balance should be updated correctly after selecting a valid product", expectedBalance, purchaseProcess.getCurrentBalance(), 0.01);
	}

	@Test
	public void test_selectProduct_invalidProduct() {
		Inventory testInventory = new Inventory();
		testInventory.loadFromFile("vendingmachine.csv");

		PurchaseProcess purchaseProcess = new PurchaseProcess(testInventory, new Logger());
		purchaseProcess.feedMoney(10);

		purchaseProcess.selectProduct("Z1");

		double expectedBalance = 10.00;
		Assert.assertEquals("Balance should remain the same after selecting an invalid product", expectedBalance, purchaseProcess.getCurrentBalance(), 0.01);
	}

	@Test
	public void test_selectProduct_insufficientFunds() {
		Inventory testInventory = new Inventory();
		testInventory.loadFromFile("vendingmachine.csv");

		PurchaseProcess purchaseProcess = new PurchaseProcess(testInventory, new Logger());
		purchaseProcess.feedMoney(1);

		purchaseProcess.selectProduct("A1");

		double expectedBalance = 1.00;
		Assert.assertEquals("Balance should remain the same with insufficient funds", expectedBalance, purchaseProcess.getCurrentBalance(), 0.01);
	}

	@Test
	public void test_finishTransaction() {
		Inventory testInventory = new Inventory();
		testInventory.loadFromFile("vendingmachine.csv");

		PurchaseProcess purchaseProcess = new PurchaseProcess(testInventory, new Logger());
		purchaseProcess.feedMoney(10);

		purchaseProcess.finishTransaction();

		double expectedBalance = 0.00;
		Assert.assertEquals("Balance should be reset to zero after finishing the transaction", expectedBalance, purchaseProcess.getCurrentBalance(), 0.01);
	}
}
