package com.laggytrylma.backend;

import com.laggytrylma.common.builders.ClassicTrylmaBuilder;
import com.laggytrylma.common.builders.GameBuilderDirector;
import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Date;
import java.util.Map;

@SpringBootApplication
@EnableMongoAuditing
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

        repoWrap.create(game);

        System.out.println("Reading");
        for(Game g: repoWrap.findAll()){
            System.out.println(g);
        }

        System.out.println("Get object id and creation date");
        Map<ObjectId, Date> archived = repoWrap.getArchivedIdsDates();
        for(ObjectId gameId: archived.keySet()){
            System.out.println(gameId + " " + archived.get(gameId));
        }

        System.out.println("Get by id");
        Game testGame = repoWrap.getGameById("123");
        System.out.println(testGame);
        if(testGame != null) testGame.addPlayer(new Player());

        System.out.println("Update game by id");
        repoWrap.updateGameById("123", testGame);

        System.out.println("Delete by id");
        repoWrap.deleteGameById("123");
        System.out.println("Deleted? " + repoWrap.getGameById("61f6d9b2097c8a0fdeb517be"));


    }
}
