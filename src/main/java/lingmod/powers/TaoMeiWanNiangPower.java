package lingmod.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static lingmod.ModCore.makeID;

public class TaoMeiWanNiangPower extends TwoAmountPower {

    public static final String CLASS_NAME = TaoMeiWanNiangPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public int threshold;

    public TaoMeiWanNiangPower(AbstractCreature owner, int threshold, int adder) {
        super();
        this.owner = owner;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        this.name = NAME;
        this.amount2 = this.threshold = threshold;
        this.amount = adder;
        updateDescription();
        this.loadRegion("like_water");
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card.dontTriggerOnUseCard)
            return;
        amount2++;
        if (this.amount2 >= threshold) {
            addToBot(new ApplyPowerAction(owner, owner, new PlatedArmorPower(owner, amount)));
            this.amount2 -= threshold;
        }
        super.onExhaust(card);
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], threshold - amount2, amount);
        super.updateDescription();
    }

}

// "lingmod:TaoMeiWanNiangPower": {
// "NAME": "BUFF",
// "DESCRIPTIONS": [
// "",
// ]
// },