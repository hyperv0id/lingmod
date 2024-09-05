package lingmod.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.ShiftingPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lingmod.ModCore;
import lingmod.actions.MyApplyPower_Action;

public class ShiftingPower2 extends ShiftingPower {
    public static final String ID = ModCore.makeID(ShiftingPower2.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public ShiftingPower2(AbstractCreature owner) {
        super(owner);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
        super.updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        this.addToTop(new MyApplyPower_Action(this.owner, this.owner, new StrengthPower(this.owner, -1), -1));
        if (!this.owner.hasPower("Artifact")) {
            this.addToTop(new MyApplyPower_Action(this.owner, this.owner, new GainStrengthPower(this.owner, 1), 1));
        }

        this.flash();

        return damageAmount;
    }
}
