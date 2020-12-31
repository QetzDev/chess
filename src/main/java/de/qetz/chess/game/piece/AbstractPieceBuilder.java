package de.qetz.chess.game.piece;

import com.google.common.base.Preconditions;

import de.qetz.chess.game.position.Position;

public abstract class AbstractPieceBuilder<T> {
  private Position position;
  private Team team;

  @SuppressWarnings("unchecked")
  public T withPosition(Position position) {
    this.position = position;
    return (T) this;
  }

  @SuppressWarnings("unchecked")
  public T withTeam(Team team) {
    this.team = team;
    return (T) this;
  }

  protected Position position() {
    Preconditions.checkNotNull(position);
    return position;
  }

  protected Team team() {
    Preconditions.checkNotNull(team);
    return team;
  }
}
