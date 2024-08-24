package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.ShiftingPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ShiftingPower2 extends ShiftingPower {
    public ShiftingPower2(AbstractCreature owner) {
        super(owner);
        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        this.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -1), -1));
        if (!this.owner.hasPower("Artifact")) {
            this.addToTop(new ApplyPowerAction(this.owner, this.owner, new GainStrengthPower(this.owner, 1), 1));
        }

        this.flash();

        return damageAmount;
    }
}
