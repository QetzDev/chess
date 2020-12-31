package de.qetz.chess.event;

import com.google.common.base.Preconditions;
import com.google.inject.Injector;
import com.google.inject.Inject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import de.qetz.chess.event.handler.EventHandlerRegistry;

public final class EventCalling {
  private static final Logger LOG = Logger.getLogger(EventCalling.class.getSimpleName());

  @Inject
  private static EventHandlerRegistry registry;
  @Inject
  private static Injector injector;

  private EventCalling() {}

  public static <T extends DefaultEvent> void callEvent(T event) {
    Preconditions.checkNotNull(event);
    registry.findEventHandlersForEvent(event.getClass())
      .ifPresent(methods -> {
        for (Method method : methods) {
          var methodClass = injector.getInstance(method.getDeclaringClass());
          executeMethod(method, methodClass, event);
        }
      });
  }

  private static void executeMethod(
    Method method,
    Object methodClass,
    Object event
  ) {
    try {
      method.invoke(methodClass, event);
    } catch (IllegalAccessException | InvocationTargetException notExecutable) {
      LOG.severe(String.format("Could not execute method: %s", method.getName()));
      LOG.severe(notExecutable.getMessage());
    }
  }
}
