package lingmod.powers;

import static lingmod.ModCore.makeID;

import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import lingmod.ModCore;

public class YuGuoZhuoYingPower extends AbstractEasyPower {

    public static final String CLASS_NAME = YuGuoZhuoYingPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; // 是否回合后消失
    public static final Logger logger = ModCore.logger;
    public int magnitute = 1;
    public boolean startTurn;

    public YuGuoZhuoYingPower(AbstractCreature owner, int magnitute, boolean startTurn) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, 0);
        this.magnitute = magnitute;
        this.startTurn = startTurn;
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        if (startTurn) {
            this.flash();
            addToBot(new GainBlockAction(owner, amount));
            this.amount = 0;
        }
    }

    @Override
    public int onLoseHp(int dmg) {
        if (dmg <= 0)
            return 0;
        this.flash();
        addToTop(new GainBlockAction(owner, dmg * magnitute));
        this.amount += dmg * magnitute;
        return super.onLoseHp(dmg);
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        if(!startTurn) {
            this.description = String.format(powerStrings.DESCRIPTIONS[0], magnitute);
        } else {
            this.description = String.format(powerStrings.DESCRIPTIONS[1], magnitute);
        }
    }
}
