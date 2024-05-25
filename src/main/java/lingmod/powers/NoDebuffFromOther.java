package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.BaseMod;
import basemod.interfaces.PostPowerApplySubscriber;

/**
 * 你不会被敌人DEBUFF到，被自己DEBUFF到时，失去此能力
 */
// @SpireInitializer
public class NoDebuffFromOther extends AbstractEasyPower implements PostPowerApplySubscriber{
    public static final String POWER_NAME = NoDebuffFromOther.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);

    public NoDebuffFromOther(AbstractPlayer p) {
        super(ID, POWER_NAME, PowerType.BUFF, false, p, 0);
        BaseMod.subscribe(this);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        BaseMod.unsubscribe(this);
    }
    
    @Override
    public void onVictory() {
        // TODO Auto-generated method stub
        super.onVictory();
        BaseMod.unsubscribe(this);
    }
    
    @Override
    public void receivePostPowerApplySubscriber(AbstractPower p2add, AbstractCreature target, AbstractCreature src) {
        if (p2add.type == PowerType.BUFF)
            return;
        if (target == owner) {
            if(src != owner) {
                this.flash();
                if(owner.powers.stream().map(p -> p.ID).anyMatch(id -> id == p2add.ID)) {
                    addToBot(new ReducePowerAction(target, owner, p2add, p2add.amount));
                } else {
                    addToBot(new RemoveSpecificPowerAction(target, owner, p2add));
                }
            } else {
                addToBot(new RemoveSpecificPowerAction(target, owner, this));
                BaseMod.unsubscribe(this);
            }
        }
    }
}
