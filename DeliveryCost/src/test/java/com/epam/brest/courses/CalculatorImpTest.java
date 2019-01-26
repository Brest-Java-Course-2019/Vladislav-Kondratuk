package com.epam.brest.courses;

import com.epam.brest.courses.calc.Calculator;
import com.epam.brest.courses.calc.CalculatorImp;
import com.epam.brest.courses.calc.DataItem;
import org.opentest4j.AssertionFailedError;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

public class CalculatorImpTest {

    Calculator calculator = new CalculatorImp();

    DataItem dataItem;

    @BeforeAll
    static void setup() {
        System.out.println("@Start CalculatorImpTest");
    }

    @BeforeEach
    void init() {
        System.out.println("@Start test");

        dataItem = new DataItem();
        dataItem.setWeight(new BigDecimal("2"));
        dataItem.setDistance(new BigDecimal("2"));
        dataItem.setCostPerKg(new BigDecimal("2"));
        dataItem.setCostPerKm(new BigDecimal("2"));
    }

    @Test
    void calc() {
        System.out.println("@Test 1");

        BigDecimal calcResult = calculator.calc(dataItem);
        Assertions.assertEquals(new BigDecimal("8"), calcResult);
    }

    @Test
    void incorrectTest() {
        System.out.println("@Test 2");

        BigDecimal calcResult = calculator.calc(dataItem);

        Assertions.assertThrows(AssertionFailedError.class, () -> {
            Assertions.assertEquals(new BigDecimal("7"), calcResult);
        });
    }

    @AfterEach
    void afterEach() {
        System.out.println("@End test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("@End CalculatorImpTest");
    }


}
