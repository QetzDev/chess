package de.qetz.chess.game.position;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import de.qetz.chess.game.position.direction.Direction;

// Have to use a main-method and not the normal test environment
// because IntelliJ, Gradle & Junit combined doesn't work with Java 14
// respectively with Java preview Features.
public final class PositionTest {
  private static final Logger LOG = Logger.getLogger(PositionTest.class.getSimpleName());

  // From this position every move can be executed (or at least a certain times)
  private static final Position BASIC_MIDDLE_POSITION = Position.of(4, 4);

  public static void main(String[] args) {
    testForwardMove();
  }

  @Test
  public static void testForwardMove() {
    try {
      BASIC_MIDDLE_POSITION.move(Direction.FORWARD, 2);
      LOG.info("Moved successfully FORWARD");
    } catch (PositionNotReachable positionNotReachable) {
      LOG.severe(positionNotReachable.getMessage());
    }
  }
}
