package com.example.demo.jpa;

import com.example.demo.movie.Album;
import com.example.demo.repositories.AlbumRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private AlbumRepository albumRepository;

    public AlbumController(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @GetMapping("")
    public Iterable<Album> all() {
        return this.albumRepository.findAll();
    }

    @PostMapping("")
    public Album add(@RequestBody Album album) {
        this.albumRepository.save(album);
        return album;
    }


}
