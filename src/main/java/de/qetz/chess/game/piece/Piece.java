package de.qetz.chess.game.piece;

import java.util.Collection;
import java.util.Map;

import de.qetz.chess.game.position.Position;
import de.qetz.chess.game.position.Move;

public interface Piece {
  Collection<Move> calculatePossibleMoves(Map<Position, DefaultPiece> grid);
  default void executeMove() {}
}
