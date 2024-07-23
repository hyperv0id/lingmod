package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import lingmod.ModCore;
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;

/**
 * 梦长，损失生命时部分 转换成缠绕
 */
public class DreamIsEndless extends AbstractEasyPower {

    public static final String CLASS_NAME = DreamIsEndless.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = false; // 是否回合后消失
    public static final Logger logger = ModCore.logger;

    public DreamIsEndless(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, amount);
        this.isJustApplied = true;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        int amt = damageAmount / this.amount;
        if(amt > 0)
            addToBot(new ApplyPowerAction(owner, owner, new ConstrictedPower(owner, owner, amt)));
        return super.onAttackToChangeDamage(info, damageAmount);
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();

        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));

    }
}
