package lingmod.powers;

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
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;

/**
 * 下一张牌将消耗全部能量，打出 X 次
 * 每次获得 X 酒
 */
public class DrinkAlonePower extends AbstractEasyPower {
    public static final String CLASS_NAME = DrinkAlonePower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final Logger logger = ModCore.logger;
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    public static int postfix = 0;

    public DrinkAlonePower(AbstractPlayer owner, int amount) {
        super(POWER_ID + postfix++, NAME, TYPE, false, owner, 0);
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        loadTexture("Icon_Amplify");
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (this.amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            return;
        }
        super.onUseCard(card, action);
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
                for (int i = 0; i < amount; i++) {
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
                    tmp.energyOnUse = 0; // 免费打出
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, 0, true, true), true);
                }
                AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
            });
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount, amount);
    }
}
