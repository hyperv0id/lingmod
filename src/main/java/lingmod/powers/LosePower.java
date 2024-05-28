package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import lingmod.ModCore;

/**
 * 回合开始时选择 x 张牌丢弃，类似少抽一张牌
 */
public class LosePower extends AbstractEasyPower{
    public static final String POWER_NAME = LosePower.class.getSimpleName();
    public static final String ID = ModCore.makeID(POWER_NAME);
    public static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(ID);

    public LosePower( AbstractCreature owner, int amount) {
        super(ID, ps.NAME, PowerType.DEBUFF, false, owner, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        // 选一张牌丢弃
        this.flash();
        addToBot(new DiscardAction(owner, owner, amount, false));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
