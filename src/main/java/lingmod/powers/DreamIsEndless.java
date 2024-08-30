package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;

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

    public DreamIsEndless(AbstractCreature owner) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, 0);
        this.isJustApplied = true;
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0)
            addToBot(new ApplyPowerAction(owner, owner, new ConstrictedPower(owner, owner, damageAmount)));
        return super.onLoseHp(damageAmount);
    }
}
