package lingmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RemovePowerAction extends AbstractGameAction {
    private final AbstractPower.PowerType powerType;
    private AbstractCreature c;

    public RemovePowerAction(AbstractCreature c, AbstractPower.PowerType powerType) {
        this.powerType = powerType;
        this.c = c;
        this.duration = 0.5F;
    }

    public void update() {
        for (AbstractPower p : c.powers) {
            if (p.type == powerType)
                this.addToTop(new RemoveSpecificPowerAction(this.c, this.c, p));
        }
    }
}