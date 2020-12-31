package de.qetz.chess;

import com.google.common.base.Preconditions;

import de.qetz.chess.game.Grid;

public final class Chess {
  public enum GameState {
    RUNNING,
    FINISHED
  }

  private final Grid grid;
  private GameState state;

  private Chess(Grid grid, GameState state) {
    this.grid = grid;
    this.state = state;
  }

  public Grid grid() {
    return grid;
  }

  public void updateState(GameState state) {
    Preconditions.checkNotNull(state);
    this.state = state;
  }

  public GameState state() {
    return state;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder {
    private Grid grid;
    private GameState state;

    private Builder() {}

    public Builder withGrid(Grid grid) {
      this.grid = grid;
      return this;
    }

    public Builder withGameState(GameState state) {
      this.state = state;
      return this;
    }

    public Chess createChess() {
      Preconditions.checkNotNull(grid);
      Preconditions.checkNotNull(state);
      return new Chess(grid, state);
    }
  }
}
