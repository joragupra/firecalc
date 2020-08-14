package com.joragupra.fire;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

public class CashPortfolioCalculatorTest {

    private static final CurrencyUnit DEFAULT_CURRENCY = Monetary.getCurrency("EUR");

    private CashPortfolioSimulator simulator = new CashPortfolioSimulator();
    private LocalDate year2020 = LocalDate.of(2020, 1, 1);
    private LocalDate year2050 = LocalDate.of(2050, 1, 1);

    @Test
    public void shouldBeEnough_WhenCashDividedByTotalYearsIsGreaterThanYearlySpending() {
        var yearlySpending = Money.of(30000, DEFAULT_CURRENCY);

        assertTrue(
                simulator.simulate(
                        Money.of(1000000, DEFAULT_CURRENCY), yearlySpending, year2020, year2050));
    }

    @Test
    public void shouldBeEnough_WhenCashDividedByTotalYearsIsEqualToYearlySpending() {
        var yearlySpending = Money.of(30000, DEFAULT_CURRENCY);

        assertTrue(
                simulator.simulate(
                        Money.of(900000, DEFAULT_CURRENCY), yearlySpending, year2020, year2050));
    }

    @Test
    public void shouldNotBeEnough_WhenCashDividedByTotalYearsIsLessThanYearlySpending() {
        var yearlySpending = Money.of(30000, DEFAULT_CURRENCY);

        assertFalse(
                simulator.simulate(
                        Money.of(800000, DEFAULT_CURRENCY), yearlySpending, year2020, year2050));
    }

    @Test
    public void shouldNotBeEnough_WhenNoCashProvided() {
        var zeroEuros = Money.of(0, DEFAULT_CURRENCY);

        assertFalse(simulator.simulate(zeroEuros, null, null, null));
    }
}
