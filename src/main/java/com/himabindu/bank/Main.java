package com.himabindu.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        // ==================== ArrayList Demo ====================

        // STEP 1: Create an ArrayList to store accounts
        List<Account> accounts = new ArrayList<>();
        // Why ArrayList? We're storing a variable number of accounts that we'll access by index
        // Benefits: O(1) random access, easy iteration, safe removal with Iterator

        // STEP 2: Add accounts (demonstrates amortized O(1) add)
        System.out.println("=== Adding Accounts ===");
        accounts.add(new SavingAccount("SA1", "Himabindu", new BigDecimal("1000.00")));
        accounts.add(new CurrentAccount("CA1", "Raj", new BigDecimal("5000.00")));
        accounts.add(new SavingAccount("SA2", "Priya", new BigDecimal("2500.00")));
        // Internally: Each add() is O(1) amortized
        //   - Adds 1-10: Quick, no resize needed
        //   - Add 11: ArrayList realizes capacity 10 is full
        //             Creates new array of size 15 (1.5× growth)
        //             Copies all 10 elements to new array
        //             Adds the 11th element
        //             From now on, 4 more adds can happen before next resize

        // STEP 3: Print all accounts (iteration with for-each)
        System.out.println("\n=== All Accounts (For-Each Iteration) ===");
        for (Account account : accounts) {
            System.out.println(account);
        }
        // Why for-each? Creates an Iterator internally, safe, easy to read

        // STEP 4: Access by index (demonstrates O(1) random access)
        System.out.println("\n=== Access by Index ===");
        System.out.println("First account (index 0): " + accounts.get(0));
        // Why this matters? LinkedList would be O(n) for this operation
        // ArrayList is O(1) because it directly accesses the array at index 0

        // STEP 5: Get size
        System.out.println("Total accounts: " + accounts.size());
        // size() is O(1) — ArrayList stores a counter that updates on add/remove

        // STEP 6: Check if an account exists
        System.out.println("\n=== Searching for Account ===");
        Account searchAccount = new SavingAccount("SA1", "Himabindu", new BigDecimal("1000.00"));
        if (accounts.contains(searchAccount)) {
            System.out.println("Account SA1 found in the list");
        }
        // contains() is O(n) — it searches linearly through the array

        // STEP 7: IMPORTANT — Safely remove accounts with zero balance using Iterator
        System.out.println("\n=== Removing Zero-Balance Accounts ===");
        // Create some zero-balance accounts first to demonstrate
        accounts.add(new SavingAccount("SA3", "Zero", new BigDecimal("0.00")));
        accounts.add(new CurrentAccount("CA2", "Empty", new BigDecimal("0.00")));

        // Use Iterator for safe removal
        Iterator<Account> it = accounts.iterator();
        while (it.hasNext()) {
            Account acc = it.next();
            if (acc.getBalance().signum() == 0) {  // signum() returns 0 if balance is 0
                System.out.println("Removing: " + acc.getAccountId() + " (balance: " + acc.getBalance() + ")");
                it.remove();  // ✅ SAFE — Iterator knows about the removal
            }
        }

        // Why Iterator instead of regular for-loop?
        // ❌ DON'T DO THIS:
        //   for (int i = 0; i < accounts.size(); i++) {
        //       if (accounts.get(i).getBalance().signum() == 0) {
        //           accounts.remove(i);  // BUG! Shifts elements, skips next element
        //       }
        //   }
        //
        // ✅ DO THIS:
        //   for (Iterator<Account> it = accounts.iterator(); it.hasNext();) {
        //       Account acc = it.next();
        //       if (acc.getBalance().signum() == 0) {
        //           it.remove();  // Safe removal — Iterator handles shift internally
        //       }
        //   }

        System.out.println("Accounts after removal:");
        for (Account account : accounts) {
            System.out.println("  - " + account.getAccountId() + ": ₹" + account.getBalance());
        }

        // STEP 8: Insert account at specific position (demonstrates O(n) insert)
        System.out.println("\n=== Inserting Account at Position ===");
        accounts.add(1, new SavingAccount("SA_NEW", "NewAccount", new BigDecimal("750.00")));
        // Internally: ArrayList shifts all elements from index 1 onward RIGHT by 1
        //   [Account0, Account1, Account2, ...]
        //   becomes
        //   [Account0, NEW_ACCOUNT, Account1, Account2, ...]
        // Cost: O(n) where n = elements after insertion point
        // This is why ArrayList is bad for frequent head insertions!
        System.out.println("List after inserting at index 1:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(i + ": " + accounts.get(i).getAccountId());
        }
    }
}