package de.qetz.chess.game;

import java.util.Collection;
import java.util.Map;

import de.qetz.chess.game.position.Position;
import de.qetz.chess.game.position.Move;
import de.qetz.chess.game.piece.Team;

public interface Grid {
  Collection<Move> listPossibleMovesForPiece(Position position);
  Map<Position, Collection<Move>> listAllPossibleMovesForCurrentTeam();

  Team playingTeam();
  void executeMove(Move move);
}
