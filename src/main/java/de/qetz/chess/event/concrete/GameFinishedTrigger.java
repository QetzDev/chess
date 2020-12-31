package de.qetz.chess.event.concrete;

import com.google.common.base.Preconditions;

import de.qetz.chess.event.DefaultEvent;
import de.qetz.chess.game.piece.Team;

public final class GameFinishedTrigger extends DefaultEvent {
  public enum GameFinishedReason {
    STALEMATE,
    DRAW,
    WIN
  }

  public enum GameWinner {
    WHITE,
    BLACK,
    NOBODY;

    public static GameWinner ofTeam(Team team) {
      Preconditions.checkNotNull(team);
      return team == Team.WHITE
        ? GameWinner.WHITE
        : GameWinner.BLACK;
    }
  }

  public static GameFinishedTrigger createWith(
    GameFinishedReason reason,
    GameWinner winner
  ) {
    Preconditions.checkNotNull(reason);
    Preconditions.checkNotNull(winner);
    return new GameFinishedTrigger(reason, winner);
  }

  private final GameFinishedReason reason;
  private final GameWinner winner;

  private GameFinishedTrigger(GameFinishedReason reason, GameWinner winner) {
    this.reason = reason;
    this.winner = winner;
  }

  public GameWinner winner() {
    return winner;
  }

  public GameFinishedReason reason() {
    return reason;
  }
}