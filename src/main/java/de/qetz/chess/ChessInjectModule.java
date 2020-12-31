package de.qetz.chess;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.Provides;

import de.qetz.chess.scanning.ClassScanner;
import de.qetz.chess.logging.LoggerFactory;
import de.qetz.chess.logging.Logger;

public final class ChessInjectModule extends AbstractModule {
  public static ChessInjectModule createOf(Chess chess) {
    Preconditions.checkNotNull(chess);
    return new ChessInjectModule(chess);
  }

  private final Chess chess;

  private ChessInjectModule(Chess chess) {
    this.chess = chess;
  }

  @Override
  protected void configure() {
    bind(Chess.class).toInstance(chess);
  }

  @Singleton
  @Provides
  Logger createDefaultLogger() {
    return LoggerFactory.newLogger()
      .createLogger();
  }

  @Singleton
  @Provides
  ClassScanner createClassScanner() {
    return ClassScanner.createInPackageRecursive(getClass().getPackageName());
  }
}
