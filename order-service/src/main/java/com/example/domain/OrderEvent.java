package com.example.domain;

import com.example.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private EventType type;
    private Order order;
}
