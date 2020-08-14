package com.joragupra.fire;

import java.time.LocalDate;
import java.time.Period;
import javax.money.MonetaryAmount;

public class CashPortfolioSimulator implements IFIRESimulator {
    @Override
    public boolean simulate(
            MonetaryAmount initialCash,
            MonetaryAmount yearlySpending,
            LocalDate retirementStart,
            LocalDate retirementEnd) {
        if (initialCash.isNegativeOrZero()) {
            return false;
        }

        return initialCash
                .divide(yearsBetween(retirementStart, retirementEnd))
                .isGreaterThanOrEqualTo(yearlySpending);
    }

    private int yearsBetween(LocalDate initialDate, LocalDate endDate) {
        return Period.between(initialDate, endDate).getYears();
    }
}
