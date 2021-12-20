package com.laggytrylma.backend.ctx;

import java.util.UUID;

public interface ISocketHandler {
  Object processInput(Object o, UUID client);
}
