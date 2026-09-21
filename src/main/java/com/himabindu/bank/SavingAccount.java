package com.himabindu.bank;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SavingAccount extends Account {

    private static final BigDecimal FEE_RATE = new BigDecimal("0.005");   // 0.5%

    public SavingAccount(String accountId, String holderName, BigDecimal openingBalance) {
        super(accountId, holderName, openingBalance);
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(FEE_RATE).setScale(2, RoundingMode.HALF_UP);
    }
}