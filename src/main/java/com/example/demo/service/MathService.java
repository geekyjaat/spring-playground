package com.example.demo.service;

import com.example.demo.model.AreaRequest;
import com.example.demo.model.ArithmeticOperation;
import com.example.demo.model.Dimensions;
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

    public String volume(Dimensions dimensions) {
        int volume = dimensions.getLength() * dimensions.getWidth() * dimensions.getHeight();

        return String.format("The volume of a %dx%dx%d rectangle is %d",
                dimensions.getLength(), dimensions.getWidth(), dimensions.getHeight(), volume);
    }

    public String area(AreaRequest areaRequest) {
        if (areaRequest != null) {
            if (areaRequest.getType() != null) {
                switch (areaRequest.getType()) {
                    case "circle":
                        if (areaRequest.getRadius() != null && !areaRequest.getRadius().isEmpty()) {
                            return String.format(
                                    "Area of a circle with a radius of %s is %7.5f",
                                    areaRequest.getRadius(),
                                    3.141592653589793 * Math.pow(Double.parseDouble(areaRequest.getRadius()), 2)
                            );
                        }
                        break;

                    case "rectangle":
                        if (areaRequest.getWidth() != null
                                && areaRequest.getHeight() != null
                                && !areaRequest.getWidth().isEmpty()
                                && !areaRequest.getHeight().isEmpty()) {
                            return String.format(
                                    "Area of a %sx%s rectangle is %d",
                                    areaRequest.getWidth(),
                                    areaRequest.getHeight(),
                                    Integer.parseInt(areaRequest.getWidth()) * Integer.parseInt(areaRequest.getHeight())
                            );
                        }
                        break;
                }
            }
        }
        return "Invalid";
    }
}
