package com.example.command;

// 5. Client: Creates concrete commands and sets their receiver
public class CommandMain {
    public static void main(String[] args) {
        RemoteControl remoteControl = new RemoteControl();

        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        Stereo stereo = new Stereo("Living Room");

        // Create command objects
        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
        LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);

        StereoOnWithCDCommand stereoOnWithCD = new StereoOnWithCDCommand(stereo);
        StereoOffCommand stereoOff = new StereoOffCommand(stereo);

        // Load commands into remote control slots
        remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remoteControl.setCommand(1, kitchenLightOn, kitchenLightOff);
        remoteControl.setCommand(2, stereoOnWithCD, stereoOff);

        System.out.println(remoteControl);

        // Press buttons
        System.out.println("\n--- Pressing On Button for Living Room Light ---");
        remoteControl.onButtonWasPushed(0);
        System.out.println("\n--- Pressing Off Button for Living Room Light ---");
        remoteControl.offButtonWasPushed(0);
        System.out.println("\n--- Pressing Undo Button ---");
        remoteControl.undoButtonWasPushed(); // Should turn light back on

        System.out.println("\n--- Pressing On Button for Stereo ---");
        remoteControl.onButtonWasPushed(2);
        System.out.println("\n--- Pressing Off Button for Stereo ---");
        remoteControl.offButtonWasPushed(2);
        System.out.println("\n--- Pressing Undo Button ---");
        remoteControl.undoButtonWasPushed(); // Should turn stereo back on

        System.out.println("\n--- Pressing On Button for Kitchen Light ---");
        remoteControl.onButtonWasPushed(1);
        System.out.println("\n--- Pressing Undo Button ---");
        remoteControl.undoButtonWasPushed(); // Should turn kitchen light off
    }
}
