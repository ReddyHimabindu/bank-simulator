package com.himabindu.bank;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Account a1 = new SavingAccount("A1", "Himabindu", new BigDecimal("1000.00"));
        Account a2 = new CurrentAccount("A2", "Himabindu", new BigDecimal("50.00"));

        System.out.println("=== InsufficientFundsException demo ===");
        try {
            a2.withdraw(new BigDecimal("200.00"));
            System.out.println("Withdrew successfully.");
        } catch (InsufficientFundsException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }

        try {
            a1.withdraw(new BigDecimal("300.00"));
            System.out.println("Withdrew successfully. " + a1);
        } catch (InsufficientFundsException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Integer caching demo ===");
        Integer a = 127;
        Integer b = 127;
        System.out.println("a == b (127, 127): " + (a == b));

        Integer c = 200;
        Integer d = 200;
        System.out.println("c == d (200, 200): " + (c == d));
    }
}