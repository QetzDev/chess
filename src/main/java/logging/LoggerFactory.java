package logging;

import com.google.common.base.Preconditions;

public final class LoggerFactory {
  public static LoggerFactory newLogger() {
    return new LoggerFactory();
  }

  private Logger.LogLevel printLevel = DEFAULT_PRINT_LEVEL;
  private String logFormat = DEFAULT_LOG_FORMAT;

  private LoggerFactory() {}

  private static final Logger.LogLevel DEFAULT_PRINT_LEVEL
    = Logger.LogLevel.INFO;

  public LoggerFactory withPrintLevel(Logger.LogLevel printLevel) {
    this.printLevel = printLevel;
    return this;
  }

  private static final String DEFAULT_LOG_FORMAT = "[%s - %s]: %s";

  // First %s is the time, second %s the log level and third %s the message
  public LoggerFactory withLogFormat(String logFormat) {
    this.logFormat = logFormat;
    return this;
  }

  public Logger createLogger() {
    Preconditions.checkNotNull(printLevel);
    Preconditions.checkNotNull(logFormat);
    return new Logger(printLevel, logFormat);
  }
}