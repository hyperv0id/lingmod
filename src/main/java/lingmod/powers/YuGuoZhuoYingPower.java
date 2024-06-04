package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.ModCore;
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;

public class YuGuoZhuoYingPower extends AbstractEasyPower {

    public static final String CLASS_NAME = YuGuoZhuoYingPower.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true; // 是否回合后消失
    public static final Logger logger = ModCore.logger;

    public YuGuoZhuoYingPower(AbstractCreature owner) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, 0);
        updateDescription();
    }

    @Override
    public int onLoseHp(int dmg) {
        addToTop(new GainBlockAction(owner, dmg));
        return super.onLoseHp(dmg);
    }

}
