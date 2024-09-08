package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import lingmod.actions.FastApplyPower_Action;

public class PoemVSQuickSandPower extends AbstractEasyPower {
    public static final String CLASS_NAME = PoemVSQuickSandPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; //  是否回合后消失
    private boolean allowLoseHP = false;

    public PoemVSQuickSandPower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public PoemVSQuickSandPower(AbstractCreature owner, int amount, boolean allowLoseHP) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, amount);
        updateDescription();
        this.allowLoseHP = true;
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        if (allowLoseHP) this.description = DESCRIPTIONS[1];
        else this.description = DESCRIPTIONS[0];
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        super.wasHPLost(info, damageAmount);
        if (allowLoseHP) {
            if (info.owner != null && info.owner != this.owner) {
                this.flash();
                addToBot(new FastApplyPower_Action(owner, owner, new PoeticMoodPower(owner, this.amount)));
            }
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.owner != this.owner) {
            this.flash();
            addToBot(new FastApplyPower_Action(owner, owner, new PoeticMoodPower(owner, this.amount)));
        }
        return damageAmount;
    }
}

//  "ModID:PoemVSQuickSandPower": {
//    "NAME": "BUFF",
//    "DESCRIPTIONS": [
//      "每次受到伤害时，获得X诗兴",
//    ]
//  },