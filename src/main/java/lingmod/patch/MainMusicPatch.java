package lingmod.patch;

import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.audio.MainMusic;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeMusicPath;

public class MainMusicPatch {

    public MainMusicPatch() {
    }

    @SpirePatch(clz = MainMusic.class, method = "getSong")
    public static class getSongPatch {
        public getSongPatch() {
        }

        @SpirePostfixPatch
        public static Music Postfix(Music _res, MainMusic _inst, String key) {
            if (!key.startsWith(makeID(""))) return _res;
            String realKey = key.split(":")[1];
            Music music = MainMusic.newMusic(makeMusicPath(realKey));
            return music == null ? _res : music;
        }
    }

}
