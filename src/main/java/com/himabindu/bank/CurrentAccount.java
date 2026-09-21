package com.himabindu.bank;

import java.math.BigDecimal;

public class CurrentAccount extends Account {

    private static final BigDecimal FLAT_FEE = new BigDecimal("15.00");

    public CurrentAccount(String accountId, String holderName, BigDecimal openingBalance) {
        super(accountId, holderName, openingBalance);
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return FLAT_FEE;
    }
}