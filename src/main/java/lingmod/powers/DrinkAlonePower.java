package lingmod.powers;

import static lingmod.ModCore.makeID;

import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import lingmod.ModCore;
import lingmod.cards.skill.DrinkAlone;

public class DrinkAlonePower extends AbstractEasyPower {
    public static final String CLASS_NAME = DrinkAlonePower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    public static final Logger logger = ModCore.logger;
    public static int postfix = 0;

    public DrinkAlonePower(AbstractPlayer owner) {
        super(POWER_ID + postfix++, NAME, TYPE, false, owner, 0);
        this.owner = owner;
        this.updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        int cost = card.costForTurn;
        if (cost <= 0)
            return;
        // 对自己无效
        if (card.cardID.equals(DrinkAlone.ID))
            return;
        if (!card.purgeOnUse) {
            this.flash();
            // 卡牌打出多次
            addToBotAbstract(() -> {
                AbstractMonster m = null;
                if (action.target != null) {
                    m = (AbstractMonster) action.target;
                }
                int energy = EnergyPanel.totalCount;
                int times = (int) Math.ceil(((double) energy) / cost); // 打出 上取整次
                times = Math.max(1, times);
                for (int i = 0; i < times; i++) {
                    AbstractCard tmp = card.makeStatEquivalentCopy();
                    AbstractDungeon.player.limbo.addToBottom(tmp);
                    tmp.current_x = card.current_x;
                    tmp.current_y = card.current_y;
                    tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                    tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                    if (m != null) {
                        tmp.calculateCardDamage(m);
                    }
                    tmp.purgeOnUse = true;
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, 0, true, true), true);
                }
                AbstractDungeon.player.energy.use(energy);
            });
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }
}
