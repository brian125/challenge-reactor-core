package com.example.demo.service;

import com.example.demo.model.Player;
import com.example.demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    public Flux<Player> getAll() {
        return playerRepository.findAll()
                .buffer(100)
                .flatMap(players -> Flux.fromStream(players.parallelStream()));
    }

    public Flux<Player> mayores34() {
        return getAll()
                .buffer(100)
                .flatMap(player -> Flux.fromStream(player.parallelStream()))
                .filter(jugador -> {
                    try {
                        return jugador.getAge() > 34;
                    } catch (Exception e) {
                        return false;
                    }
                });
    }


    public Flux<Player> clubEspecifico() {
        return mayores34()
                .buffer(100)
                .flatMap(player -> Flux.fromStream(player.parallelStream()))
                .filter(jugador -> {
                    try {
                        return jugador.getClub().equals("FC Barcelona");
                    } catch (Exception e) {
                        return false;
                    }
                })
                .onErrorContinue((e, i) ->
                        System.out.println("error por filtro 1 "+i)
                );
    }


    public Flux<Player> getFilterPlayer() {
        return clubEspecifico()
                .buffer(100)
                .flatMap(player -> Flux.fromStream(player.parallelStream()))
                .onErrorContinue((e, i) ->
                        System.out.println("error filtrando Listas "+i));
    }

    public Flux<List<Player>> getRankingPlayer() {

        return getAll()
                .buffer(100)
                .flatMap(player -> Flux.fromStream(player.parallelStream()))
                .distinct()
                .groupBy(Player::getNational)
                .flatMap(Flux::collectList)
                .map(lista -> {
                    Collections.sort(lista);
                    return lista;
                })
                .onErrorContinue((e, i) ->
                        System.out.println("error filtrando Listas "+i));
    }
}