package de.qetz.chess.game.position;

import com.google.gson.Gson;

import java.util.logging.Logger;
import java.util.Objects;

import de.qetz.chess.game.position.direction.Direction;
import de.qetz.chess.game.DefaultChessGrid;

public final class Position {
  private static final Logger LOG = Logger.getLogger(Position.class.getSimpleName());

  public static Position of(int column, int row) {
    return new Position(column, row);
  }

  private int column;
  private int row;

  private Position(int column, int row) {
    this.column = column;
    this.row = row;
  }

  public int column() {
    return column;
  }

  public int row() {
    return row;
  }

  public Move moveUnsafe(Direction direction, int moves) {
    try {
      return move(direction, moves);
    } catch (PositionNotReachable positionNotReachable) {
      LOG.severe(positionNotReachable.getMessage());
      throw new IllegalStateException();
    }
  }

  public Move move(Direction direction, int moves) throws PositionNotReachable {
    Position oldPosition = clone();
    Position newPosition;
    switch (direction) {
      case FORWARD -> newPosition = moveForward(moves);
      case RIGHT -> newPosition = moveRight(moves);
      case BACKWARD -> newPosition = moveBackward(moves);
      case LEFT -> newPosition = moveLeft(moves);
      case RIGHT_UP_DIAGONAL -> newPosition = moveRightUpDiagonal(moves);
      case RIGHT_DOWN_DIAGONAL -> newPosition = moveRightDownDiagonal(moves);
      case LEFT_DOWN_DIAGONAL -> newPosition = moveLeftDownDiagonal(moves);
      case LEFT_UP_DIAGONAL -> newPosition = moveLeftUpDiagonal(moves);
      case KNIGHT_RIGHT_UP -> newPosition = moveKnightlyRightUp();
      case KNIGHT_RIGHT_MIDDLE_UP -> newPosition = moveKnightlyRightMiddleUp();
      case KNIGHT_RIGHT_MIDDLE_DOWN -> newPosition = moveKnightlyRightMiddleDown();
      case KNIGHT_RIGHT_DOWN -> newPosition = moveKnightlyRightDown();
      case KNIGHT_LEFT_DOWN -> newPosition = moveKnightlyLeftDown();
      case KNIGHT_LEFT_MIDDLE_DOWN -> newPosition = moveKnightlyLeftMiddleDown();
      case KNIGHT_LEFT_MIDDLE_UP -> newPosition = moveKnightlyLeftMiddleUp();
      case KNIGHT_LEFT_UP -> newPosition = moveKnightlyLeftUp();
      default -> throw PositionNotReachable.createOf(
        createMove(oldPosition, direction, moves));
    }
    return Move.newBuilder()
      .withOldPosition(oldPosition)
      .withNewPosition(newPosition)
      .withDirection(direction)
      .withMoves(moves)
      .createMove();
  }

  private Move createMove(Position newPosition, Direction direction, int moves) {
    return Move.newBuilder()
      .withOldPosition(clone())
      .withNewPosition(newPosition)
      .withDirection(direction)
      .withMoves(moves)
      .createMove();
  }

  private Position moveForward(int moves) throws PositionNotReachable {
    Position position = Position.of(column + moves, row);
    if (column + moves <= DefaultChessGrid.MAX_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.FORWARD, moves));
  }

  private Position moveRight(int moves) throws PositionNotReachable {
    Position position = Position.of(column, row + moves);
    if (row + moves <= DefaultChessGrid.MAX_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.RIGHT, moves));
  }

  private Position moveBackward(int moves) throws PositionNotReachable {
    Position position = Position.of(column - moves, row);
    if (column - moves >= DefaultChessGrid.MIN_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.BACKWARD, moves));
  }

  private Position moveLeft(int moves) throws PositionNotReachable {
    Position position = Position.of(column, row - moves);
    if (row - moves >= DefaultChessGrid.MIN_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.LEFT, moves));
  }

  private Position moveRightUpDiagonal(int moves) throws PositionNotReachable {
    Position position = Position.of(column + moves, row + moves);
    if (column + moves <= DefaultChessGrid.MAX_VALUE
      && row + moves <= DefaultChessGrid.MAX_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.RIGHT_UP_DIAGONAL, moves));
  }

  private Position moveRightDownDiagonal(int moves) throws PositionNotReachable {
    Position position = Position.of(column - moves, row + moves);
    if (column - moves >= DefaultChessGrid.MIN_VALUE
      && row + moves <= DefaultChessGrid.MAX_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.RIGHT_DOWN_DIAGONAL, moves));
  }

  private Position moveLeftDownDiagonal(int moves) throws PositionNotReachable {
    Position position = Position.of(column - moves, row - moves);
    if (column - moves >= DefaultChessGrid.MIN_VALUE
      && row - moves >= DefaultChessGrid.MIN_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.LEFT_DOWN_DIAGONAL, moves));
  }

  private Position moveLeftUpDiagonal(int moves) throws PositionNotReachable {
    Position position = Position.of(column + moves, row - moves);
    if (column + moves <= DefaultChessGrid.MAX_VALUE
      && row - moves >= DefaultChessGrid.MIN_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.LEFT_UP_DIAGONAL, moves));
  }

  private static final int KNIGHT_MOVE_TWO_FIELDS = 2;
  private static final int KNIGHT_MOVE_ONE_FIELD = 1;

  private static final int KNIGHT_MOVES = 1;

  private Position moveKnightlyRightUp() throws PositionNotReachable {
    Position position = Position.of(column + KNIGHT_MOVE_TWO_FIELDS,
      row + KNIGHT_MOVE_ONE_FIELD);
    if (column + KNIGHT_MOVE_TWO_FIELDS <= DefaultChessGrid.MAX_VALUE
      && row + KNIGHT_MOVE_ONE_FIELD <= DefaultChessGrid.MAX_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.KNIGHT_RIGHT_UP, KNIGHT_MOVES));
  }

  private Position moveKnightlyRightMiddleUp() throws PositionNotReachable {
    Position position = Position.of(column + KNIGHT_MOVE_ONE_FIELD,
      row + KNIGHT_MOVE_TWO_FIELDS);
    if (column + KNIGHT_MOVE_ONE_FIELD <= DefaultChessGrid.MAX_VALUE
      && row + KNIGHT_MOVE_TWO_FIELDS <= DefaultChessGrid.MAX_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.KNIGHT_RIGHT_MIDDLE_UP, KNIGHT_MOVES));
  }

  private Position moveKnightlyRightMiddleDown() throws PositionNotReachable {
    Position position = Position.of(column - KNIGHT_MOVE_ONE_FIELD,
      row + KNIGHT_MOVE_TWO_FIELDS);
    if (column - KNIGHT_MOVE_ONE_FIELD >= DefaultChessGrid.MIN_VALUE
      && row + KNIGHT_MOVE_TWO_FIELDS <= DefaultChessGrid.MAX_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.KNIGHT_RIGHT_MIDDLE_DOWN, KNIGHT_MOVES));
  }

  private Position moveKnightlyRightDown() throws PositionNotReachable {
    Position position = Position.of(column - KNIGHT_MOVE_TWO_FIELDS,
      row + KNIGHT_MOVE_ONE_FIELD);
    if (column - KNIGHT_MOVE_TWO_FIELDS >= DefaultChessGrid.MIN_VALUE
      && row + KNIGHT_MOVE_ONE_FIELD <= DefaultChessGrid.MAX_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.KNIGHT_RIGHT_DOWN, KNIGHT_MOVES));
  }

  private Position moveKnightlyLeftDown() throws PositionNotReachable {
    Position position = Position.of(column - KNIGHT_MOVE_TWO_FIELDS,
      row - KNIGHT_MOVE_ONE_FIELD);
    if (column - KNIGHT_MOVE_TWO_FIELDS >= DefaultChessGrid.MIN_VALUE
      && row - KNIGHT_MOVE_ONE_FIELD >= DefaultChessGrid.MIN_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.KNIGHT_LEFT_DOWN, KNIGHT_MOVES));
  }

  private Position moveKnightlyLeftMiddleDown() throws PositionNotReachable {
    Position position = Position.of(column - KNIGHT_MOVE_ONE_FIELD,
      row - KNIGHT_MOVE_TWO_FIELDS);
    if (column - KNIGHT_MOVE_ONE_FIELD >= DefaultChessGrid.MIN_VALUE
      && row - KNIGHT_MOVE_TWO_FIELDS >= DefaultChessGrid.MIN_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.KNIGHT_LEFT_MIDDLE_DOWN, KNIGHT_MOVES));
  }

  private Position moveKnightlyLeftMiddleUp() throws PositionNotReachable {
    Position position = Position.of(column + KNIGHT_MOVE_ONE_FIELD,
      row - KNIGHT_MOVE_TWO_FIELDS);
    if (column + KNIGHT_MOVE_ONE_FIELD <= DefaultChessGrid.MAX_VALUE
      && row - KNIGHT_MOVE_TWO_FIELDS >= DefaultChessGrid.MIN_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.KNIGHT_LEFT_MIDDLE_UP, KNIGHT_MOVES));
  }

  private Position moveKnightlyLeftUp() throws PositionNotReachable {
    Position position = Position.of(column + KNIGHT_MOVE_TWO_FIELDS,
      row - KNIGHT_MOVE_ONE_FIELD);
    if (column + KNIGHT_MOVE_TWO_FIELDS <= DefaultChessGrid.MAX_VALUE
      && row - KNIGHT_MOVE_ONE_FIELD >= DefaultChessGrid.MIN_VALUE) {
      return position;
    }
    throw PositionNotReachable.createOf(
      createMove(position, Direction.KNIGHT_LEFT_UP, KNIGHT_MOVES));
  }

  private static final Gson JSON_SERIALIZER = new Gson();

  @Override
  public String toString() {
    return JSON_SERIALIZER.toJson(this);
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, row);
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof Position)) {
      return false;
    }
    Position position = (Position) object;
    return column == position.column
      && row == position.row;
  }

  @Override
  public Position clone() {
    return Position.of(column, row);
  }
}
