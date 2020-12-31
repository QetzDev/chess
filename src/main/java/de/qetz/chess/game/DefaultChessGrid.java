package de.qetz.chess.game;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Optional;
import java.util.Map;

import de.qetz.chess.event.concrete.GameFinishedTrigger;
import de.qetz.chess.game.piece.concrete.King;
import de.qetz.chess.game.piece.DefaultPiece;
import de.qetz.chess.game.position.Position;
import de.qetz.chess.event.EventCalling;
import de.qetz.chess.game.position.Move;
import de.qetz.chess.game.piece.Team;

public final class DefaultChessGrid implements Grid {
  public static final int MAX_VALUE = 8;
  public static final int MIN_VALUE = 1;

  private static final Team DEFAULT_START_TEAM = Team.WHITE;

  static DefaultChessGrid createDefault(Map<Position, DefaultPiece> grid) {
    Preconditions.checkNotNull(grid);
    DefaultChessGrid chessGrid = new DefaultChessGrid(grid, DEFAULT_START_TEAM);
    chessGrid.prepareNextMove();
    return chessGrid;
  }

  private final Map<Position, DefaultPiece> grid;
  private Map<Position, Collection<Move>> currentPossibleMoves;
  private Team playingTeam;

  private DefaultChessGrid(Map<Position, DefaultPiece> grid, Team playingTeam) {
    this.grid = grid;
    this.playingTeam = playingTeam;
  }

  @Override
  public Team playingTeam() {
    return playingTeam;
  }

  @Override
  public void executeMove(Move move) {
    Preconditions.checkNotNull(move);

    this.playingTeam = resolveNextTeam(playingTeam);
    resolvePiece(move.oldPosition()).ifPresent(piece ->
      piece.move(move.newPosition()));

    grid.put(move.newPosition(), grid.get(move.oldPosition()));
    grid.remove(move.oldPosition());

    executeCastling(move);
    prepareNextMove();
  }

  private void prepareNextMove() {
    checkForOnlyTwoPieces();

    currentPossibleMoves = listAllPossibleMovesForTeam(playingTeam);
  }

  private Team resolveNextTeam(Team oldTeam) {
    return oldTeam == Team.WHITE ? Team.BLACK : Team.WHITE;
  }

  private static final int LEFT_CASTLING_ROW = 2;
  private static final int RIGHT_CASTLING_ROW = 7;

  private void executeCastling(Move move) {
    if (grid.get(move.newPosition()) instanceof King) {
      if (move.moves() > 1) {
        if (move.newPosition().row() == LEFT_CASTLING_ROW) {
          executeLeftCastling(move.newPosition().column());
        } else if (move.newPosition().row() == RIGHT_CASTLING_ROW) {
          executeRightCastling(move.newPosition().column());
        }
      }
    }
  }

  private static final int RIGHT_CASTLING_OLD_ROOK_POSITION_ROW = 8;
  private static final int RIGHT_CASTLING_NEW_ROOK_POSITION_ROW = 6;

  private void executeRightCastling(int column) {
    Position oldPosition =
      Position.of(column, RIGHT_CASTLING_OLD_ROOK_POSITION_ROW);
    Position newPosition =
      Position.of(column, RIGHT_CASTLING_NEW_ROOK_POSITION_ROW);
    grid.get(oldPosition).move(newPosition);
    grid.put(newPosition, grid.get(oldPosition));
    grid.remove(oldPosition);
  }

  private static final int LEFT_CASTLING_OLD_ROOK_POSITION_ROW = 1;
  private static final int LEFT_CASTLING_NEW_ROOK_POSITION_ROW = 3;

  private void executeLeftCastling(int column) {
    Position oldPosition =
      Position.of(column, LEFT_CASTLING_OLD_ROOK_POSITION_ROW);
    Position newPosition =
      Position.of(column, LEFT_CASTLING_NEW_ROOK_POSITION_ROW);
    grid.get(oldPosition).move(newPosition);
    grid.put(newPosition, grid.get(oldPosition));
    grid.remove(oldPosition);
  }

  private Optional<DefaultPiece> resolvePiece(Position position) {
    Preconditions.checkNotNull(position);
    return Optional.ofNullable(grid.get(position));
  }

  private void checkForOnlyTwoPieces() {
    if (grid.size() <= 2) {
      EventCalling.callEvent(GameFinishedTrigger.createWith(
        GameFinishedTrigger.GameFinishedReason.STALEMATE,
        GameFinishedTrigger.GameWinner.NOBODY
      ));
    }
  }

  @Override
  public Collection<Move> listPossibleMovesForPiece(Position position) {
    Preconditions.checkNotNull(position);
    return grid.containsKey(position)
      ? grid.get(position).calculatePossibleMoves(Maps.newHashMap(grid))
      : Lists.newArrayList();
  }

  @Override
  public Map<Position, Collection<Move>> listAllPossibleMovesForCurrentTeam() {
    return currentPossibleMoves;
  }

  private Map<Position, Collection<Move>> listAllPossibleMovesForTeam(
    Team team
  ) {
    Preconditions.checkNotNull(team);
    Collection<Move> enemyMoves = allPossibleMovesOfTeam(
      resolveNextTeam(playingTeam));
    if (isInCheck(enemyMoves)) {
      return calculateCheckMoves(enemyMoves);
    } else {
      return calculateNormalMoves(enemyMoves, team);
    }
  }

  private boolean isInCheck(
    Collection<Move> enemyMoves
  ) {
    Position kingPosition = resolveKingPosition(playingTeam);
    return enemyMoves.stream()
      .anyMatch(move -> move.newPosition().equals(kingPosition));
  }

  private Map<Position, Collection<Move>> calculateCheckMoves(
    Collection<Move> enemyMoves
  ) {
    Position kingPosition = resolveKingPosition(playingTeam);
    Collection<Move> kingMoves = listPossibleMovesForPiece(kingPosition);

    kingMoves.removeIf(kingMove -> enemyMoves.stream()
      .anyMatch(enemyMove -> enemyMove.newPosition()
        .equals(kingMove.newPosition())));
    return resolveCheckResult(kingMoves, kingPosition);
  }

  private Map<Position, Collection<Move>> resolveCheckResult(
    Collection<Move> possibleKingMoves,
    Position kingPosition
  ) {
    if (possibleKingMoves.size() == 0) {
      EventCalling.callEvent(GameFinishedTrigger.createWith(
        GameFinishedTrigger.GameFinishedReason.WIN,
        GameFinishedTrigger.GameWinner.ofTeam(resolveNextTeam(playingTeam))));
      return mapToMovesMap(kingPosition, Lists.newArrayList());
    } else {
      return mapToMovesMap(kingPosition, possibleKingMoves);
    }
  }

  private Map<Position, Collection<Move>> mapToMovesMap(
    Position kingPosition,
    Collection<Move> moves
  ) {
    Map<Position, Collection<Move>> possibleMoves = Maps.newHashMap();
    possibleMoves.put(kingPosition, moves);
    return possibleMoves;
  }

  private Position resolveKingPosition(Team team) {
    for (Position position : grid.keySet()) {
      var currentPiece = grid.get(position);
      if (currentPiece.team() == team && currentPiece instanceof King) {
        return position;
      }
    }
    throw new IllegalStateException();
  }

  private Collection<Move> allPossibleMovesOfTeam(Team team) {
    Collection<Move> allMoves = Lists.newArrayList();
    for (Collection<Move> moves :
      calculateNormalMoves(Lists.newArrayList(), team).values()) {
      allMoves.addAll(moves);
    }
    return allMoves;
  }

  private Map<Position, Collection<Move>> calculateNormalMoves(
    Collection<Move> enemyMoves,
    Team team
  ) {
    Map<Position, Collection<Move>> possibleMoves = Maps.newHashMap();
    for (Position position : grid.keySet()) {
      if (grid.get(position) != null) {
        if (grid.get(position).team() == team) {
          possibleMoves.put(position, listPossibleMovesForPiece(position));
        }
      }
    }
    removeNotAllowedKingMoves(possibleMoves, enemyMoves);
    return possibleMoves;
  }

  private void removeNotAllowedKingMoves(
    Map<Position, Collection<Move>> moves,
    Collection<Move> enemyMoves
  ) {
    Position kingPosition = resolveKingPosition(playingTeam);
    if (moves.containsKey(kingPosition)) {
      Collection<Move> kingMoves = moves.get(kingPosition);

      kingMoves.removeIf(kingMove -> enemyMoves.stream()
        .anyMatch(enemyMove -> enemyMove.newPosition()
          .equals(kingMove.newPosition())));
      moves.replace(kingPosition, kingMoves);
    }
    if (moves.size() == 0) {
      EventCalling.callEvent(GameFinishedTrigger
        .createWith(GameFinishedTrigger.GameFinishedReason.DRAW,
          GameFinishedTrigger.GameWinner.ofTeam(resolveNextTeam(playingTeam))));
    }
  }
}