package de.qetz.chess.game.piece.concrete;

import java.util.Collection;
import java.util.Map;

import de.qetz.chess.game.position.direction.Direction;
import de.qetz.chess.game.piece.AbstractPieceBuilder;
import de.qetz.chess.game.piece.DefaultPiece;
import de.qetz.chess.game.position.Position;
import de.qetz.chess.game.position.Move;
import de.qetz.chess.game.piece.Team;

public final class Queen extends DefaultPiece {
  private Queen(Position position, Team team) {
    super(position, team);
  }

  private static final Direction[] QUEEN_DIRECTIONS = new Direction[]{
    Direction.FORWARD,
    Direction.BACKWARD,
    Direction.RIGHT,
    Direction.LEFT,
    Direction.RIGHT_UP_DIAGONAL,
    Direction.RIGHT_DOWN_DIAGONAL,
    Direction.LEFT_DOWN_DIAGONAL,
    Direction.LEFT_UP_DIAGONAL
  };

  @Override
  public Collection<Move> calculatePossibleMoves(
    Map<Position, DefaultPiece> grid
  ) {
    return calculateMovesForMultipleDirection(grid, QUEEN_DIRECTIONS);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder extends AbstractPieceBuilder<Builder> {
    private Builder() {}

    public Queen createQueen() {
      return new Queen(position(), team());
    }
  }
}