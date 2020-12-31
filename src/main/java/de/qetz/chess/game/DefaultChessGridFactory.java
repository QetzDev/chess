package de.qetz.chess.game;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

import de.qetz.chess.game.piece.DefaultPiece;
import de.qetz.chess.game.position.Position;
import de.qetz.chess.game.piece.concrete.*;
import de.qetz.chess.game.piece.Team;

public final class DefaultChessGridFactory {
  public static DefaultChessGridFactory newFactory() {
    return new DefaultChessGridFactory();
  }

  private Map<Position, DefaultPiece> grid;

  private DefaultChessGridFactory() {}

  public DefaultChessGridFactory withEmptyGrid() {
    this.grid = Maps.newHashMap();
    return this;
  }

  public DefaultChessGridFactory withDefaultGrid() {
    this.grid = Maps.newHashMap(DefaultChessGridMap.DEFAULT_GRID);
    return this;
  }

  public DefaultChessGridFactory updatePiece(
    Position position,
    DefaultPiece piece
  ) {
    Preconditions.checkNotNull(grid);
    Preconditions.checkNotNull(position);
    Preconditions.checkNotNull(piece);
    grid.put(position, piece);
    return this;
  }

  public DefaultChessGridFactory removePiece(Position position) {
    Preconditions.checkNotNull(position);
    grid.remove(position);
    return this;
  }

  public DefaultChessGrid createGrid() {
    Preconditions.checkNotNull(grid);
    return DefaultChessGrid.createDefault(grid);
  }

  public static final class DefaultChessGridMap {
    private static final ImmutableMap<Position, DefaultPiece> DEFAULT_GRID =
      ImmutableMap.<Position, DefaultPiece>builder()
        .put(Position.of(1, 1), Rook.newBuilder()
          .withPosition(Position.of(1, 1))
          .withTeam(Team.WHITE)
          .createRook())
        .put(Position.of(1, 2), Knight.newBuilder()
          .withPosition(Position.of(1, 2))
          .withTeam(Team.WHITE)
          .createKnight())
        .put(Position.of(1, 3), Bishop.newBuilder()
          .withPosition(Position.of(1, 3))
          .withTeam(Team.WHITE)
          .createBishop())
        .put(Position.of(1, 4), Queen.newBuilder()
          .withPosition(Position.of(1, 4))
          .withTeam(Team.WHITE)
          .createQueen())
        .put(Position.of(1, 5), King.newBuilder()
          .withPosition(Position.of(1, 5))
          .withTeam(Team.WHITE)
          .createKing())
        .put(Position.of(1, 6), Bishop.newBuilder()
          .withPosition(Position.of(1, 6))
          .withTeam(Team.WHITE)
          .createBishop())
        .put(Position.of(1, 7), Knight.newBuilder()
          .withPosition(Position.of(1, 7))
          .withTeam(Team.WHITE)
          .createKnight())
        .put(Position.of(1, 8), Rook.newBuilder()
          .withPosition(Position.of(1, 8))
          .withTeam(Team.WHITE)
          .createRook())

        .put(Position.of(2, 1), Pawn.newBuilder()
          .withPosition(Position.of(2, 1))
          .withTeam(Team.WHITE)
          .createPawn())
        .put(Position.of(2, 2), Pawn.newBuilder()
          .withPosition(Position.of(2, 2))
          .withTeam(Team.WHITE)
          .createPawn())
        .put(Position.of(2, 3), Pawn.newBuilder()
          .withPosition(Position.of(2, 3))
          .withTeam(Team.WHITE)
          .createPawn())
        .put(Position.of(2, 4), Pawn.newBuilder()
          .withPosition(Position.of(2, 4))
          .withTeam(Team.WHITE)
          .createPawn())
        .put(Position.of(2, 5), Pawn.newBuilder()
          .withPosition(Position.of(2, 5))
          .withTeam(Team.WHITE)
          .createPawn())
        .put(Position.of(2, 6), Pawn.newBuilder()
          .withPosition(Position.of(2, 6))
          .withTeam(Team.WHITE)
          .createPawn())
        .put(Position.of(2, 7), Pawn.newBuilder()
          .withPosition(Position.of(2, 7))
          .withTeam(Team.WHITE)
          .createPawn())
        .put(Position.of(2, 8), Pawn.newBuilder()
          .withPosition(Position.of(2, 8))
          .withTeam(Team.WHITE)
          .createPawn())

        .put(Position.of(7, 1), Pawn.newBuilder()
          .withPosition(Position.of(7, 1))
          .withTeam(Team.BLACK)
          .createPawn())
        .put(Position.of(7, 2), Pawn.newBuilder()
          .withPosition(Position.of(7, 2))
          .withTeam(Team.BLACK)
          .createPawn())
        .put(Position.of(7, 3), Pawn.newBuilder()
          .withPosition(Position.of(7, 3))
          .withTeam(Team.BLACK)
          .createPawn())
        .put(Position.of(7, 4), Pawn.newBuilder()
          .withPosition(Position.of(7, 4))
          .withTeam(Team.BLACK)
          .createPawn())
        .put(Position.of(7, 5), Pawn.newBuilder()
          .withPosition(Position.of(7, 5))
          .withTeam(Team.BLACK)
          .createPawn())
        .put(Position.of(7, 6), Pawn.newBuilder()
          .withPosition(Position.of(7, 6))
          .withTeam(Team.BLACK)
          .createPawn())
        .put(Position.of(7, 7), Pawn.newBuilder()
          .withPosition(Position.of(7, 7))
          .withTeam(Team.BLACK)
          .createPawn())
        .put(Position.of(7, 8), Pawn.newBuilder()
          .withPosition(Position.of(7, 8))
          .withTeam(Team.BLACK)
          .createPawn())

        .put(Position.of(8, 1), Rook.newBuilder()
          .withPosition(Position.of(8, 1))
          .withTeam(Team.BLACK)
          .createRook())
        .put(Position.of(8, 2), Knight.newBuilder()
          .withPosition(Position.of(8, 2))
          .withTeam(Team.BLACK)
          .createKnight())
        .put(Position.of(8, 3), Bishop.newBuilder()
          .withPosition(Position.of(8, 3))
          .withTeam(Team.BLACK)
          .createBishop())
        .put(Position.of(8, 4), Queen.newBuilder()
          .withPosition(Position.of(8, 4))
          .withTeam(Team.BLACK)
          .createQueen())
        .put(Position.of(8, 5), King.newBuilder()
          .withPosition(Position.of(8, 5))
          .withTeam(Team.BLACK)
          .createKing())
        .put(Position.of(8, 6), Bishop.newBuilder()
          .withPosition(Position.of(8, 6))
          .withTeam(Team.BLACK)
          .createBishop())
        .put(Position.of(8, 7), Knight.newBuilder()
          .withPosition(Position.of(8, 7))
          .withTeam(Team.BLACK)
          .createKnight())
        .put(Position.of(8, 8), Rook.newBuilder()
          .withPosition(Position.of(8, 8))
          .withTeam(Team.BLACK)
          .createRook())
        .build();
  }
}
