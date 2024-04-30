package lingmod.patch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.character.Ling;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeMusicPath;

public class MainMusicPatch {

    public MainMusicPatch() {
    }

    @SpirePatch(clz = MainMusic.class, method = "getSong")
    public static class getSongPatch {
        public getSongPatch() {
        }
        //        @SpirePrefixPatch
        public static Music Postfix(Music res, MainMusic _inst, String key) {
            String mName = "寻隐";
            String kName = "CHAR_SELECT";
            if (key.equals(makeID(kName)))
                return MainMusic.newMusic(makeMusicPath(mName + ".mp3"));
            if (AbstractDungeon.player != null && AbstractDungeon.player.chosenClass == Ling.Enums.PLAYER_LING)
                if (key.equals(kName))
                    return MainMusic.newMusic(makeMusicPath(mName + ".mp3"));
            return res;
        }
    }
//    @SpirePatch(clz = MainMusic.class, method = "newMusic")
//    public static class newMusicPatch {
//        public newMusicPatch() {
//        }
//        //        @SpirePrefixPatch
//        public static Music Postfix(Music res, MainMusic _inst, String path) {
//            String kName = "CHAR_SELECT";
//            String mName = "寻隐";
//            String mPath = makeMusicPath(mName + ".mp3");
//
//            if (path.equals(makeID(kName)))
//                return Gdx.audio.newMusic(Gdx.files.internal(mPath));
//            if (AbstractDungeon.player != null
//                    && AbstractDungeon.player.chosenClass == Ling.Enums.PLAYER_LING
//                    && path.equals(kName)
//            ) return Gdx.audio.newMusic(Gdx.files.internal(mPath));
//            return res;
//        }
//    }
}
