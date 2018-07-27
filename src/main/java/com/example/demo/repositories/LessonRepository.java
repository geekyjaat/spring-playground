package com.example.demo.repositories;

import com.example.demo.entities.Lesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Long> {

    Lesson findByTitle(String title);

    @Query("select l from Lesson l where l.deliveredOn between ?1 and ?2")
    List<Lesson> findBetweenDates(Date date1, Date date2);
}
