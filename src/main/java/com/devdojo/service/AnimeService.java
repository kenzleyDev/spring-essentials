package com.devdojo.service;

import com.devdojo.domain.Anime;
import com.devdojo.mapper.AnimeMapper;
import com.devdojo.repository.AnimeRepository;
import com.devdojo.requests.AnimePostRequestBody;
import com.devdojo.requests.AnimePutRequestBody;
import exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {


     private final AnimeRepository animeRepository;
    public List<Anime> listAll() {
        return animeRepository.findAll() ;
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name) ;
    }

    public Anime findByIdOrThrowBadRequestException(long id) {
        return animeRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Anime ID not Found"));
    }

    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());
        animeRepository.save(anime);
    }
}
