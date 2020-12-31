package de.qetz.chess.game.piece.concrete;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;
import java.util.Collection;
import java.util.Arrays;

import de.qetz.chess.game.DefaultChessGridFactory;
import de.qetz.chess.game.position.Position;
import de.qetz.chess.game.position.Move;
import de.qetz.chess.game.piece.Team;
import de.qetz.chess.game.Grid;

// Have to use a main-method and not the normal test environment
// because IntelliJ, Gradle & Junit combined doesn't work with Java 14
// respectively with Java preview Features.
public final class RookTest {
  private static final Logger LOG = Logger.getLogger(RookTest.class.getSimpleName());

  public static void main(String[] args) {
    testRookMoves();
  }

  private static final Grid GRID = DefaultChessGridFactory.newFactory()
    .withDefaultGrid()
    .updatePiece(Position.of(3, 4), Rook.newBuilder()
      .withPosition(Position.of(3, 4))
      .withTeam(Team.BLACK)
      .createRook())
    .createGrid();

  @Test
  public static void testRookMoves() {
    Position testPosition = Position.of(3, 4);
    Collection<Move> possibleMoves = GRID.listPossibleMovesForPiece(testPosition);
    LOG.info(Arrays.toString(possibleMoves.toArray()));
  }
}
