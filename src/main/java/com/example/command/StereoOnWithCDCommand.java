package com.example.command;

// 2. Concrete Command: Turns a stereo on, sets CD, and volume
public class StereoOnWithCDCommand implements Command {
    private Stereo stereo;
    private int prevVolume;

    public StereoOnWithCDCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.on();
        stereo.setCD();
        prevVolume = 10; // Store previous volume for undo
        stereo.setVolume(prevVolume);
    }

    @Override
    public void undo() {
        stereo.off();
    }
}
