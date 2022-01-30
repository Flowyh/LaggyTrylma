package com.laggytrylma.backend;

import java.util.List;

import com.laggytrylma.common.models.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepo extends MongoRepository<Game, String>{
    public Game findByFirstName(String firstName);
    public List<Game> findByLastName(String lastName);
}