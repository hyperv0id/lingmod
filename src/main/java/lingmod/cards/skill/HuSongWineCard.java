package lingmod.cards.skill;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractWineCard;

import static lingmod.ModCore.makeID;

public class HuSongWineCard extends AbstractWineCard {
    public static final String ID = makeID(HuSongWineCard.class.getSimpleName());
    public HuSongWineCard() {
       super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
       baseBlock = 6;
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        blck();
    }
}
