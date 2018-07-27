package com.example.demo.jpa;

import com.example.demo.entities.Lesson;
import com.example.demo.entities.Views;
import com.example.demo.repositories.LessonRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonRepository repository;

    public LessonsController(LessonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @JsonView(Views.AllView.class)
    public Iterable<Lesson> all() {
        return this.repository.findAll();
    }

    @PostMapping
    @JsonView(Views.AllView.class)
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @GetMapping("/{id}")
    @JsonView(Views.AllView.class)
    public Lesson getOne(@PathVariable("id") Long id) {
        return this.repository.findById(id).get();
    }

    @PatchMapping("/{id}")
    @JsonView(Views.PatchView.class)
    public ResponseEntity<Lesson> patch(@PathVariable("id") Long id, @RequestBody final Lesson lesson) {
        try {
            final Lesson returnVal = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Record for id - %d not present.", id.intValue())));
            if (lesson.getTitle() != null)
                returnVal.setTitle(lesson.getTitle());
            if (lesson.getDeliveredOn() != null)
                returnVal.setDeliveredOn(lesson.getDeliveredOn());
            return ResponseEntity.ok(repository.save(returnVal));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.repository.deleteById(id);
    }

}