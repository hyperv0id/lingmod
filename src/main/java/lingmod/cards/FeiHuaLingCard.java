package lingmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static lingmod.ModCore.makeID;

public class FeiHuaLingCard extends AbstractPoetCard{
    public static final String ID = makeID(FeiHuaLingCard.class.getSimpleName());
    public FeiHuaLingCard(){
        super(ID, 3, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        //TODO:
    }

    @Override
    public void upp() {

    }
}
