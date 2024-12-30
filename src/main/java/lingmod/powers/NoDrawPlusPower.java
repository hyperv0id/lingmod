package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;

public class NoDrawPlusPower extends NoDrawPower {
    public NoDrawPlusPower(AbstractCreature owner) {
        super(owner);
    }

    /**
     * 持续amount回合
     */
    public NoDrawPlusPower(AbstractCreature owner, int amount) {
        super(owner);
        this.amount = amount;
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && this.amount-- >= 0) {
            this.amount--;
        } else {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

}
