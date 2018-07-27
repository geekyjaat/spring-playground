package com.example.demo.jpa;

import com.example.demo.entities.Lesson;
import com.example.demo.repositories.LessonRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonRepository repository;

    public LessonsController(LessonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Lesson> all() {
        return this.repository.findAll();
    }

    @PostMapping
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @GetMapping("/{id}")
    public Lesson getOne(@PathVariable("id") Long id) {
        return this.repository.findById(id).get();
    }

    @PatchMapping("/{id}")
    public Lesson patch(@PathVariable("id") Long id, @RequestBody Lesson lesson) {
        lesson.setId(id);
        return this.repository.save(lesson);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.repository.deleteById(id);
    }

}