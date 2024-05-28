package lingmod.powers;

import static lingmod.ModCore.makeID;

import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import lingmod.ModCore;

/**
 * 伤害增加：本回合每次受到的伤害增加X点
 */
public class IncreaseDamagePower extends AbstractEasyPower {
    // TODO: 图像可以把活力换成紫色
    public static final String CLASS_NAME = IncreaseDamagePower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = false; // 是否回合后消失
    public static final Logger logger = ModCore.logger;
    private boolean justApplied = false;

    public IncreaseDamagePower(AbstractCreature owner, int amount, boolean isSourceMonster) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, amount);
        if (AbstractDungeon.actionManager.turnHasEnded && isSourceMonster) {
            this.justApplied = true;
        }
    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
        } else {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int dmg) {
        PoisonPower pp;
        if (this.amount > 0 && !this.owner.isDeadOrEscaped() && info.type == DamageType.NORMAL) {
            this.flash();
        }
        return super.onAttackedToChangeDamage(info, dmg + amount);
    }
}
