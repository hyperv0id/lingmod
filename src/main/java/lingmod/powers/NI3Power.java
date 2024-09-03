package lingmod.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import lingmod.ModCore;

public class NI3Power extends AbstractEasyPower {

    public static final String ID = ModCore.makeID(NI3Power.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public NI3Power(AbstractCreature owner) {
        super(ID, powerStrings.NAME, PowerType.BUFF, false, owner, 3);
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        damageAmount = damageAmount / amount * amount + (damageAmount % amount != 0 ? amount : 0);
        return damageAmount;
    }
}
