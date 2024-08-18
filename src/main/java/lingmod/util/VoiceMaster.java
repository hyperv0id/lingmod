package lingmod.util;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import lingmod.util.audio.ProAudio;
import lingmod.util.audio.Voices;

import static lingmod.ModCore.logger;
import static lingmod.util.ModConfig.dialect;

/**
 * 用于管理音效
 */
public class VoiceMaster {
    private static final long VOICE_GAP = 10000;
    private static final Voices[] COMBAT_VOICES = new Voices[]{
            Voices.VOICE_COMBAT_1,
            Voices.VOICE_COMBAT_2,
            Voices.VOICE_COMBAT_3,
            Voices.VOICE_COMBAT_4,
    };
    private static long lastTime = -1;

    public static void playSound(ProAudio p) {
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
        CardCrawlGame.sound.play(new ProAudio(dialect, Voices.VOICE_ACTION_FAIL).key(), true);
    }

    public static void victory() {
        CardCrawlGame.sound.play(new ProAudio(dialect, Voices.VOICE_3_STAR_END).key(), true);
    }

    public static void attack() {
        int r4 = (int) (Math.random() * 4);
        ProAudio p = new ProAudio(dialect, COMBAT_VOICES[r4]);
        playSound(p);
    }
}
