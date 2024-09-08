package lingmod.actions;

import java.util.Collections;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.BaseMod;

public class FastApplyPower_Action extends AbstractGameAction {
    private AbstractPower power;

    public FastApplyPower_Action(AbstractCreature target, AbstractCreature source, AbstractPower power, int amount) {
        this.setValues(target, source, amount);
        this.power = power;
        this.amount = amount;
    }

    public FastApplyPower_Action(AbstractCreature target, AbstractCreature source, AbstractPower power) {
        this(target, source, power, power.amount);
    }

    @Override
    public void update() {
        if (isDone)
            return;
        if (target.hasPower(power.ID)) {
            target.getPower(power.ID).stackPower(amount);
        } else {
            this.target.powers.add(this.power);
            Collections.sort(this.target.powers);
            this.power.onInitialApplication();
        }
        BaseMod.publishPostPowerApply(power, target, source);
        this.isDone = true;
    }
}
