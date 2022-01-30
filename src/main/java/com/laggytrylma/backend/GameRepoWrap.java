package com.laggytrylma.backend;

import com.laggytrylma.backend.server.BaseGameState;
import com.laggytrylma.common.models.Game;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class GameRepoWrap {
    @Autowired
    GameRepo repository;

    GameRepoWrap(GameRepo gameRepo){
        repository = gameRepo;
    }

    public void create(Game game){
        GameArchive ga = new GameArchive(game);
        repository.save(ga);
    }

    public List<Game> findAll(){
        List<Game> games = new LinkedList<>();

        for(GameArchive ga : repository.findAll()){
            games.add(ga.getGame());
        }

        return games;
    }

    public Map<String, String> getArchivedIdsDates() {
        Map<String, String> result = new HashMap<>();
        for(GameArchive ga : repository.findAll()){
            Map<String, String> ga_args = new HashMap<>();
            ga_args.put("date", ga.creationDate.toString());

            result.put(ga.id.toString(), ObjectJSONSerializer.serialize(ga_args));
        }
        return result;
    }

    public Game getGameById(String id) {
        if(repository.findById(id).isEmpty()) return null;
        GameArchive game = repository.findById(id).get();
        return game.getGame();
    }

    public void deleteGameById(String id) {
        repository.deleteById(id);
    }

    public void updateGameById(String id, Game game) {
        if(repository.findById(id).isEmpty()) return;
        GameArchive ga = repository.findById(id).get();
        ga.setGame(game);
        repository.save(ga);
    }
}
