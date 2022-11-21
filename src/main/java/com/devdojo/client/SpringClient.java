package com.devdojo.client;

import com.devdojo.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/2", Anime.class);
        log.info(entity);

        Anime forObject = new RestTemplate().getForObject("http://localhost:8080/animes/2", Anime.class);
        log.info(forObject);

        /*Teste com lista*/
        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));

        /*Exchange*/
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange(
                "http://localhost:8080/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {
                });

        log.info(exchange.getBody());



        /*POST*/

     //   Anime kingdom = Anime.builder().name("kingdom").build();
        // Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/new", kingdom, Anime.class);
     //   log.info(kingdomSaved);

        Anime samuraiChamploo = Anime.builder().name("Samurai Champloo").build();
        ResponseEntity<Anime> animeExchange = new RestTemplate().exchange(
                "http://localhost:8080/animes/new",
                HttpMethod.POST,
                new HttpEntity<>(samuraiChamploo),
                Anime.class);
        log.info("saved anime {}", animeExchange);


        /*PUT*/

        Anime animeToBeUpdatedSaved = animeExchange.getBody();
        animeToBeUpdatedSaved.setName("Samurai Champloo 2");

        ResponseEntity<Void> samuraiChamplooUpdated = new RestTemplate().exchange(
                "http://localhost:8080/animes",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdatedSaved),
                Void.class);

        log.info("Anime updated {}", samuraiChamplooUpdated);

        /*DELETE*/

        ResponseEntity<Void> samuraiChamplooDeleted = new RestTemplate().exchange(
                "http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                animeToBeUpdatedSaved.getId());
    }
}
