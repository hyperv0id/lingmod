package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;

/**
 * 消耗最左边的牌，弃牌堆中加入2张复制
 * 选择
 */
@Credit(platform = Credit.LOFTER, username = "一叶行.帆", link = "https://yiyexingfan.lofter.com/post/31fac472_2b463e1f3")
public class RuMengLing extends AbstractEasyCard {
    public final static String ID = makeID(RuMengLing.class.getSimpleName());

    public RuMengLing() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // 选一张
        addToBotAbstract(() -> {
            addToBot(new SelectCardsInHandAction("", (cards) -> {
                addToTop(new ExhaustSpecificCardAction(cards.get(0), p.hand));
                if (upgraded)
                    addToTop(new MakeTempCardInHandAction(cards.get(0).makeStatEquivalentCopy()));
                addToTop(new MakeTempCardInDiscardAction(cards.get(0).makeStatEquivalentCopy(), 1));
                addToTop(new MakeTempCardInDrawPileAction(cards.get(0).makeStatEquivalentCopy(), 1, true, true));
            }));
        });
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }
}
