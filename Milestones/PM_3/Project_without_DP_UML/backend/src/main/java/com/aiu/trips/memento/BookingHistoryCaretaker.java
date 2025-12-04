package com.aiu.trips.memento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Memento Pattern - Caretaker for Booking history
 * Manages the history of booking states
 */
@Component
public class BookingHistoryCaretaker {
    
    @Autowired
    private BookingMementoFactory mementoFactory;
    
    private Stack<BookingMemento> history = new Stack<>();
    
    public void save(BookingMemento memento) {
        history.push(memento);
    }
    
    public BookingMemento undo() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null;
    }
    
    public BookingMemento peek() {
        if (!history.isEmpty()) {
            return history.peek();
        }
        return null;
    }
    
    public List<BookingMemento> getHistory() {
        return new ArrayList<>(history);
    }
    
    public void clearHistory() {
        history.clear();
    }
}
