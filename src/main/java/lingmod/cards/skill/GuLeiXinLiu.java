package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;

/**
 * 发掘一张牌
 */
@Credit(username = "-莫熠榆-", platform = Credit.BILI, link = "https://www.bilibili.com/video/BV1nw4m1Q7wp")
public class GuLeiXinLiu extends AbstractEasyCard {
    public final static String ID = makeID(GuLeiXinLiu.class.getSimpleName());

    public GuLeiXinLiu() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExhumeAction(false));
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
