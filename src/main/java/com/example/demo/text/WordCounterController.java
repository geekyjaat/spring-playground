package com.example.demo.text;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/words")
public class WordCounterController {

    private final WordCounter wordCounter;

    public WordCounterController(WordCounter wordCounter) {
        this.wordCounter = wordCounter;
    }

    @PostMapping("/count")
    public Map<String, Integer> count(@RequestBody String string) {
        return this.wordCounter.count(string);
    }
}
