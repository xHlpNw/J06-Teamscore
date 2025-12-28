package ru.teamscore.producer;

public class ProducerApp {
    public static void main(String[] args) {
        SensorMessageGenerator generator = new SensorMessageGenerator();
        generator.run();
    }
}
