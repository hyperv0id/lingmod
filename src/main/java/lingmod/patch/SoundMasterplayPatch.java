package lingmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.Settings;
import lingmod.ModCore;

import java.util.HashMap;

import static lingmod.ModCore.makeVoicePath;

@SpirePatch(
    cls = "com.megacrit.cardcrawl.audio.SoundMaster",
    method = "play",
    paramtypes = {"java.lang.String", "boolean"}
)
public class SoundMasterplayPatch {
    public static HashMap<String, Sfx> map = new HashMap();

    public SoundMasterplayPatch() {
    }

    public static long Postfix(long res, SoundMaster _inst, String key, boolean useBgmVolume) {
        if (map.containsKey(key)) {
            return useBgmVolume
                ? ((Sfx)map.get(key)).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME)
                : ((Sfx)map.get(key)).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME);
        } else {
            return res;
        }
    }

    private static Sfx load(String filename) {
        return new Sfx(makeVoicePath("") + filename, false);
    }

    static {
        map.put("cn_topolect/victory_1", load("cn_topolect/victory_1.ogg"));
        map.put("cn_topolect/行动失败", load("cn_topolect/行动失败.ogg"));
    }
}
