package lingmod.cards;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ZuiFeiChen extends AbstractEasyCard{

    public final static String ID = makeID(ZuiFeiChen.class.getSimpleName());
    public String lastCardDesc = null;
    public ZuiFeiChen() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
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
