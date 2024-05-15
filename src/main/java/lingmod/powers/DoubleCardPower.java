package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.ModCore;
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;

/**
 * 下amount张卡牌将打出2次，FIXME: 不吃敏捷
 */
public class DoubleCardPower extends AbstractEasyPower {
    //    public static final String NAME = "DoubleCardPower";
    public static final String CLASS_NAME = DoubleCardPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; //  是否回合后消失
    public static final Logger logger = ModCore.logger;

    public DoubleCardPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToTop(new RemoveSpecificPowerAction(owner, owner, ID));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && this.amount > 0) {
            this.flash();
            // 1. 复制卡牌
            AbstractCard tmp = card.makeSameInstanceOf();  // 复制一份
            AbstractDungeon.player.limbo.addToBottom(tmp); // 添加到消耗堆
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;
            // 2. 计算伤害
            AbstractMonster m = (AbstractMonster) action.target;
            if (m != null && m.isDead) {
                m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            }
            if (m != null) { // 怪物未死亡
                tmp.calculateCardDamage(m);
            }
            // 3. 打出
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            --this.amount;
        }
        if (this.amount == 0) {
            // this.addToTop(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            addToTop(new RemoveSpecificPowerAction(owner, owner, ID));
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }
}
