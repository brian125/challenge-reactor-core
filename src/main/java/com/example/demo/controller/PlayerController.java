package com.example.demo.controller;

import com.example.demo.model.Player;
import com.example.demo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping("/jugadores")
    public Flux<Player> getFilteredPlayers() {
        return playerService.getAll();
    }

    @GetMapping("/mayores")
    public Flux<Player> getMatores34(){
        return playerService.mayores34();
    }

    @GetMapping("/club")
    public Flux<Player> getClubEspecifico(){
        return playerService.mayores34();
    }

    @GetMapping("/ranking")
    public Flux<List<Player>> getListasRankingPlayers() {
        return playerService.getRankingPlayer();
    }

}