package com.himabindu.bank;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Account savings = new SavingAccount("A101", "Rahul", new BigDecimal("10000.00"));
        Account current = new CurrentAccount("A102", "Priya", new BigDecimal("20000.00"));

        savings.withdraw(new BigDecimal("1000.00"));
        current.withdraw(new BigDecimal("1000.00"));

        System.out.println(savings);
        System.out.println(current);

    }
}