package com.joragupra.fire;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.time.Period;

public class CashPortfolioSimulator {
    public boolean simulate(MonetaryAmount initialCash, MonetaryAmount yearlySpending, LocalDate retirementStart, LocalDate retirementEnd) {
        if (initialCash.isNegativeOrZero()) {
            return false;
        }

        return initialCash.divide(yearsBetween(retirementStart, retirementEnd)).isGreaterThanOrEqualTo(yearlySpending);
    }

    private int yearsBetween(LocalDate initialDate, LocalDate endDate) {
        return Period.between(initialDate, endDate).getYears();
    }
}
