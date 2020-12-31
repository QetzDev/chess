package de.qetz.chess.game.piece.concrete;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Optional;
import java.util.Map;

import de.qetz.chess.game.position.PositionNotReachable;
import de.qetz.chess.game.position.direction.Direction;
import de.qetz.chess.game.piece.AbstractPieceBuilder;
import de.qetz.chess.game.piece.DefaultPiece;
import de.qetz.chess.game.position.Position;
import de.qetz.chess.game.position.Move;
import de.qetz.chess.game.piece.Team;

public final class Knight extends DefaultPiece {
  private Knight(Position position, Team team) {
    super(position, team);
  }

  private static final Direction[] KNIGHT_DIRECTIONS = new Direction[]{
    Direction.KNIGHT_RIGHT_UP,
    Direction.KNIGHT_RIGHT_MIDDLE_UP,
    Direction.KNIGHT_RIGHT_MIDDLE_DOWN,
    Direction.KNIGHT_RIGHT_DOWN,
    Direction.KNIGHT_LEFT_UP,
    Direction.KNIGHT_LEFT_MIDDLE_UP,
    Direction.KNIGHT_LEFT_MIDDLE_DOWN,
    Direction.KNIGHT_LEFT_DOWN,
  };

  private static final int KNIGHT_DIRECTION_COUNT = 1;

  @Override
  public Collection<Move> calculatePossibleMoves(
    Map<Position, DefaultPiece> grid
  ) {
    Collection<Move> possibleMoves = Lists.newArrayList();
    for (Direction direction : KNIGHT_DIRECTIONS) {
      resolveUsablePosition(grid, direction).ifPresent(possibleMoves::add);
    }
    return possibleMoves;
  }

  private Optional<Move> resolveUsablePosition(
    Map<Position, DefaultPiece> grid,
    Direction direction
  ) {
    Preconditions.checkNotNull(direction);
    try {
      var move = position().move(direction, KNIGHT_DIRECTION_COUNT);
      if (grid.get(move.newPosition()) == null || grid.get(move.newPosition())
        .team() != team()) {
        return Optional.of(move);
      }
    } catch (PositionNotReachable ignorable) {}
    return Optional.empty();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder extends AbstractPieceBuilder<Builder> {
    private Builder() {}

    public Knight createKnight() {
      return new Knight(position(), team());
    }
  }
}
