package de.qetz.chess.game.finished;

import com.google.inject.Inject;

import java.util.logging.Logger;

import de.qetz.chess.event.concrete.GameFinishedTrigger;
import de.qetz.chess.event.handler.EventListener;
import de.qetz.chess.event.handler.EventHandler;
import de.qetz.chess.Chess;

@EventListener
public final class GameFinishedNotification {
  private static final Logger LOG
    = Logger.getLogger(GameFinishedNotification.class.getSimpleName());

  private final Chess chess;

  @Inject
  private GameFinishedNotification(Chess chess) {
    this.chess = chess;
  }

  @EventHandler
  public void receiveFinishedNotification(GameFinishedTrigger finished) {
    chess.updateState(Chess.GameState.FINISHED);
    LOG.info("GAME SUCCESSFULLY FINISHED BECAUSE: "
      + finished.reason() + ". WINNER: " + finished.winner());
  }
}
