package lingmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.Settings;
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
        map.put("cn_topolect/3星结束行动", load("cn_topolect/3星结束行动.ogg"));
        map.put("cn_topolect/行动失败", load("cn_topolect/行动失败.ogg"));
        map.put("cn_topolect/行动开始", load("cn_topolect/行动开始.ogg"));
        map.put("cn_topolect/作战中1", load("cn_topolect/作战中1.ogg"));
        map.put("cn_topolect/作战中2", load("cn_topolect/作战中2.ogg"));
        map.put("cn_topolect/作战中3", load("cn_topolect/作战中3.ogg"));
        map.put("cn_topolect/作战中4", load("cn_topolect/作战中4.ogg"));
        map.put("cn_topolect/选中干员1", load("cn_topolect/选中干员1.ogg"));
        map.put("cn_topolect/选中干员2", load("cn_topolect/选中干员2.ogg"));
        map.put("cn_topolect/行动出发", load("cn_topolect/行动出发.ogg"));
    }
}
