package com.example.demo.service;

import com.example.demo.model.ArithmeticOperation;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MathService.class})
public class MathServiceTests {

    @Autowired
    MathService mathService;

    private static ArithmeticOperation arithmeticOperation;

    @BeforeClass
    public static void beforeClass() {
        arithmeticOperation = new ArithmeticOperation("add", 9, 7);
    }

    @Test
    public void test_calculate_add() throws Exception {
        arithmeticOperation.setOperation("add");
        assertTrue("Add is not working fine", mathService.calculate(arithmeticOperation).equals("9 + 7 = 16"));
    }

    @Test
    public void test_calculate_multiply() throws Exception {
        arithmeticOperation.setOperation("multiply");
        assertTrue("Add is not working fine", mathService.calculate(arithmeticOperation).equals("9 * 7 = 63"));
    }

    @Test
    public void test_calculate_subtract() throws Exception {
        arithmeticOperation.setOperation("subtract");
        assertTrue("Add is not working fine", mathService.calculate(arithmeticOperation).equals("9 - 7 = 2"));
    }

    @Test
    public void test_calculate_divide() throws Exception {
        arithmeticOperation.setOperation("divide");
        assertTrue("Add is not working fine", mathService.calculate(arithmeticOperation).equals("9 / 7 = 1"));
    }

    @Test
    public void test_calculate_default() throws Exception {
        arithmeticOperation.setOperation(null);
        assertTrue("Default is not working fine with null", mathService.calculate(arithmeticOperation).equals("9 + 7 = 16"));

        arithmeticOperation.setOperation("");
        assertTrue("Default is not working fine with empty", mathService.calculate(arithmeticOperation).equals("9 + 7 = 16"));
    }

    @Test
    public void test_sum() throws Exception {
        assertTrue("Sum is not working fine", mathService.sum(Arrays.asList("4", "5", "6")).equals("4 + 5 + 6 = 15"));
    }
}
