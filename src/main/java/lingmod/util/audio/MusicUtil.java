package lingmod.util.audio;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

public class MusicUtil {

    public static void changeBGM(String key) {
        ArrayList<MainMusic> mainTrack = ReflectionHacks.getPrivate(CardCrawlGame.music, CardCrawlGame.music.getClass(), "mainTrack");
        boolean found = false;
        for (MainMusic music : mainTrack) {
            if (!music.key.equals(key)) {
                music.fadeOut();
            } else {
                found = true;
            }
        }
        if (!found) {
            mainTrack.add(new MainMusic(key));
        }
    }
}
