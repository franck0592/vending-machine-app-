package com.techelevator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static String logFile="Log.txt";

    public void transactionStory(String operation, double amount, double balance){
        try(PrintWriter writer=new PrintWriter(new FileOutputStream(logFile,true))){
            LocalDateTime dateNow=LocalDateTime.now();
            String dateNowFormat=dateNow.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a"));
            String logStory=String.format("%S %S: %.2f %.2f",dateNowFormat,operation,amount,balance);
            writer.println(logStory);

        }catch (FileNotFoundException ex){
            System.out.println("Archiving error:"+ex.getMessage());
        }
    }
}
