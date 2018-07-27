package com.example.demo.jpa;

import com.example.demo.entities.Lesson;
import com.example.demo.entities.Views;
import com.example.demo.repositories.LessonRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final LessonRepository repository;

    @Autowired
    public LessonsController(LessonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @JsonView(Views.CompactView.class)
    public Iterable<Lesson> all() {
        return this.repository.findAll();
    }

    @PostMapping
    @JsonView(Views.CompactView.class)
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @GetMapping("/{id}")
    @JsonView(Views.CompactView.class)
    public Lesson getOne(@PathVariable("id") Long id) {
        return this.repository.findById(id).get();
    }

    @PatchMapping("/{id}")
    @JsonView(Views.AllView.class)
    public ResponseEntity<Lesson> patch(@PathVariable("id") Long id, @RequestBody final Lesson lesson) {
        try {
            final Lesson returnVal = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
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

    @GetMapping("/find/{title}")
    @JsonView(Views.AllView.class)
    public ResponseEntity<Lesson> find(@PathVariable("title") String title) {
        Lesson lesson = repository.findByTitle(title);
        if (lesson != null && lesson.getId() != null) {
            return ResponseEntity.ok(lesson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/between")
    @JsonView(Views.AllView.class)
    public List<Lesson> find(@RequestParam("date1") String date1, @RequestParam("date2") String date2) throws ParseException {
        Date firstDate = DATE_FORMAT.parse(date1);
        Date secondDate = DATE_FORMAT.parse(date2);
        return repository.findBetweenDates(firstDate, secondDate);
    }

}