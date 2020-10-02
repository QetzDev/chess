package de.qetz.chess;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import de.qetz.chess.logging.Logger;
import de.qetz.chess.logging.LoggerFactory;

public final class ChessInjectModule extends AbstractModule {
  @Singleton
  @Provides
  Logger createDefaultLogger() {
    return LoggerFactory.newLogger()
      .createLogger();
  }
}
