package com.example;

import java.io.Serializable;

public class Event<T> implements Serializable {

    private EventType type;

    private T payload;

    public Event() {}

    public Event(EventType type, T payload) {
        this.payload = payload;
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public T getPayload() {
        return payload;
    }
}
