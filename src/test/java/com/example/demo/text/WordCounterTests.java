package com.example.demo.text;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {WordCounter.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WordCounterTests {
    @Autowired
    private WordCounter wordCounter;

    @Test
    public void test_word_count() {
        Map<String, Integer> result = wordCounter.count("A brown cow jumps over a brown fox");
        assertTrue(result.size() == 7);
        assertTrue(result.containsKey("A"));
        assertTrue(result.get("brown").equals(2));
    }

    @Test
    public void test_word_count_punctuation() {
        Map<String, Integer> result = wordCounter.count("How now, brown cow");
        assertTrue(result.size() == 4);
        assertTrue(result.containsKey("brown"));
        assertTrue(result.get("cow").equals(1));
    }

}
