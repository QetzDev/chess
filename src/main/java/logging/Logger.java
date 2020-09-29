package logging;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Logger {
  public enum LogLevel {
    DEBUG(false, 0),
    INFO(false, 1),
    WARNING(true, 2),
    ERROR(true, 3);

    private final boolean useErrorPrinting;
    private final int level;

    LogLevel(boolean useErrorPrinting, int level) {
      this.useErrorPrinting = useErrorPrinting;
      this.level = level;
    }

    public boolean useErrorPrinting() {
      return useErrorPrinting;
    }

    public int level() {
      return level;
    }
  }

  private final LogLevel printLevel;
  private final String logFormat;

  protected Logger(
    LogLevel printLevel,
    String logFormat
  ) {
    this.printLevel = printLevel;
    this.logFormat = logFormat;
  }


  public void debug(String message, Object... substitutes) {
    var replacedMessage = String.format(message, substitutes);
    printMessage(message, LogLevel.DEBUG);
  }

  public void log(String message, Object... substitutes) {
    var replacedMessage = String.format(message, substitutes);
    printMessage(message, LogLevel.INFO);
  }

  public void warn(String message, Object... substitutes) {
    var replacedMessage = String.format(message, substitutes);
    printMessage(message, LogLevel.WARNING);
  }

  public void fail(String message, Object... substitutes) {
    var replacedMessage = String.format(message, substitutes);
    printMessage(message, LogLevel.ERROR);
  }

  private void printMessage(String message, LogLevel urgency) {
    if(printLevel.level() >= urgency.level()) {
      var formattedMessage = formatMessage(urgency, message);
      resolvePrintStream(urgency).println(formattedMessage);
    }
  }

  private PrintStream resolvePrintStream(LogLevel urgency) {
    return urgency.useErrorPrinting()
      ? System.out
      : System.err;
  }

  private static final String TIME_FORMAT = "HH:mm:ss";
  private static final SimpleDateFormat DATE_CREATOR
    = new SimpleDateFormat(TIME_FORMAT);

  private String formatMessage(LogLevel urgency, String message) {
    var time = DATE_CREATOR.format(new Date());
    return String.format(logFormat, time, urgency.name(), message);
  }
}