package Bomberman.Sounds;
import static Bomberman.Constants.Constant.MAX_VOLUME;
import static com.almasb.fxgl.dsl.FXGL.getSettings;
import static com.almasb.fxgl.dsl.FXGL.showMessage;

public class SoundEffect {
    public static boolean isSoundEnabled = true;
    public static boolean isMusicEnabled = true;

    public static void turnOffMusic() {
        if (!isSoundEnabled) {
            return;
        }
        if (isMusicEnabled) {
            getSettings().setGlobalMusicVolume(0.0);
            isMusicEnabled = false;
        }
    }

    public static void turnOnMusic() {
        if (!isSoundEnabled) {
            return;
        }
        if (!isMusicEnabled) {
            getSettings().setGlobalMusicVolume(MAX_VOLUME);
            isMusicEnabled = true;
        }
    }

    public static void mute() {
        if (isSoundEnabled) {
            getSettings().setGlobalSoundVolume(0.0);
            getSettings().setGlobalMusicVolume(0.0);
            isSoundEnabled = false;
            isMusicEnabled = false;
        }
    }

    public static void unmute() {
        if (!isSoundEnabled) {
            getSettings().setGlobalSoundVolume(MAX_VOLUME);
            getSettings().setGlobalMusicVolume(MAX_VOLUME);
            isSoundEnabled = true;
            isMusicEnabled = true;
        }
    }

    public static void setSoundSwitch() {
        if (isSoundEnabled) {
            mute();
            showMessage("Sound disabled!");
        } else {
            unmute();
            showMessage("Sound enabled!");
        }
    }
}
