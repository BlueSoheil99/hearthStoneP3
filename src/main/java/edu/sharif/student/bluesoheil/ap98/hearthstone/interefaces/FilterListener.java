package edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.collection.FilterEvent;

import java.util.EventListener;

public interface FilterListener extends EventListener {
    void filter(FilterEvent filterEvent);
}
