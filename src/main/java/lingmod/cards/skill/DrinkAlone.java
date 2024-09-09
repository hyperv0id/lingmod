package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.actions.EasyXCostAction;
import lingmod.actions.ExhaustAllAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;

/**
 * 独酌：选择一张牌，变化所有手牌为这张牌
 */
@Credit(username = "my", platform = "lofter", link = "https://my069672.lofter.com/post/3098e10d_2b466010f")
public class DrinkAlone extends AbstractEasyCard {
    public final static String ID = makeID(DrinkAlone.class.getSimpleName());

    public DrinkAlone() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        String msg = cardStrings.EXTENDED_DESCRIPTION[0];
        if (p.hand.isEmpty())
            return;
        addToBot(new SelectCardsInHandAction(msg, cards -> {
            addToBot(new ExhaustAllAction());
            addToBot(new EasyXCostAction(this, (effect, param) -> {
                addToTop(new MakeTempCardInHandAction(param, effect + (upgraded ? 1 : 0)));
                return true;
            }, cards.get(0).makeStatEquivalentCopy()));
        }));
    }

    @Override
    public void upp() {
    }
}
