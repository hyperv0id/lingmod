package lingmod.cards.skill;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;

/**
 * 定风波：获得 10/13 格挡
 */
@AutoAdd.Ignore
public class DingFengBoCard extends AbstractEasyCard {

    public final static String CARD_ID = makeID(DingFengBoCard.class.getSimpleName());

    public DingFengBoCard() {
        super(CARD_ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
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
