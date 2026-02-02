package com.example.command;

// 2. Concrete Command: Turns a stereo off
public class StereoOffCommand implements Command {
    private Stereo stereo;

    public StereoOffCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.off();
    }

    @Override
    public void undo() {
        stereo.on(); // For simplicity, undo just turns it back on
    }
}
