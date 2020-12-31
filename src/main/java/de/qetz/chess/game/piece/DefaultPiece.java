package de.qetz.chess.game.piece;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.logging.Logger;
import java.util.Collection;
import java.util.Optional;
import java.util.Map;


import de.qetz.chess.game.position.PositionNotReachable;
import de.qetz.chess.game.position.direction.Direction;
import de.qetz.chess.game.position.Position;
import de.qetz.chess.game.DefaultChessGrid;
import de.qetz.chess.game.position.Move;

public abstract class DefaultPiece implements Piece {
  private static final Logger LOG = Logger.getLogger(DefaultPiece.class.getSimpleName());

  private Position position;
  private final Team team;
  private int moves;

  public DefaultPiece(Position position, Team team) {
    this.position = position;
    this.team = team;
  }

  public Position position() {
    return position;
  }

  public Team team() {
    return team;
  }

  public int moves() {
    return moves;
  }

  public void move(Position newPosition) {
    Preconditions.checkNotNull(newPosition);

    position = newPosition;
    moves++;
    executeMove();
  }

  protected Collection<Move> calculateMovesForMultipleDirection(
    Map<Position, DefaultPiece> grid,
    Direction... directions
  ) {
    Collection<Move> possibleMoves = Lists.newArrayList();
    for (var direction : directions) {
      possibleMoves.addAll(calculateMovesForDirection(grid, direction));
    }
    return possibleMoves;
  }

  protected Collection<Move> calculateMovesForDirection(
    Map<Position, DefaultPiece> grid,
    Direction direction
  ) {
    return calculateCertainMovesForDirection(
      grid,
      direction,
      DefaultChessGrid.MAX_VALUE
    );
  }

  protected Collection<Move> calculateCertainMovesForDirection(
    Map<Position, DefaultPiece> grid,
    Direction direction,
    int number
  ) {
    Preconditions.checkArgument(number <= DefaultChessGrid.MAX_VALUE);
    Preconditions.checkArgument(number >= DefaultChessGrid.MIN_VALUE);
    Collection<Move> possibleMoves = Lists.newArrayList();
    var oldPosition = position.clone();
    for (int index = 1; index <= number; index++) {
      try {
        var move = oldPosition.move(direction, index);
        var lookingFor = move.newPosition();
        if (resolvePiece(grid, lookingFor).isEmpty()) {
          possibleMoves.add(move);
          continue;
        }
        if (resolvePiece(grid, lookingFor).get().team != team) {
          possibleMoves.add(move);
        }
        break;
      } catch (PositionNotReachable ignorable) {
        break;
      }
    }
    return possibleMoves;
  }

  private Optional<DefaultPiece> resolvePiece(
    Map<Position, DefaultPiece> grid,
    Position position
  ) {
    return grid.containsKey(position)
      ? Optional.of(grid.get(position))
      : Optional.empty();
  }
}
