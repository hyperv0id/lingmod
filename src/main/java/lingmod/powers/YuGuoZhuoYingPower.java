package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.util.Wiz;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

public class YuGuoZhuoYingPower extends AbstractEasyPower {

    public static final String CLASS_NAME = YuGuoZhuoYingPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; // 是否回合后消失
    private final boolean checkExhaustPile;

    public YuGuoZhuoYingPower(AbstractCreature owner, int amount, boolean checkExhaustPile) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, amount);
        this.checkExhaustPile = checkExhaustPile;
        updateDescription();
        if (checkExhaustPile) this.ID += "+";
    }

    /**
     * 主要处理逻辑
     */
    public void accept(AbstractCard c) {
        flash();
        Wiz.att(new GainBlockAction(Wiz.adp(), amount));
    }

    @Override
    public void onExhaust(AbstractCard c) {
        super.onExhaust(c);
        if (checkExhaustPile) {
            logger.info("雨过濯缨 消耗" + c.name);
            accept(c);
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[checkExhaustPile ? 1 : 0];
        this.description = String.format(this.description, amount);
    }

    @Override
    public void onDrawOrDiscard() {
        super.onDrawOrDiscard();
    }
}
