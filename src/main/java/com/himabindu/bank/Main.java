package com.himabindu.bank;

import java.util.*;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Collections.synchronizedList ===\n");

        // Regular ArrayList: NOT thread-safe
        System.out.println("--- Regular ArrayList (NOT thread-safe) ---");
        List<Account> regularList = new ArrayList<>();
        regularList.add(new SavingAccount("SA1", "Alice", new BigDecimal("1000")));
        regularList.add(new CurrentAccount("CA1", "Bob", new BigDecimal("5000")));
        System.out.println("Regular list size: " + regularList.size());
        System.out.println();

        // ✅ CORRECT: Thread-safe list using Collections.synchronizedList()
        System.out.println("--- Collections.synchronizedList (thread-safe) ---");
        List<Account> sharedAccounts = Collections.synchronizedList(new ArrayList<>());
        sharedAccounts.add(new SavingAccount("SA1", "Alice", new BigDecimal("1000")));
        sharedAccounts.add(new CurrentAccount("CA1", "Bob", new BigDecimal("5000")));
        sharedAccounts.add(new SavingAccount("SA2", "Charlie", new BigDecimal("750")));
        System.out.println("Synchronized list size: " + sharedAccounts.size());
        System.out.println("Individual operations (add/get/remove) are synchronized internally");
        System.out.println();

        // ❌ WRONG: Iterating over synchronized list without explicit synchronization
        System.out.println("--- WRONG: Iterating without synchronization ---");
        try {
            for (Account acc : sharedAccounts) {
                System.out.println("  " + acc.getAccountId());
                // Another thread might modify the list here → ConcurrentModificationException
            }
            System.out.println("✓ No exception (but not guaranteed in multithreaded code)");
        } catch (ConcurrentModificationException e) {
            System.out.println("✗ ConcurrentModificationException during iteration");
        }
        System.out.println();

        // ✅ CORRECT: Iterate with explicit synchronization
        System.out.println("--- CORRECT: Iterating with synchronization ---");
        synchronized (sharedAccounts) {  // Lock the entire list
            for (Account acc : sharedAccounts) {
                System.out.println("  " + acc.getAccountId());
            }
        }
        // During this synchronized block, no other thread can modify the list
        System.out.println("✓ Safe: No other thread can modify during iteration");
        System.out.println();

        // Demonstrate the cost: mutual exclusion
        System.out.println("--- Performance Cost: Mutual Exclusion ---");
        List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());

        long start = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            syncList.add(i);  // Each add() acquires a lock
        }
        long syncTime = System.nanoTime() - start;

        List<Integer> unsyncList = new ArrayList<>();
        start = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            unsyncList.add(i);  // No lock overhead
        }
        long unsyncTime = System.nanoTime() - start;

        System.out.println("Synchronized 100K adds: " + syncTime + " ns");
        System.out.println("Unsynchronized 100K adds: " + unsyncTime + " ns");
        System.out.println("Synchronized is 2-5x slower due to locking overhead");
        System.out.println();

        // ❌ WRONG: Check-then-act is NOT atomic
        System.out.println("--- WRONG: Non-atomic check-then-act ---");
        List<String> ids = Collections.synchronizedList(new ArrayList<>());
        ids.add("SA1");
        ids.add("CA1");

        // This is NOT atomic: race condition can occur between contains() and add()
        String id = "SA1";
        if (!ids.contains(id)) {  // Step 1: Check (no lock)
            ids.add(id);           // Step 2: Add (acquire lock)
            // Another thread might have added "SA1" between steps 1 and 2!
        }
        System.out.println("Race condition possible: Thread A checks, Thread B adds, Thread A adds duplicate");
        System.out.println();

        // ✅ CORRECT: Synchronize the entire check-then-act
        System.out.println("--- CORRECT: Atomic check-then-act ---");
        synchronized (ids) {  // Hold lock for entire operation
            String checkId = "CA1";
            if (!ids.contains(checkId)) {
                ids.add(checkId);
            }
        }
        System.out.println("✓ Safe: Lock held for entire check-then-add sequence");
        System.out.println();

        // When to use Collections.synchronizedList()
        System.out.println("--- When to Use ---");
        System.out.println("✅ USE: Multiple threads accessing ArrayList");
        System.out.println("✅ USE: Occasional concurrent modifications");
        System.out.println("❌ AVOID: Read-heavy workloads (use CopyOnWriteArrayList instead)");
        System.out.println("❌ AVOID: Single-threaded code (overhead not worth it)");
        System.out.println("❌ AVOID: When check-then-act atomicity is critical");
    }
}