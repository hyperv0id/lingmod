package lingmod.powers;

import basemod.BaseMod;
import basemod.interfaces.OnPlayerTurnStartPostDrawSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lingmod.actions.Sui7DealAction;
import lingmod.monsters.MonsterSui_7_Ji;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

/**
 * 回合开始时选择牌交易，消耗牌时，绩获得1力量
 */
public class Sui7DealPower extends AbstractEasyPower
        implements PostExhaustSubscriber, OnPlayerTurnStartPostDrawSubscriber {
    public static final String POWER_ID;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final PowerStrings powerStrings;

    static {
        POWER_ID = makeID(Sui7DealPower.class.getSimpleName());
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public AbstractCreature provider;

    public Sui7DealPower(AbstractCreature owner, AbstractCreature provider, int strengthAmount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, strengthAmount);
        this.loadRegion("anger");
        this.provider = provider;
        this.ID = POWER_ID;
        this.name = NAME;
        this.type = null; // 不是负面
        this.updateDescription();
        BaseMod.subscribe(this);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void receivePostExhaust(AbstractCard arg0) {
        if (arg0.dontTriggerOnUseCard)
            return;
        if (owner == null || owner.isDeadOrEscaped()) {
            BaseMod.unsubscribeLater(this);
        }
        addToBot(new ApplyPowerAction(provider, provider, new StrengthPower(owner, this.amount)));
    }

    @Override
    public void onVictory() {
        super.onVictory();
        BaseMod.unsubscribeLater(this);
    }

    @Override
    public void onDeath() {
        super.onDeath();
        BaseMod.unsubscribeLater(this);
    }

    @Override
    public void receiveOnPlayerTurnStartPostDraw() {
        if (owner == null || owner.isDeadOrEscaped()) {
            BaseMod.unsubscribeLater(this);
            return;
        }
        logger.info("绩交易");

        addToBot(new TalkAction(provider, MonsterSui_7_Ji.DIALOGS[1], 1F, 2.0F));
        addToBot(new Sui7DealAction(provider));
    }

    public static final String ACTION_ID = makeID(Sui7DealAction.class.getSimpleName());
    private static final UIStrings actionUiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
}
