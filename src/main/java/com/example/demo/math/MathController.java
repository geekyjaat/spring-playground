package com.example.demo.math;

import com.example.demo.model.Dimensions;
import com.example.demo.service.MathService;
import com.example.demo.model.ArithmeticOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/math")
public class MathController {

    @Autowired
    private MathService mathService;

    @GetMapping("/pi")
    public String pi() {
        return "3.141592653589793";
    }

    @GetMapping("/calculate")
    public String calculate(ArithmeticOperation arithmeticOperation) {
        return mathService.calculate(arithmeticOperation);
    }

    @PostMapping("/sum")
    public String sum(@RequestParam("n") List<String> values) {
        return mathService.sum(values);
    }

    @RequestMapping("/volume/{length}/{width}/{height}")
    public String volume(Dimensions dimensions) {
        return mathService.volume(dimensions);
    }
}
