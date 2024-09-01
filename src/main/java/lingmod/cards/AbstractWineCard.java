package lingmod.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static lingmod.ModCore.makeID;

public abstract class AbstractWineCard extends AbstractEasyCard {
    public final static String ID = makeID(AbstractWineCard.class.getSimpleName());

    public AbstractWineCard() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        
    }
    @Override
    public void upp() {
        // TODO Auto-generated method stub
    }
}
//  "lingmod:AbstractWineCard": {
//    "NAME": "AbstractWineCard",
//    "DESCRIPTION": ""
//  } AbstractWineCard {
    
// }
