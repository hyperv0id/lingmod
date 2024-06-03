package lingmod.powers;

import static lingmod.ModCore.makeID;

import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import lingmod.ModCore;

public class PoemVSQuickSandPower extends AbstractEasyPower {
    public static final String CLASS_NAME = PoemVSQuickSandPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; //  是否回合后消失
    public static final Logger logger = ModCore.logger;

    public PoemVSQuickSandPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        super.wasHPLost(info, damageAmount);
        addToBot(new ApplyPowerAction(owner, owner, new PoeticMoodPower(owner, this.amount)));
    }
}

//  "ModID:PoemVSQuickSandPower": {
//    "NAME": "BUFF",
//    "DESCRIPTIONS": [
//      "每次受到伤害时，获得X诗兴",
//    ]
//  },