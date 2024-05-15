package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import static lingmod.ModCore.makeID;

/**
 * 小血瓶：格挡可用于下一场战斗
 */
public class AltXiaoXuePing extends AbstractEasyRelic {
    public static final String ID = makeID(AltXiaoXuePing.class.getSimpleName());

    public AltXiaoXuePing() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onVictory() {
        super.onVictory();
        AbstractPlayer player = AbstractDungeon.player;
        this.counter = player.currentBlock;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        addToBot(new GainBlockAction(AbstractDungeon.player, this.counter));
        this.counter = 0;
    }
}
