package com.laggytrylma.backend;

import com.laggytrylma.backend.ctx.AbstractServer;
import com.laggytrylma.backend.server.BaseGameServer;
import com.laggytrylma.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Main backend app.
 */
@SpringBootApplication
@EnableMongoAuditing
public class Main implements CommandLineRunner {

  @Autowired
  GameRepoWrap repoWrap;

  /**
   * Main method.
   * @param args Command line args
   */
  public static void main(String[] args)  {
    SpringApplication.run(Main.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Logger.setDepth(4);
    AbstractServer server = BaseGameServer.getInstance();
    server.bindRepoWrapper(repoWrap);
    server.startServer(100, null);
    server.listen();
  }
}
