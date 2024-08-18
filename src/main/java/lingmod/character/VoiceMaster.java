package lingmod.character;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.util.audio.ProAudio;
import lingmod.util.audio.Voices;

import static lingmod.ModCore.logger;
import static lingmod.util.ModConfig.dialect;

/**
 * 用于管理音效
 */
public class VoiceMaster {
    private volatile static VoiceMaster instance;
    public AbstractCreature owner;
    protected String sound;

    private VoiceMaster() {
        owner = AbstractDungeon.player;
    }

    public static VoiceMaster getInstance() {
        if (instance == null) {
            synchronized (VoiceMaster.class) {
                if (instance == null || instance.owner == null) {
                    instance = new VoiceMaster();
                }
            }
        }
        return instance;
    }

    public void select() {
        ProAudio p;
        if (Math.random() > 0.5F) p = new ProAudio(dialect, Voices.VOICE_OPERATOR_SELECT_1);
        else p = new ProAudio(dialect, Voices.VOICE_OPERATOR_SELECT_2);
        logger.info("Playing Audio: " + p);
        CardCrawlGame.sound.play(p.key(), true);
    }

    public void beginBattle() {
        ProAudio p;
        if (Math.random() > 0.5F) p = new ProAudio(dialect, Voices.VOICE_ACTION_START);
        else p = new ProAudio(dialect, Voices.VOICE_ACTION_DEPART);
        CardCrawlGame.sound.play(p.key(), true);
    }

    public void death() {
        CardCrawlGame.sound.play(new ProAudio(dialect, Voices.VOICE_ACTION_FAIL).key(), true);
    }

    public void victory() {
        CardCrawlGame.sound.play(new ProAudio(dialect, Voices.VOICE_3_STAR_END).key(), true);
    }

    public void onAttack() {
        ProAudio p = new ProAudio(dialect, Voices.VOICE_COMBAT_1);
        int r4 = 1 + (int) (Math.random() * 4);
        switch (r4) {
            case 1:
                p = new ProAudio(dialect, Voices.VOICE_COMBAT_1);
                break;
            case 2:
                p = new ProAudio(dialect, Voices.VOICE_COMBAT_2);
                break;
            case 3:
                p = new ProAudio(dialect, Voices.VOICE_COMBAT_3);
                break;
            case 4:
                p = new ProAudio(dialect, Voices.VOICE_COMBAT_4);
                break;
        }
        CardCrawlGame.sound.play(p.key(), true);
    }
}
