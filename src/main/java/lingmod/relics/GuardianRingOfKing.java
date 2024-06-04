package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static lingmod.ModCore.makeID;

/**
 * 国王的护戒：格挡可用于下一场战斗
 */
public class GuardianRingOfKing extends KingRelic {
    public static final String ID = makeID(GuardianRingOfKing.class.getSimpleName());

    public GuardianRingOfKing() {
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
        if(checkDoExtra()) 
            addToBot(new GainBlockAction(AbstractDungeon.player, 5));
        this.counter = 0;
    }
}
