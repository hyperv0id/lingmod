package lingmod.util;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import lingmod.util.audio.Dialect;
import lingmod.util.audio.ProAudio;
import lingmod.util.audio.Voices;

import static lingmod.ModCore.logger;
import static lingmod.util.ModConfig.dialect;

/**
 * 用于管理音效
 */
public class VoiceMaster {
    private static final long VOICE_GAP = 5000;
    private static final Voices[] COMBAT_VOICES = new Voices[]{
            Voices.VOICE_COMBAT_1,
            Voices.VOICE_COMBAT_2,
            Voices.VOICE_COMBAT_3,
            Voices.VOICE_COMBAT_4,
    };
    private static long lastTime = -1;

    public static void playSound(ProAudio p) {
        if (p.dialect == Dialect.NONE) {
            logger.info("不使用语音");
            return;
        }
        long now = System.currentTimeMillis();
        if (now - lastTime < VOICE_GAP) return;
        logger.info("Playing Audio: " + p);
        CardCrawlGame.sound.play(p.key(), true);
        lastTime = now;
    }

    public static void select() {
        ProAudio p;
        if (Math.random() > 0.5F) p = new ProAudio(dialect, Voices.VOICE_OPERATOR_SELECT_1);
        else p = new ProAudio(dialect, Voices.VOICE_OPERATOR_SELECT_2);
        playSound(p);
    }

    public static void beginBattle() {
        ProAudio p;
        if (Math.random() > 0.5F) p = new ProAudio(dialect, Voices.VOICE_ACTION_START);
        else p = new ProAudio(dialect, Voices.VOICE_ACTION_DEPART);
        playSound(p);
    }

    public static void death() {
        playSound(new ProAudio(dialect, Voices.VOICE_ACTION_FAIL));
    }

    public static void victory() {
        playSound(new ProAudio(dialect, Voices.VOICE_3_STAR_END));
    }

    public static void attack() {
        int r4 = (int) (Math.random() * 4);
        ProAudio p = new ProAudio(dialect, COMBAT_VOICES[r4]);
        playSound(p);
    }
}
