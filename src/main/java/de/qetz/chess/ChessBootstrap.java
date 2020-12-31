package de.qetz.chess;

import com.google.inject.Injector;
import com.google.inject.Guice;

import de.qetz.chess.game.DefaultChessGridFactory;
import de.qetz.chess.event.EventInjectModule;

public final class ChessBootstrap {
  public static void main(String[] args) {
    Injector injector = createInjector();
    Chess chess = injector.getInstance(Chess.class);

    while (chess.state() == Chess.GameState.RUNNING) {}
  }

  private static Injector createInjector() {
    return Guice.createInjector(ChessInjectModule.createOf(createChess()),
      EventInjectModule.createInjectModule());
  }

  private static Chess createChess() {
    return Chess.newBuilder()
      .withGrid(DefaultChessGridFactory
        .newFactory()
        .withDefaultGrid()
        .createGrid())
      .withGameState(Chess.GameState.RUNNING)
      .createChess();
  }
}