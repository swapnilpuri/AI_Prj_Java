package com.example.command;

// 3. Receiver: Another object that performs actions
public class Stereo {
    private String location;

    public Stereo(String location) {
        this.location = location;
    }

    public void on() {
        System.out.println(location + " stereo is ON");
    }

    public void off() {
        System.out.println(location + " stereo is OFF");
    }

    public void setCD() {
        System.out.println(location + " stereo is set to CD");
    }

    public void setVolume(int volume) {
        System.out.println(location + " stereo volume set to " + volume);
    }
}
