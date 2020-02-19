package com.example;

public class Event<T> {

    public EventType type;

    public T payload;

    public Event() {}

    public Event(EventType type, T payload) {
        this.payload = payload;
        this.type = type;
    }

}
