package com.devdojo.util;


import com.devdojo.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {

    public static AnimePutRequestBody createAnimePutRequestBody() {
        return AnimePutRequestBody.builder()
                .id(AnimeCreator.createValidUpdateAnimeAnime().getId())
                .name(AnimeCreator.createValidUpdateAnimeAnime().getName())
                .build();
    }
}
