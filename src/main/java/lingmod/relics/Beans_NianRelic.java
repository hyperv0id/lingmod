package lingmod.relics;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

/**
 * 每回合 3 活力
 */
public class Beans_NianRelic extends AbstractEasyRelic{
    public static final String ID = makeID(Beans_NianRelic.class.getSimpleName());

    public  Beans_NianRelic(){
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);

    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        this.flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, 3)));
    }
}