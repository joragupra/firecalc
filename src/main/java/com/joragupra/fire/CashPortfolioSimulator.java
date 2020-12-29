package com.joragupra.fire;

import java.time.LocalDate;
import java.time.Period;
import javax.money.MonetaryAmount;

public class CashPortfolioSimulator implements IFIRESimulator {
    @Override
    public boolean simulate(
            MonetaryAmount portfolioValue,
            MonetaryAmount yearlySpending,
            LocalDate retirementStart,
            LocalDate retirementEnd) {
        if (portfolioValue.isNegativeOrZero()) {
            return false;
        }

        return portfolioValue
                .divide(yearsBetween(retirementStart, retirementEnd))
                .isGreaterThanOrEqualTo(yearlySpending);
    }

    private int yearsBetween(LocalDate initialDate, LocalDate endDate) {
        return Period.between(initialDate, endDate).getYears();
    }
}
