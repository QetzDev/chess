package de.qetz.chess.game.position;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import de.qetz.chess.game.position.direction.Direction;

import java.util.Objects;

public final class Move {
  private final Direction direction;
  private final Position position;
  private final int moves;

  private Move(Direction direction, Position position, int moves) {
    this.direction = direction;
    this.position = position;
    this.moves = moves;
  }

  public Direction direction() {
    return direction;
  }

  public Position position() {
    return position;
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
    return Objects.hash(direction, position, moves);
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof Move move)) {
      return false;
    }
    return direction == move.direction
      && position.equals(move.position)
      && moves == move.moves;
  }

  @Override
  public Move clone() {
    return newBuilder()
      .withDirection(direction)
      .withPosition(position.clone())
      .withMoves(moves)
      .createMove();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder {
    private Direction direction;
    private Position position;
    private int moves;

    private Builder() {}

    public Builder withDirection(Direction direction) {
      this.direction = direction;
      return this;
    }

    public Builder withPosition(Position position) {
      this.position = position;
      return this;
    }

    public Builder withMoves(int moves) {
      this.moves = moves;
      return this;
    }

    public Move createMove() {
      Preconditions.checkNotNull(direction);
      Preconditions.checkNotNull(position);
      return new Move(direction, position, moves);
    }
  }
}
