package lingmod.powers;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AngryPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lingmod.actions.Sui7DealAction;
import lingmod.monsters.MonsterSui_7_Ji;

import static lingmod.ModCore.makeID;

/**
 * 回合开始时选择牌交易，消耗牌时，绩获得1力量
 */
public class Sui7DealPower extends AngryPower{
    public static final String POWER_ID;
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
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
        // TODO Auto-generated method stub
        super.atStartOfTurnPostDraw();

        addToBot(new TalkAction(provider, MonsterSui_7_Ji.DIALOGS[1], 0.5F, 2.0F));
        addToBot(new Sui7DealAction(provider));
    }

    @Override
    public void onExhaust(AbstractCard card) {
        addToBot(new ApplyPowerAction(provider, provider, new StrengthPower(owner, this.amount)));
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        return damageAmount;
    }

    static {
        POWER_ID = makeID(Sui7DealPower.class.getSimpleName());
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
