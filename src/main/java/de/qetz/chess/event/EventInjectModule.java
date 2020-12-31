package de.qetz.chess.event;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.Provides;
import com.google.inject.Inject;

import de.qetz.chess.event.handler.EventHandlerRegistry;
import de.qetz.chess.event.handler.EventHandlerScanning;

public final class EventInjectModule extends AbstractModule {
  public static EventInjectModule createInjectModule() {
    return new EventInjectModule();
  }

  private EventInjectModule() {}

  @Override
  protected void configure() {
    requestStaticInjection(EventCalling.class);
  }

  @Inject
  @Provides
  @Singleton
  EventHandlerRegistry createRegistry(EventHandlerScanning scanning) {
    return scanning.scanAndCreateRegistry();
  }
}
