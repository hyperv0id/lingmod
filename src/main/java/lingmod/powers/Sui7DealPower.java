package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AngryPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import lingmod.actions.FastApplyPower_Action;
import lingmod.actions.Sui7DealAction;
import lingmod.monsters.MonsterSui_7_Ji;

/**
 * 回合开始时选择牌交易，消耗牌时，绩获得1力量
 */
public class Sui7DealPower extends AngryPower {
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
        super(owner, strengthAmount);
        this.provider = provider;
        this.ID = POWER_ID;
        this.name = NAME;
        this.type = null; // 不是负面
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new TalkAction(provider, MonsterSui_7_Ji.DIALOGS[1], 0.5F, 2.0F));
        addToBot(new Sui7DealAction(provider));
    }

    @Override
    public void onExhaust(AbstractCard card) {
        addToBot(new FastApplyPower_Action(provider, provider, new StrengthPower(owner, this.amount)));
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        return damageAmount;
    }
}
