package com.example.command;

// A "null" object for commands, useful to initialize slots without checking for null
public class NoCommand implements Command {
    @Override
    public void execute() {
        // Do nothing
    }

    @Override
    public void undo() {
        // Do nothing
    }
}
