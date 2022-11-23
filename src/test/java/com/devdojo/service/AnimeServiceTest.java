package com.devdojo.service;


import com.devdojo.domain.Anime;
import com.devdojo.exception.BadRequestException;
import com.devdojo.repository.AnimeRepository;
import com.devdojo.util.AnimeCreator;
import com.devdojo.util.AnimePostRequestBodyCreator;
import com.devdojo.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn((List.of(AnimeCreator.createValidAnime())));

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }

    @Test
    @DisplayName("ListAll returns list of anime inside page object when successful")
    void listAll_ReturnsListOfAnimesInsidePageObjectt_WhenSucessful() {

        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeService.listAll(PageRequest.of(1, 1));

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("ListAll returns list of anime when successful")
    void listAll_ReturnsListAllOfAnimes_WhenSucessful() {

        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animeList = animeService.listAllNonPageable();

        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns anime when successful")
    void findByIdOrThrowBadRequestException_ReturnsAnime_WhenSucessful() {

        Long excpectedId = AnimeCreator.createValidAnime().getId();
        Anime animes = animeService.findByIdOrThrowBadRequestException(1L);

        Assertions.assertThat(animes.getId())
                .isNotNull()
                .isEqualTo(excpectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when anime is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound() {

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> this.animeService.findByIdOrThrowBadRequestException(1l));
    }

    @Test
    @DisplayName("findByName returns a list of anime when successful")
    void findByName_ReturnsAnimes_WhenSucessful() {

        String excpectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> anime = animeService.findByName("anime");

        Assertions.assertThat(anime.get(0).getName())
                .isNotNull()
                .isEqualTo(excpectedName);

    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsAnEmptyListOfAnime_WhenNotFound() {

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> anime = animeService.findByName("anime");

        Assertions.assertThat(anime)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSucessful() {

        Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(anime).isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("replace udate anime when successful")
    void replace_UpdatesAnime_WhenSucessful() {

        // Maneira 1
        Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("delete anime when successful")
    void delete_RemoveAnime_WhenSucessful() {

        // Maneira 1
        Assertions.assertThatCode(() -> animeService.delete(1))
                .doesNotThrowAnyException();
    }

}