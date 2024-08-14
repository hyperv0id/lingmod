package lingmod.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static lingmod.ModCore.makeID;

public class HP_Lock_Power extends AbstractEasyPower implements InvisiblePower {
    public static final String POWER_NAME = HP_Lock_Power.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public HP_Lock_Power(AbstractCreature owner) {
        super(ID, powerStrings.NAME, PowerType.BUFF, false, owner, 0);
    }

    @Override
    public void onSpecificTrigger() {
        super.onSpecificTrigger();
        this.flash();
        owner.heal(1, true);
    }
}
