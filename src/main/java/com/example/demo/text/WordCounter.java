package com.example.demo.text;

import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class WordCounter {
    public Map<String, Integer> count(String str) {
        return Arrays.asList(str.replace(",", "").split(" "))
                .stream()
                .collect(Collectors.groupingBy(String::valueOf, Collectors.summingInt(s -> 1)));
    }
}