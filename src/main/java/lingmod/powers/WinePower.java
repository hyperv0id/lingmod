package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
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
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;

public class WinePower extends AbstractEasyPower {

    public static final String CLASS_NAME = WinePower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final Logger logger = ModCore.logger;
    public static float damageModi = 0.1F; // 每层增加多少 伤害
    public static float blockModi = 0.05F; // 每层增加多少 格挡

    public WinePower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, TYPE, true, owner, amount);
        this.amount = amount;
    }

    @Override
    public float atDamageFinalGive(float damage, DamageType type) {
        if (type == DamageType.NORMAL && amount > 0) {
            damage *= 1F + damageModi * amount;
        }
        return damage;
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return blockAmount * (1F + blockModi * amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card.type == CardType.ATTACK) {
            this.flash();
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
        if (this.amount == 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}