package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;

/**
 * 换盏：交换两张牌的费用，如果是酒，那么先将其费用变为0
 */
public class SwapCup extends AbstractEasyCard {
    public final static String ID = makeID(SwapCup.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public SwapCup() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // TODO: 选两张牌
        // TODO: 交换这两张牌的费用
    }

    @Override
    public void upp() {
        // TODO: 如果是酒，那么先将费用变为 0
    }
}