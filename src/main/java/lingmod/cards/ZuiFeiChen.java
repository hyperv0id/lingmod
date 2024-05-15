package lingmod.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static lingmod.ModCore.makeID;

public class ZuiFeiChen extends AbstractEasyCard{

    public final static String ID = makeID(ZuiFeiChen.class.getSimpleName());
    public String lastCardDesc = null;
    public ZuiFeiChen() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        this.exhaust = true;
        this.baseMagicNumber = 1;
        this.initializeDescription();
    }
    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
