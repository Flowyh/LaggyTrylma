package com.laggytrylma.backend;

import com.laggytrylma.common.builders.ClassicTrylmaBuilder;
import com.laggytrylma.common.builders.GameBuilderDirector;
import com.laggytrylma.common.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbResearch {
    @Autowired
    private GameRepo repository;

    public static void main(String[] args){
        SpringApplication.run(DbResearch.class, args);
    }

    public void run(String... args){
        GameBuilderDirector gbd = new GameBuilderDirector(new ClassicTrylmaBuilder());
        Game game = gbd.build();
        repository.save(game);
    }
}
