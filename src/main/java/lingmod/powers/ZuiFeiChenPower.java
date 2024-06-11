package lingmod.powers;

import basemod.BaseMod;
import basemod.interfaces.PostExhaustSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.ModCore;
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;

public class ZuiFeiChenPower extends AbstractEasyPower implements PostExhaustSubscriber {
    public static final String POWER_ID = makeID(ZuiFeiChenPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; // 是否回合后消失
    public static final Logger logger = ModCore.logger;
    protected final int gain;
    protected final int threshold;

    public ZuiFeiChenPower(AbstractCreature owner, int threshold, int gain) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, 0);
        this.threshold = threshold;
        this.gain = gain;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = String.format(powerStrings.DESCRIPTIONS[0], threshold, gain);
    }

    @Override
    public void onDrawOrDiscard() {
        super.onDrawOrDiscard();
        this.stackPower(1);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        while (amount >= threshold) {
            amount -= threshold;
            addToBot(new ApplyPowerAction(owner, owner, new WinePower(owner, gain)));
        }
    }

    @Override
    public void receivePostExhaust(AbstractCard abstractCard) {
        this.flash();
        this.stackPower(1);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        BaseMod.unsubscribe(this);
    }
}
