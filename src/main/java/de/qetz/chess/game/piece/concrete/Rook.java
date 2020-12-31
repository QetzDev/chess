package de.qetz.chess.game.piece.concrete;

import java.util.Collection;
import java.util.Map;

import de.qetz.chess.game.position.direction.Direction;
import de.qetz.chess.game.piece.AbstractPieceBuilder;
import de.qetz.chess.game.piece.DefaultPiece;
import de.qetz.chess.game.position.Position;
import de.qetz.chess.game.position.Move;
import de.qetz.chess.game.piece.Team;

public final class Rook extends DefaultPiece {
  private Rook(Position position, Team team) {
    super(position, team);
  }

  private static final Direction[] ROOK_DIRECTIONS = new Direction[]{
    Direction.FORWARD,
    Direction.BACKWARD,
    Direction.RIGHT,
    Direction.LEFT
  };

  @Override
  public Collection<Move> calculatePossibleMoves(
    Map<Position, DefaultPiece> grid
  ) {
    return calculateMovesForMultipleDirection(grid, ROOK_DIRECTIONS);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder extends AbstractPieceBuilder<Builder> {
    private Builder() {}

    public Rook createRook() {
      return new Rook(position(), team());
    }
  }
}
