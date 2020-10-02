package de.qetz.chess.game.position;

import com.google.gson.Gson;
import de.qetz.chess.game.DefaultChessGrid;
import de.qetz.chess.game.position.direction.Direction;

import java.util.Objects;

public final class Position {
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

  public Move move(Direction direction, int moves) throws PositionNotReachable {
    Position position = clone();
    switch (direction) {
      case FORWARD -> moveForward(moves);
      case RIGHT -> moveRight(moves);
      case BACKWARD -> moveBackward(moves);
      case LEFT -> moveLeft(moves);
      case RIGHT_UP_DIAGONAL -> moveRightUpDiagonal(moves);
      case RIGHT_DOWN_DIAGONAL -> moveRightDownDiagonal(moves);
      case LEFT_DOWN_DIAGONAL -> moveLeftDownDiagonal(moves);
      case LEFT_UP_DIAGONAL -> moveLeftUpDiagonal(moves);
      case KNIGHT_RIGHT_UP -> moveKnightlyRightUp();
      case KNIGHT_RIGHT_MIDDLE_UP -> moveKnightlyRightMiddleUp();
      case KNIGHT_RIGHT_MIDDLE_DOWN -> moveKnightlyRightMiddleDown();
      case KNIGHT_RIGHT_DOWN -> moveKnightlyRightDown();
      case KNIGHT_LEFT_DOWN -> moveKnightlyLeftDown();
      case KNIGHT_LEFT_MIDDLE_DOWN -> moveKnightlyLeftMiddleDown();
      case KNIGHT_LEFT_MIDDLE_UP -> moveKnightlyLeftMiddleUp();
      case KNIGHT_LEFT_UP -> moveKnightlyLeftUp();
      default -> throw PositionNotReachable.createOf(createMove(direction, moves));
    }
    return Move.newBuilder()
      .withDirection(direction)
      .withPosition(position)
      .withMoves(moves)
      .createMove();
  }

  private Move createMove(Direction direction, int moves) {
    return Move.newBuilder()
      .withDirection(direction)
      .withPosition(clone())
      .withMoves(moves)
      .createMove();
  }

  private void moveForward(int moves) throws PositionNotReachable {
    if (column + moves <= DefaultChessGrid.MAX_VALUE) {
      column += moves;
      return;
    }
    throw PositionNotReachable.createOf(createMove(Direction.FORWARD, moves));
  }

  private void moveRight(int moves) throws PositionNotReachable {
    if (row + moves <= DefaultChessGrid.MAX_VALUE) {
      row += moves;
      return;
    }
    throw PositionNotReachable.createOf(createMove(Direction.RIGHT, moves));
  }

  private void moveBackward(int moves) throws PositionNotReachable {
    if (column - moves >= DefaultChessGrid.MIN_VALUE) {
      column -= moves;
      return;
    }
    throw PositionNotReachable.createOf(createMove(Direction.BACKWARD, moves));
  }

  private void moveLeft(int moves) throws PositionNotReachable {
    if (row - moves >= DefaultChessGrid.MIN_VALUE) {
      row -= moves;
      return;
    }
    throw PositionNotReachable.createOf(createMove(Direction.LEFT, moves));
  }

  private void moveRightUpDiagonal(int moves) throws PositionNotReachable {
    if (column + moves <= DefaultChessGrid.MAX_VALUE
      && row + moves <= DefaultChessGrid.MAX_VALUE) {
      column += moves;
      row += moves;
      return;
    }
    throw PositionNotReachable.createOf(
      createMove(Direction.RIGHT_UP_DIAGONAL, moves));
  }

  private void moveRightDownDiagonal(int moves) throws PositionNotReachable {
    if (column - moves >= DefaultChessGrid.MIN_VALUE
      && row + moves <= DefaultChessGrid.MAX_VALUE) {
      column -= moves;
      row += moves;
      return;
    }
    throw PositionNotReachable.createOf(
      createMove(Direction.RIGHT_DOWN_DIAGONAL, moves));
  }

  private void moveLeftDownDiagonal(int moves) throws PositionNotReachable {
    if (column - moves >= DefaultChessGrid.MIN_VALUE
      && row - moves >= DefaultChessGrid.MIN_VALUE) {
      column -= moves;
      row -= moves;
      return;
    }
    throw PositionNotReachable.createOf(
      createMove(Direction.LEFT_DOWN_DIAGONAL, moves));
  }

  private void moveLeftUpDiagonal(int moves) throws PositionNotReachable {
    if (column + moves <= DefaultChessGrid.MAX_VALUE
      && row - moves >= DefaultChessGrid.MIN_VALUE) {
      column += moves;
      row -= moves;
      return;
    }
    throw PositionNotReachable.createOf(
      createMove(Direction.LEFT_UP_DIAGONAL, moves));
  }

  private static final int KNIGHT_MOVE_TWO_FIELDS = 2;
  private static final int KNIGHT_MOVE_ONE_FIELD = 1;

  private static final int KNIGHT_MOVES = 1;

  private void moveKnightlyRightUp() throws PositionNotReachable {
    if (column + KNIGHT_MOVE_TWO_FIELDS >= DefaultChessGrid.MIN_VALUE
      && row + KNIGHT_MOVE_ONE_FIELD >= DefaultChessGrid.MIN_VALUE) {
      column += KNIGHT_MOVE_TWO_FIELDS;
      row += KNIGHT_MOVE_ONE_FIELD;
      return;
    }
    throw PositionNotReachable.createOf(
      createMove(Direction.KNIGHT_RIGHT_UP, KNIGHT_MOVES));
  }

  private void moveKnightlyRightMiddleUp() throws PositionNotReachable {
    if (column + KNIGHT_MOVE_ONE_FIELD >= DefaultChessGrid.MIN_VALUE
      && row + KNIGHT_MOVE_TWO_FIELDS >= DefaultChessGrid.MIN_VALUE) {
      column += KNIGHT_MOVE_ONE_FIELD;
      row += KNIGHT_MOVE_TWO_FIELDS;
      return;
    }
    throw PositionNotReachable.createOf(
      createMove(Direction.KNIGHT_RIGHT_MIDDLE_UP, KNIGHT_MOVES));
  }

  private void moveKnightlyRightMiddleDown() throws PositionNotReachable {
    if (column - KNIGHT_MOVE_ONE_FIELD <= DefaultChessGrid.MAX_VALUE
      && row + KNIGHT_MOVE_TWO_FIELDS >= DefaultChessGrid.MIN_VALUE) {
      column -= KNIGHT_MOVE_ONE_FIELD;
      row += KNIGHT_MOVE_TWO_FIELDS;
      return;
    }
    throw PositionNotReachable.createOf(
      createMove(Direction.KNIGHT_RIGHT_MIDDLE_DOWN, KNIGHT_MOVES));
  }

  private void moveKnightlyRightDown() throws PositionNotReachable {
    if (column - KNIGHT_MOVE_TWO_FIELDS >= DefaultChessGrid.MIN_VALUE
      && row + KNIGHT_MOVE_ONE_FIELD <= DefaultChessGrid.MAX_VALUE) {
      column -= KNIGHT_MOVE_TWO_FIELDS;
      row += KNIGHT_MOVE_ONE_FIELD;
      return;
    }
    throw PositionNotReachable.createOf(
      createMove(Direction.KNIGHT_RIGHT_DOWN, KNIGHT_MOVES));
  }

  private void moveKnightlyLeftDown() throws PositionNotReachable {
    if (column - KNIGHT_MOVE_TWO_FIELDS >= DefaultChessGrid.MIN_VALUE
      && row - KNIGHT_MOVE_ONE_FIELD >= DefaultChessGrid.MIN_VALUE) {
      column -= KNIGHT_MOVE_TWO_FIELDS;
      row -= KNIGHT_MOVE_ONE_FIELD;
      return;
    }
    throw PositionNotReachable.createOf(
      createMove(Direction.KNIGHT_LEFT_DOWN, KNIGHT_MOVES));
  }

  private void moveKnightlyLeftMiddleDown() throws PositionNotReachable {
    if (column - KNIGHT_MOVE_ONE_FIELD >= DefaultChessGrid.MIN_VALUE
      && row - KNIGHT_MOVE_TWO_FIELDS >= DefaultChessGrid.MIN_VALUE) {
      column -= KNIGHT_MOVE_ONE_FIELD;
      row -= KNIGHT_MOVE_TWO_FIELDS;
      return;
    }
    throw PositionNotReachable.createOf(
      createMove(Direction.KNIGHT_LEFT_MIDDLE_DOWN, KNIGHT_MOVES));
  }

  private void moveKnightlyLeftMiddleUp() throws PositionNotReachable {
    if (column + KNIGHT_MOVE_ONE_FIELD <= DefaultChessGrid.MAX_VALUE
      && row - KNIGHT_MOVE_TWO_FIELDS >= DefaultChessGrid.MIN_VALUE) {
      column += KNIGHT_MOVE_ONE_FIELD;
      row -= KNIGHT_MOVE_TWO_FIELDS;
      return;
    }
    throw PositionNotReachable.createOf(
      createMove(Direction.KNIGHT_LEFT_MIDDLE_UP, KNIGHT_MOVES));
  }

  private void moveKnightlyLeftUp() throws PositionNotReachable {
    if (column + KNIGHT_MOVE_TWO_FIELDS <= DefaultChessGrid.MAX_VALUE
      && row - KNIGHT_MOVE_ONE_FIELD >= DefaultChessGrid.MIN_VALUE) {
      column += KNIGHT_MOVE_TWO_FIELDS;
      row -= KNIGHT_MOVE_ONE_FIELD;
      return;
    }
    throw PositionNotReachable.createOf(
      createMove(Direction.KNIGHT_LEFT_UP, KNIGHT_MOVES));
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
    if (!(object instanceof Position position)) {
      return false;
    }
    return column == position.column
      && row == position.row;
  }

  @Override
  public Position clone() {
    return Position.of(column, row);
  }
}
