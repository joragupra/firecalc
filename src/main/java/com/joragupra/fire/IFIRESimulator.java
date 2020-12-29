package com.joragupra.fire;

import java.time.LocalDate;
import javax.money.MonetaryAmount;

public interface IFIRESimulator {
    boolean simulate(
            MonetaryAmount portfolioValue,
            MonetaryAmount yearlySpending,
            LocalDate retirementStart,
            LocalDate retirementEnd);
}
