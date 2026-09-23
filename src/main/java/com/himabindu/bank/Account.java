package com.himabindu.bank;

import java.math.BigDecimal;

public abstract class Account {

    private final String accountId;
    private final String holderName;
    private BigDecimal balance;

    public Account(String accountId, String holderName, BigDecimal openingBalance) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.balance = openingBalance;
    }

    public void deposit(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) throws InsufficientFundsException {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        BigDecimal total = amount.add(calculateFee(amount));
        if (balance.compareTo(total) < 0) {
            throw new InsufficientFundsException(
                    "Cannot withdraw " + amount + ", balance is only " + balance
            );
        }
        balance = balance.subtract(total);
    }

    // No body here: every child account must write its own version
    public abstract BigDecimal calculateFee(BigDecimal amount);

    public String getAccountId() { return accountId; }
    public String getHolderName() { return holderName; }
    public BigDecimal getBalance() { return balance; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + accountId
                + ", holder=" + holderName + ", balance=" + balance + "}";
    }
}