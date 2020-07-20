package me.Oxygen.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Oxygen & Leakey Hacked Client
 * 
 * Event API
 * 
 * @author Pipi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTarget {
    EventPriority priority() default EventPriority.LOW;
    Class<? extends Event>[] events();
}