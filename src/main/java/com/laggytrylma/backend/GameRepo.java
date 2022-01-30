package com.laggytrylma.backend;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepo extends MongoRepository<GameArchive, String>{

}