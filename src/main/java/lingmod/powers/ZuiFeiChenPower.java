package lingmod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.actions.FastApplyPower_Action;

import static lingmod.ModCore.makeID;

public class ZuiFeiChenPower extends AbstractEasyPower {
    public static final String POWER_ID = makeID(ZuiFeiChenPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; // 是否回合后消失

    public ZuiFeiChenPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = String.format(powerStrings.DESCRIPTIONS[0], amount);
    }


    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        this.flash();
        addToBot(new FastApplyPower_Action(owner, owner, new WinePower(owner, amount)));
    }
}
