package lingmod.powers;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import lingmod.util.Wiz;

public class YuGuoZhuoYingPower extends AbstractEasyPower {

    public static final String CLASS_NAME = YuGuoZhuoYingPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; // 是否回合后消失
    private boolean checkExhaustPile;

    public YuGuoZhuoYingPower(AbstractCreature owner, int amount, boolean checkExhaustPile) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, amount);
        this.checkExhaustPile = checkExhaustPile;
        updateDescription();
    }

    /**
     * 主要处理逻辑
     */
    public void accept(AbstractCard c) {
        flash();
        int blck = c.freeToPlayOnce ? 0 : c.costForTurn;
        blck *= this.amount;
        if (blck >= 0) {
            Wiz.atb(new GainBlockAction(Wiz.adp(), blck));
        }
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
    }

    @Override
    public void onDrawOrDiscard() {
        super.onDrawOrDiscard();
    }
}
