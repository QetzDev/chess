package de.qetz.chess.game.position;

import com.google.common.base.Preconditions;

public final class PositionNotReachable extends Exception {
  public static PositionNotReachable createOf(Move move) {
    Preconditions.checkNotNull(move);
    return new PositionNotReachable(move.clone());
  }

  private final Move move;

  private PositionNotReachable(Move move) {
    this.move = move;
  }

  public Move move() {
    return move;
  }

  private static final String ERROR_MESSAGE
    = "Field to move is out of grid! " +
    "OldPosition(Column: %s, Row: %s), NewPosition(Column: %s, Row: %s), " +
    "Moves: %s, Direction: %s";

  @Override
  public String getMessage() {
    return String.format(ERROR_MESSAGE,
      move.oldPosition().column(),
      move.oldPosition().row(),
      move.newPosition().column(),
      move.newPosition().row(),
      move.moves(),
      move.direction().name());
  }
}
