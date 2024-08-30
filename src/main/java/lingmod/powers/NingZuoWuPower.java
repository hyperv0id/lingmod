package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

/**
 * 你无法再失去任何能力/异常
 * see: PowerPatch.RemoveSpecificPowerAction
 */
public class NingZuoWuPower extends AbstractEasyPower {
    public static final String POWER_NAME = NingZuoWuPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public NingZuoWuPower(AbstractCreature owner) {
        super(ID, powerStrings.NAME, null, false, owner, 0);
    }
}