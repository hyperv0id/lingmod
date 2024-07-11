package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.GuLeiXinLiuAction;
import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;

/**
 * 发掘一张牌，然后其不再消耗
 */
public class GuLeiXinLiu extends AbstractEasyCard {
    public final static String ID = makeID(GuLeiXinLiu.class.getSimpleName());

    public GuLeiXinLiu() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GuLeiXinLiuAction());
    }

    @Override
    public void upp() {
        updateCost(-1);
    }
}
// "lingmod:GuLeiXinLiu": {
// "NAME": "故垒新柳",
// "DESCRIPTION": "发掘 一张牌，并使其 自身不再 消耗"
// }
