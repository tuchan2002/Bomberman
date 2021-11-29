package Bomberman.Sounds;

import static Bomberman.Constants.Constanst.MAX_VOLUME;
import static com.almasb.fxgl.dsl.FXGL.getSettings;
import static com.almasb.fxgl.dsl.FXGL.showMessage;

public class SoundEffect {
    public static boolean sound_enabled = true;
    public static boolean music_enabled = true;

    public static void turnOffMusic() {
        if (!sound_enabled) {
            return;
        }
        if (music_enabled) {
            getSettings().setGlobalMusicVolume(0.0);
            music_enabled = false;
        }
    }

    public static void turnOnMusic() {
        if (!sound_enabled) {
            return;
        }
        if (!music_enabled) {
            getSettings().setGlobalMusicVolume(MAX_VOLUME);
            music_enabled = true;
        }
    }

    public static void mute() {
        if (sound_enabled) {
            getSettings().setGlobalSoundVolume(0.0);
            getSettings().setGlobalMusicVolume(0.0);
            sound_enabled = false;
            music_enabled = false;
        }
    }

    public static void unmute() {
        if (!sound_enabled) {
            getSettings().setGlobalSoundVolume(MAX_VOLUME);
            getSettings().setGlobalMusicVolume(MAX_VOLUME);
            sound_enabled = true;
            music_enabled = true;
        }
    }

    public static void setSoundEnabled() {
        if (sound_enabled) {
            mute();
            showMessage("Sound disabled!");
        } else {
            unmute();
            showMessage("Sound enabled!");
        }
    }
}
