package lingmod.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static lingmod.ModCore.makeID;

public class PoemIsShort extends AbstractEasyPower {

    public static final String CLASS_NAME = PoemIsShort.class.getSimpleName();
    public static final String POWER_ID = makeID(CLASS_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false; // 是否回合后消失
    public int immunityRate;

    public PoemIsShort(AbstractCreature owner, int immunityRate) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, 0);
        this.immunityRate = immunityRate;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], immunityRate);
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        super.atDamageFinalReceive(damage, type);
        damage *= (float) immunityRate / 100F; // 伤害减免
        return damage;
    }
}