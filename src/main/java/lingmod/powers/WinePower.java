package lingmod.powers;

import static lingmod.ModCore.makeID;

import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import lingmod.ModCore;

public class WinePower extends AbstractEasyPower {

    public static final String CLASS_NAME = WinePower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final Logger logger = ModCore.logger;

    public WinePower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, TYPE, true, owner, amount);
        this.amount = amount;
    }

    @Override
    public float atDamageFinalGive(float damage, DamageType type) {
        if (type == DamageType.NORMAL && amount > 0) {
            damage *= 1F + 0.1 * amount;
        }
        return damage;
    }
    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if(card.type == CardType.ATTACK)
        {
            this.flash();
            amount--;
        }
        if (this.amount == 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));

    }
}