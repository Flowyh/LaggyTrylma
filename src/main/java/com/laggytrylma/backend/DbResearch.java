package com.laggytrylma.backend;

import com.laggytrylma.common.builders.ClassicTrylmaBuilder;
import com.laggytrylma.common.builders.GameBuilderDirector;
import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbResearch implements CommandLineRunner {

    @Autowired
    GameRepoWrap repoWrap;

    public static void main(String[] args){
        SpringApplication.run(DbResearch.class, args);
    }

    public void run(String... args) throws Exception{
        GameBuilderDirector gbd = new GameBuilderDirector(new ClassicTrylmaBuilder());
        gbd.setPlayers(new Player[] {new Player(), new Player()});
        Game game = gbd.build();

        repoWrap.save(game);

        System.out.println("Reading");
        for(Game g: repoWrap.findAll()){
            System.out.println(g);
        }
    }
}
