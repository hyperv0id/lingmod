package lingmod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 * 国王套：右键将生命值置为1,战斗开始时生命值为1时获得BUFF
 * TODO: 可能会在事件中掉血
 */
public abstract class KingRelic extends AbstractEasyRelic implements ClickableRelic {

    protected static boolean triggered = false;
    protected boolean doExtra = false; // 多个国王套的隐藏机制
    protected static final int TRIGGER_CNT = 3;

    public KingRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        super(setId, tier, sfx);
    }

    protected boolean checkDoExtra() {
        if (AbstractDungeon.player.relics.stream()
                .filter(r -> r instanceof KingRelic)
                .count() > TRIGGER_CNT) {
            doExtra = true;
        }
        return doExtra;
    }

    @Override
    public void onRightClick() {
        AbstractDungeon.player.currentHealth = 1;
        triggered = true;
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        return triggered ? healAmount : 0;
    }
}
