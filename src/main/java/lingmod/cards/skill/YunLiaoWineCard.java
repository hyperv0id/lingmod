package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractWineCard;
import lingmod.interfaces.CardConfig;

@CardConfig(magic = 6)
public class YunLiaoWineCard extends AbstractWineCard {

    public static final String ID = makeID(YunLiaoWineCard.class.getSimpleName());

    public YunLiaoWineCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, 0);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
    }
}
