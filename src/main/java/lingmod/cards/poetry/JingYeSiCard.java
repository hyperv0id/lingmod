package lingmod.cards.poetry;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractPoetryCard;
import lingmod.interfaces.CardConfig;

import static lingmod.ModCore.makeID;

/**
 * 普通五言：床前明月光，疑是地上霜，举头望明月，低头思故乡
 */
@CardConfig(block = 5)
public class JingYeSiCard extends AbstractPoetryCard {
    public static final String ID = makeID(JingYeSiCard.class.getSimpleName());

    public JingYeSiCard() {
        super(ID, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    @Override
    public void use_p(AbstractPlayer p, AbstractMonster m) {
        blck();
    }
}