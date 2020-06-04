package edu.sharif.student.bluesoheil.ap98.hearthstone.util;

import javax.sound.sampled.*;
import java.io.File;

public class SoundLoader {

//    public enum sounds{Open}

    public static void playSound(String path) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream sound = AudioSystem.getAudioInputStream(new File(path));
            clip.open(sound);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decreaseSound(){}

    public static void increaseSound(){}
}