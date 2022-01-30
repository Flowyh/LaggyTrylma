package com.laggytrylma.backend;

import com.laggytrylma.common.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;


@Component
public class GameRepoWrap {
    @Autowired
    GameRepo repository;

    public void save(Game game){
        GameArchive ga = new GameArchive(game);
        repository.save(ga);
    };

    public List<Game> findAll(){
        List<Game> games = new LinkedList<>();

        for(GameArchive ga : repository.findAll()){
            games.add(ga.getGame());
        }

        return games;
    }
}
