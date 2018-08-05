package com.example.demo.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
        "wordCount.caseSensitive=false",
        "wordCount.words.skip[0]=hello",
        "wordCount.words.skip[1]=blah",
        "wordCount.words.skip[2]=so",
})
public class WordCountConfigTests {

    @Autowired
    WordCountConfig wordCountConfig;

    @Test
    public void testPropertiesAreMappedCorrectly() {
        assertThat(wordCountConfig.isCaseSensitive(), equalTo(false));
        assertThat(wordCountConfig.getWords().getSkip(), contains("hello", "blah", "so"));
    }
}
