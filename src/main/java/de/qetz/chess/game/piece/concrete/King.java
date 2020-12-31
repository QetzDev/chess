package de.qetz.chess.game.piece.concrete;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Map;


import de.qetz.chess.game.position.direction.Direction;
import de.qetz.chess.game.piece.AbstractPieceBuilder;
import de.qetz.chess.game.piece.DefaultPiece;
import de.qetz.chess.game.position.Position;
import de.qetz.chess.game.position.Move;
import de.qetz.chess.game.piece.Team;

public final class King extends DefaultPiece {
  private King(Position position, Team team) {
    super(position, team);
  }

  private static final Direction[] KING_DIRECTIONS = new Direction[]{
    Direction.FORWARD,
    Direction.RIGHT,
    Direction.BACKWARD,
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
    Collection<Move> possibleMoves = Lists.newArrayList();
    for (Direction direction : KING_DIRECTIONS) {
      possibleMoves.addAll(checkNextMoveForDirection(grid, direction));
    }
    addRightCastling(grid, possibleMoves);
    addLeftCastling(grid, possibleMoves);
    return possibleMoves;
  }

  private static final int ONLY_NEXT_FIELD = 1;

  private Collection<Move> checkNextMoveForDirection(
    Map<Position, DefaultPiece> grid,
    Direction direction
  ) {
    return calculateCertainMovesForDirection(grid, direction, ONLY_NEXT_FIELD);
  }

  private static final int ROW_ROCK_RIGHT = 8;

  private void addRightCastling(
    Map<Position, DefaultPiece> grid,
    Collection<Move> possibleMoves
  ) {
    if (moves() == 0
      && grid.containsKey(Position.of(position().column(), ROW_ROCK_RIGHT))
      && grid.get(Position.of(position().column(), ROW_ROCK_RIGHT)).moves() == 0
    ) {
      var isApplicable = true;
      for (int row = position().row() + 1; row < ROW_ROCK_RIGHT; row++) {
        if (grid.get(Position.of(position().column(), row)) != null) {
          isApplicable = false;
        }
      }
      if (isApplicable) {
        possibleMoves.add(Move.newBuilder()
          .withOldPosition(position())
          .withNewPosition(Position.of(position().column(), ROW_ROCK_RIGHT - 1))
          .withDirection(Direction.RIGHT)
          .withMoves((ROW_ROCK_RIGHT - 1) - position().row())
          .createMove());
      }
    }
  }

  private static final int ROW_LEFT_RIGHT = 1;

  private void addLeftCastling(
    Map<Position, DefaultPiece> grid,
    Collection<Move> possibleMoves
  ) {
    if (moves() == 0
      && grid.containsKey(Position.of(position().column(), ROW_LEFT_RIGHT))
      && grid.get(Position.of(position().column(), ROW_LEFT_RIGHT)).moves() == 0
    ) {
      var isApplicable = true;
      for (int row = position().row() - 1; row > ROW_LEFT_RIGHT; row--) {
        if (grid.get(Position.of(position().column(), row)) != null) {
          isApplicable = false;
        }
      }
      if (isApplicable) {
        possibleMoves.add(Move.newBuilder()
          .withOldPosition(position())
          .withNewPosition(Position.of(position().column(), ROW_LEFT_RIGHT + 1))
          .withDirection(Direction.LEFT)
          .withMoves(position().row() - ROW_LEFT_RIGHT)
          .createMove());
      }
    }
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder extends AbstractPieceBuilder<Builder> {
    private Builder() {}

    public King createKing() {
      return new King(position(), team());
    }
  }
}