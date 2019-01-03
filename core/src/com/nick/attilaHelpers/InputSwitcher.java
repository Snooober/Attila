package com.nick.attilaHelpers;

import java.util.ArrayList;
import java.util.List;

public abstract class InputSwitcher {
    private List<InputSwitchListener> listeners = new ArrayList<InputSwitchListener>();

    public void attach(final InputSwitchListener listener) {
        listeners.add(listener);
    }

    public void
}
