package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.ExhaustAllAction;
import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;

/**
 * 独酌：选择一张牌，变化所有手牌为这张牌
 */
public class DrinkAlone extends AbstractEasyCard {
    public final static String ID = makeID(DrinkAlone.class.getSimpleName());

    public DrinkAlone() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        String msg = cardStrings.EXTENDED_DESCRIPTION[0];
        if (p.hand.isEmpty()) return;
        addToBot(new SelectCardsInHandAction(msg, cards -> {
            int siz = p.hand.size();
            addToBot(new ExhaustAllAction());
            for (AbstractCard c : cards) {
                addToBot(new MakeTempCardInHandAction(c, p.hand.size() + 1));
            }
        }));
    }

    @Override
    public void upp() {
        updateCost(-1);
    }
}
