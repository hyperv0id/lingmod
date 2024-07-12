package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

/**
 * 定风波：获得 10/13 格挡
 */
@CardConfig(poemAmount = 2)
public class DingFengBoCard extends AbstractEasyCard {

    public final static String CARD_ID = makeID(DingFengBoCard.class.getSimpleName());

    public DingFengBoCard() {
        super(CARD_ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 8;
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }
}
