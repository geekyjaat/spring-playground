package com.example.demo.text;

import com.example.demo.config.WordCountConfig;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WordCounter {

    private WordCountConfig wordCountConfig;

    public WordCounter(WordCountConfig wordCountConfig) {
        this.wordCountConfig = wordCountConfig;
    }

    public Map<String, Integer> count(String str) {
        return Arrays
                .asList(str.replace(",", "").split(" "))
                .stream()
                .map(s -> {
                    if (!wordCountConfig.isCaseSensitive())
                        return s.toLowerCase();
                    return s;
                })
                .filter(s -> !wordCountConfig.getWords().getSkip().contains(s))
                .collect(Collectors.groupingBy(String::valueOf, Collectors.summingInt(s -> 1)));
    }
}