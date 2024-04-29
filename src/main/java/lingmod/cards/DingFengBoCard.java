package lingmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static lingmod.ModCore.makeID;

/**
 * 定风波：获得 10/13 格挡
 */
public class DingFengBoCard extends AbstractPoetCard{

    public final static String CARD_ID = makeID(DingFengBoCard.class.getSimpleName());

    public DingFengBoCard() {
        super(CARD_ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 10;
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        blck();
    }
}