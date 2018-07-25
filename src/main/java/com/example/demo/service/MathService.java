package com.example.demo.service;

import com.example.demo.model.ArithmeticOperation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MathService {

    public String calculate(ArithmeticOperation arithmeticOperation) {
        int x = arithmeticOperation.getX();
        int y = arithmeticOperation.getY();
        char symbol = '+';
        int result = x + y;
        switch (arithmeticOperation.getOperation()) {
            case "add":
                result = x + y;
                break;
            case "multiply":
                symbol = '*';
                result = x * y;
                break;
            case "subtract":
                symbol = '-';
                result = x - y;
                break;
            case "divide":
                symbol = '/';
                if (y == 0)
                    result = 0;
                else
                    result = x / y;
                break;
        }
        return String.format("%d %c %d = %d", x, symbol, y, result);
    }


    public String sum(List<String> values) {
        int sum = values.stream()
                .mapToInt(Integer::parseInt)
                .sum();

        String lhs = String.join(" + ", values);
        return lhs + " = " + sum;
    }
}
