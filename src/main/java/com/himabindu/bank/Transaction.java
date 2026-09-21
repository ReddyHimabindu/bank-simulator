package com.himabindu.bank;

import java.math.BigDecimal;
import java.time.Instant;

public final class Transaction {

    private final String id;
    private final String fromAccountId;
    private final String toAccountId;
    private final BigDecimal amount;
    private final Instant time;

    public Transaction(String id, String fromAccountId, String toAccountId, BigDecimal amount) {
        this.id = id;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.time = Instant.now();
    }

    public String getId() { return id; }
    public String getFromAccountId() { return fromAccountId; }
    public String getToAccountId() { return toAccountId; }
    public BigDecimal getAmount() { return amount; }
    public Instant getTime() { return time; }

    @Override
    public String toString() {
        return "Transaction{id=" + id + ", from=" + fromAccountId
                + ", to=" + toAccountId + ", amount=" + amount + ", time=" + time + "}";
    }
}