package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.ModCore;
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;

/**
 * 获得格挡时，多获得amount点，然后amount++
 */
public class HuSongPower extends AbstractEasyPower {
    public static final String CLASS_NAME = HuSongPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final Logger logger = ModCore.logger;
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; // 是否回合后消失
    private final int adder;

    public HuSongPower(AbstractCreature owner, int amount, int adder) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, amount);
        this.adder = adder;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.name = I18N.getName(this.ID);
        this.description = I18N.getDesc(this.ID)[0];
        this.description = String.format(description, amount, adder);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if (isPlayer) {
            addToBot(new GainBlockAction(owner, amount));
        }
    }

    @Override
    public void onExhaust(AbstractCard card) {
        this.amount += adder;
    }
}

// "lingmod:HuSong": {
// "NAME": "BUFF",
// "DESCRIPTIONS": [
// "",
// ]
// },