package de.qetz.chess.event.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

import java.util.stream.Collectors;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import de.qetz.chess.scanning.ClassScanner;
import de.qetz.chess.event.DefaultEvent;

public final class EventHandlerScanning {
  private final ClassScanner scanner;

  @Inject
  private EventHandlerScanning(ClassScanner scanner) {
    this.scanner = scanner;
  }

  public EventHandlerRegistry scanAndCreateRegistry() {
    Map<Class<? extends DefaultEvent>, Collection<Method>> eventHandlers =
      resolveEventHandlers();
    return EventHandlerRegistry.createOfMap(eventHandlers);
  }

  private Map<Class<? extends DefaultEvent>, Collection<Method>>
    resolveEventHandlers()
  {
    Map<Class<? extends DefaultEvent>, Collection<Method>> eventHandlers =
      Maps.newHashMap();
    for (var event : resolveEvents()) {
      eventHandlers.put(event,
        resolveMethodsForEvent(event, resolveEventListener()));
    }
    return eventHandlers;
  }

  private Collection<Class<? extends DefaultEvent>> resolveEvents() {
    return scanner.findSubTypes(DefaultEvent.class)
      .collect(Collectors.toList());
  }

  private Collection<Class<?>> resolveEventListener() {
    return scanner.findAnnotated(EventListener.class)
      .collect(Collectors.toList());
  }

  private Collection<Method> resolveMethodsForEvent(
    Class<?> event,
    Collection<Class<?>> listeners
  ) {
    Collection<Method> methods = Lists.newArrayList();
    for (Class<?> listener : listeners) {
      var listenerMethods =
        Lists.newArrayList(listener.getDeclaredMethods());
      methods.addAll(filterForEventHandlers(event, listenerMethods));
    }
    return methods;
  }

  private Collection<Method> filterForEventHandlers(
    Class<?> event,
    Collection<Method> methods
  ) {
    return methods.stream()
      .filter(method -> method.getModifiers() == Modifier.PUBLIC)
      .filter(method -> method.isAnnotationPresent(EventHandler.class))
      .filter(method -> method.getParameterTypes().length == 1)
      .filter(method -> method.getParameterTypes()[0] == event)
      .collect(Collectors.toList());
  }
}
