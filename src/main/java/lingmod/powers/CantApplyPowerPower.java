package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.BaseMod;
import basemod.interfaces.PostPowerApplySubscriber;

/**
 * 你无法再获得任何能力/异常
 */
// @SpireInitializer
public class CantApplyPowerPower extends AbstractEasyPower implements PostPowerApplySubscriber {
    public static final String POWER_NAME = CantApplyPowerPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(ID);

    public CantApplyPowerPower(AbstractCreature owner) {
        super(ID, ps.NAME, PowerType.DEBUFF, false, owner, 0);
        BaseMod.subscribe(this);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        BaseMod.unsubscribe(this);
    }

    @Override
    public void onVictory() {
        super.onVictory();
        BaseMod.unsubscribe(this);
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower p2add, AbstractCreature target, AbstractCreature src) {
        // if (p2add.type == PowerType.BUFF)
        // return;
        if (target == owner) {
            // if(src != owner) {
            this.flash();
            if (owner.powers.stream().map(p -> p.ID).anyMatch(id -> id == p2add.ID)) {
                addToBot(new ReducePowerAction(target, owner, p2add, p2add.amount));
            } else {
                addToBot(new RemoveSpecificPowerAction(target, owner, p2add));
            }
        }
        // } else {
        // addToBot(new RemoveSpecificPowerAction(target, owner, this));
        // BaseMod.unsubscribe(this);
        // }
    }
}