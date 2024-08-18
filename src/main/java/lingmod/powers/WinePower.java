package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

public class WinePower extends AbstractEasyPower {
    public static final String CLASS_NAME = WinePower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    public WinePower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, TYPE, false, owner, amount);
        this.amount = amount;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageType.NORMAL ? damage + this.amount : damage;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card.type == CardType.ATTACK) {
            this.flash();
            if (Wiz.isStanceNell()) {
                addToBot(new ReducePowerAction(owner, owner, this, 1));
            } else {
                addToBot(new ReducePowerAction(owner, owner, this, this.amount / 2));
            }
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount <= 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}