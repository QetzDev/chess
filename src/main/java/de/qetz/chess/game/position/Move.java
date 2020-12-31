package de.qetz.chess.game.position;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;

import java.util.Objects;

import de.qetz.chess.game.position.direction.Direction;

public final class Move {
  private final Position oldPosition;
  private final Position newPosition;
  private final Direction direction;
  private final int moves;

  private Move(
    Position oldPosition,
    Position newPosition,
    Direction direction,
    int moves
  ) {
    this.oldPosition = oldPosition;
    this.newPosition = newPosition;
    this.direction = direction;
    this.moves = moves;
  }

  public Position oldPosition() {
    return oldPosition;
  }

  public Position newPosition() {
    return newPosition;
  }

  public Direction direction() {
    return direction;
  }

  public int moves() {
    return moves;
  }

  private static final Gson JSON_SERIALIZER = new Gson();

  @Override
  public String toString() {
    return JSON_SERIALIZER.toJson(this);
  }

  @Override
  public int hashCode() {
    return Objects.hash(oldPosition, newPosition, direction, moves);
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof Move)) {
      return false;
    }
    Move move = (Move) object;
    return oldPosition.equals(move.oldPosition)
      && newPosition.equals(move.newPosition)
      &&  direction == move.direction
      && moves == move.moves;
  }

  @Override
  public Move clone() {
    return newBuilder()
      .withOldPosition(oldPosition.clone())
      .withNewPosition(newPosition.clone())
      .withDirection(direction)
      .withMoves(moves)
      .createMove();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder {
    private Position oldPosition;
    private Position newPosition;
    private Direction direction;
    private int moves;

    private Builder() {}

    public Builder withOldPosition(Position oldPosition) {
      this.oldPosition = oldPosition;
      return this;
    }

    public Builder withNewPosition(Position newPosition) {
      this.newPosition = newPosition;
      return this;
    }

    public Builder withDirection(Direction direction) {
      this.direction = direction;
      return this;
    }

    public Builder withMoves(int moves) {
      this.moves = moves;
      return this;
    }

    public Move createMove() {
      Preconditions.checkNotNull(oldPosition);
      Preconditions.checkNotNull(newPosition);
      Preconditions.checkNotNull(direction);
      return new Move(oldPosition, newPosition, direction, moves);
    }
  }
}
