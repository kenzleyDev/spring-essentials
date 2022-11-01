package com.devdojo.repository;

import com.devdojo.domain.Anime;

import java.util.List;

public interface AnimeRepository {
    List<Anime> listAll();
}
