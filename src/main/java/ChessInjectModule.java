import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import logging.Logger;
import logging.LoggerFactory;

public final class ChessInjectModule extends AbstractModule {
  @Singleton
  @Provides
  Logger createDefaultLogger() {
    return LoggerFactory.newLogger()
      .createLogger();
  }
}
