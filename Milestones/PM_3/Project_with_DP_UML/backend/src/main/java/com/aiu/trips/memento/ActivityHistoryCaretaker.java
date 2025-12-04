package com.aiu.trips.memento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Memento Pattern - Caretaker for Activity history
 * Manages the history of activity states
 */
@Component
public class ActivityHistoryCaretaker {
    
    @Autowired
    private ActivityMementoFactory mementoFactory;
    
    private Stack<ActivityMemento> history = new Stack<>();
    
    public void save(ActivityMemento memento) {
        history.push(memento);
    }
    
    public ActivityMemento undo() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null;
    }
    
    public ActivityMemento peek() {
        if (!history.isEmpty()) {
            return history.peek();
        }
        return null;
    }
    
    public List<ActivityMemento> getHistory() {
        return new ArrayList<>(history);
    }
    
    public void clearHistory() {
        history.clear();
    }
}
