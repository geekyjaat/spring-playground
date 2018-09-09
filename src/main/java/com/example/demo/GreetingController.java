package com.example.demo;

import com.example.demo.model.Greeting;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class GreetingController {
    private static final String TEMPLATE = "Hello! %s!";

    @GetMapping("/greeting")
    public ResponseEntity<Greeting> greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name
    ) {
        Greeting greeting = new Greeting(String.format(TEMPLATE, name));
        greeting.add(linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel());
        return ResponseEntity.ok(greeting);
    }
}
