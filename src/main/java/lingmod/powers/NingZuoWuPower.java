package lingmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static lingmod.ModCore.makeID;

/**
 * 你无法再失去任何能力/异常
 * see: PowerPatch.RemoveSpecificPowerAction
 */
public class NingZuoWuPower extends AbstractEasyPower {
    public static final String POWER_NAME = NingZuoWuPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public NingZuoWuPower(AbstractCreature owner) {
        super(ID, powerStrings.NAME, PowerType.BUFF, false, owner, 0);
    }
}