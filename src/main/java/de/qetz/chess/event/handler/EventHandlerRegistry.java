package de.qetz.chess.event.handler;

import com.google.common.base.Preconditions;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;
import java.util.Map;

import de.qetz.chess.event.DefaultEvent;

public final class EventHandlerRegistry {
  public static EventHandlerRegistry createOfMap(
    Map<Class<? extends DefaultEvent>, Collection<Method>> eventHandlers
  ) {
    Preconditions.checkNotNull(eventHandlers);
    return new EventHandlerRegistry(eventHandlers);
  }

  private final Map<Class<? extends DefaultEvent>, Collection<Method>>
    eventHandlers;

  private EventHandlerRegistry(
    Map<Class<? extends DefaultEvent>, Collection<Method>> eventHandlers
  ) {
    this.eventHandlers = eventHandlers;
  }

  public Optional<Collection<Method>> findEventHandlersForEvent(
    Class<? extends DefaultEvent> event
  ) {
    Preconditions.checkNotNull(event);
    return Optional.ofNullable(eventHandlers.get(event));
  }
}
