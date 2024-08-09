package lingmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import lingmod.stance.NellaFantasiaStance;

import static lingmod.ModCore.makeID;

public class BeiChangMengPower extends AbstractEasyPower {
    public static final String POWER_NAME = BeiChangMengPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public BeiChangMengPower(AbstractCreature owner) {
        super(ID, powerStrings.NAME, PowerType.DEBUFF, false, owner, 0);
    }

    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        super.onChangeStance(oldStance, newStance);
        if (oldStance.ID.equals(NellaFantasiaStance.STANCE_ID)) {
            owner.applyEndOfTurnTriggers();
        }
    }
}
