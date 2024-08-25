package lingmod.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.stance.NellaFantasiaStance;

import static lingmod.ModCore.makeID;

public class LanKePower extends AbstractEasyPower {
    public static final String CLASS_NAME = LanKePower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = false; // 是否回合后消失

    public LanKePower(AbstractPlayer owner) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, 0);
        this.isJustApplied = true;
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        NellaFantasiaStance.adder++;
    }

    @Override
    public void onRemove() {
        super.onRemove();
        NellaFantasiaStance.adder--;
    }

    @Override
    public void onVictory() {
        super.onVictory();
        NellaFantasiaStance.adder--;
    }
}
