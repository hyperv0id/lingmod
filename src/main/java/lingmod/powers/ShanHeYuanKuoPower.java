package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.ModCore;
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;
import static lingmod.powers.AbstractEasyPower.I18N.getName;

public class ShanHeYuanKuoPower extends AbstractEasyPower {
    public static final String CLASS_NAME = ShanHeYuanKuoPower.class.getSimpleName();
    public static final String ID = makeID(CLASS_NAME);
    public static final Logger logger = ModCore.logger;
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    public ShanHeYuanKuoPower(AbstractPlayer p, int amount) {
        super(ID, getName(ID), TYPE, true, p, amount);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        super.onAfterCardPlayed(usedCard);
        addToBot(new DrawCardAction(amount));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
