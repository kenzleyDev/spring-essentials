package com.devdojo.controller;

import com.devdojo.domain.Anime;
import com.devdojo.requests.AnimePostRequestBody;
import com.devdojo.requests.AnimePutRequestBody;
import com.devdojo.service.AnimeService;
import com.devdojo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

    private final DateUtil dateUtil;
    private final AnimeService animeService;

    @GetMapping(path = "/list")
    public ResponseEntity<Page<Anime>> listAnime(Pageable pageable) {
        log.info(dateUtil.formatLocalDateTimeToDataBaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Anime>> listAll() {
        log.info(dateUtil.formatLocalDateTimeToDataBaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAllNonPageable(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id) {
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }
    @GetMapping(path = "/name")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @PostMapping(path = "/new")
    public ResponseEntity<Anime> saveNewAnime(@RequestBody @Valid AnimePostRequestBody animePostRequestBody) {
       return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody) {
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
