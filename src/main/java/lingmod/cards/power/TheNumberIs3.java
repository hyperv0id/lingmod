package lingmod.cards.power;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.powers.NI3Power;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

@AutoAdd.Ignore
public class TheNumberIs3 extends AbstractEasyCard {

    public final static String ID = makeID(TheNumberIs3.class.getSimpleName());

    public TheNumberIs3() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void upp() {
        updateCost(-1);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        Wiz.applyToSelf(new NI3Power(Wiz.adp()));
    }
}
