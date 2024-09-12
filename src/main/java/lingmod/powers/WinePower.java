package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
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

    public void updateDescription() {
        this.description = String.format(powerStrings.DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (this.amount <= 0)
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    this));
    }

    /**
     * 酒能力时衰减效果，梦中只会减少一半
     */
    public void damp() {
        this.flash();
        if (!Wiz.isStanceNell()) {
            addToBot(new ReducePowerAction(owner, owner, this, amount - (amount / 2)));
        } else {
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageType.NORMAL ? damage + (float) this.amount : damage;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == CardType.ATTACK) {
            damp();
        }
    }
}