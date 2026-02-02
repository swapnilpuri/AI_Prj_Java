package com.example.command;

// 1. Command Interface
public interface Command {
    void execute();
    void undo(); // For undoable operations
}
