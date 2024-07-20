package lingmod.powers;

import static lingmod.ModCore.makeID;

import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import lingmod.ModCore;
import lingmod.util.CustomTags;

public class NvErHongPower extends AbstractEasyPower {
    public static final String CLASS_NAME = NvErHongPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; // 是否回合后消失
    public static final Logger logger = ModCore.logger;

    public NvErHongPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, amount);
        updateDescription();
        loadTexture("Icon_AfterImage");
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if (usedCard.hasTag(CustomTags.WINE)) {
            addToBot(new DrawCardAction(amount));
        }
    }
}

// "lingmod:NvErHongPower": {
// "NAME": "BUFF",
// "DESCRIPTIONS": [
// "",
// ]
// },