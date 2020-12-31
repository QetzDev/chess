package de.qetz.chess.game.piece.concrete;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Optional;
import java.util.Map;

import de.qetz.chess.game.position.PositionNotReachable;
import de.qetz.chess.game.piece.AbstractPieceBuilder;
import de.qetz.chess.game.position.direction.Direction;
import de.qetz.chess.game.piece.DefaultPiece;
import de.qetz.chess.game.position.Position;
import de.qetz.chess.game.position.Move;
import de.qetz.chess.game.piece.Team;

public final class Pawn extends DefaultPiece {
  private Pawn(Position position, Team team) {
    super(position, team);
  }

  private static final Direction PAWN_DIRECTION = Direction.FORWARD;

  @Override
  public Collection<Move> calculatePossibleMoves(
    Map<Position, DefaultPiece> grid
  ) {
    Collection<Move> possibleMoves = Lists.newArrayList();
    possibleMoves.addAll(resolveAttackPossibilities(grid));
    possibleMoves.addAll(resolveWalkPossibilities(grid));
    return possibleMoves;
  }

  private Collection<Move> resolveWalkPossibilities(
    Map<Position, DefaultPiece> grid
  ) {
    Collection<Move> possibleMoves = Lists.newArrayList();
    resolveNormalWalkMove(grid).ifPresent(possibleMoves::add);
    if (moves() == 0 && possibleMoves.size() != 0) {
      resolveDoubleWalkMove(grid).ifPresent(possibleMoves::add);
    }
    return possibleMoves;
  }

  /**
   * A pawn which was never moved is in his first move
   * able to walk two fields instead of one
   */
  private static final int MOVES_UNMOVED_PAWN = 2;
  private static final int MOVES_MOVED_PAWN = 1;

  private Optional<Move> resolveNormalWalkMove(
    Map<Position, DefaultPiece> grid
  ) {
    return resolveWalkMove(grid, MOVES_MOVED_PAWN);
  }

  private Optional<Move> resolveDoubleWalkMove(
    Map<Position, DefaultPiece> grid
  ) {
    return resolveWalkMove(grid, MOVES_UNMOVED_PAWN);
  }

  private static final Direction WALK_DIRECTION = Direction.FORWARD;

  private Optional<Move> resolveWalkMove(
    Map<Position, DefaultPiece> grid,
    int moves
  ) {
    try {
      var move = position().move(WALK_DIRECTION, moves);
      if (grid.get(move.newPosition()) == null) {
        return Optional.of(move);
      }
    } catch (PositionNotReachable ignorable) {}
    return Optional.empty();
  }

  private Collection<Move> resolveAttackPossibilities(
    Map<Position, DefaultPiece> grid
  ) {
    Collection<Move> possibleMoves = Lists.newArrayList();
    resolveRightAttackMove(grid).ifPresent(possibleMoves::add);
    resolveLeftAttackMove(grid).ifPresent(possibleMoves::add);
    return possibleMoves;
  }

  private static final Direction RIGHT_ATTACK_DIRECTION
    = Direction.RIGHT_UP_DIAGONAL;

  private Optional<Move> resolveRightAttackMove(
    Map<Position, DefaultPiece> grid
  ) {
    return resolveAttackMove(grid, RIGHT_ATTACK_DIRECTION);
  }

  private static final Direction LEFT_ATTACK_DIRECTION
    = Direction.LEFT_UP_DIAGONAL;

  private Optional<Move> resolveLeftAttackMove(
    Map<Position, DefaultPiece> grid
  ) {
    return resolveAttackMove(grid, LEFT_ATTACK_DIRECTION);
  }

  private Optional<Move> resolveAttackMove(
    Map<Position, DefaultPiece> grid,
    Direction direction
  ) {
    try {
      var attack = position().move(direction, 1);
      if (grid.get(attack.newPosition()) != null
        && grid.get(attack.newPosition()).team() != team()) {
        return Optional.of(attack);
      }
    } catch (PositionNotReachable ignorable) {}
    return Optional.empty();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder extends AbstractPieceBuilder<Builder> {
    private Builder() {}

    public Pawn createPawn() {
      return new Pawn(position(), team());
    }
  }
}