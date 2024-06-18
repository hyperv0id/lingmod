package lingmod.cards.attack;

import static lingmod.ModCore.makeID;
import static lingmod.util.Wiz.isStanceNell;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.PoemMod;

/**
 * 消耗最左边的牌，手牌中放入此牌的复制
 * 如果在梦中：抽一张牌
 */
public class RuMengLing extends AbstractEasyCard {
    public final static String ID = makeID(RuMengLing.class.getSimpleName());

    public RuMengLing() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseMagicNumber = 1;
        CardModifierManager.addModifier(this, new ExhaustMod());
        CardModifierManager.addModifier(this, new PoemMod(1));
        freeToPlayOnce = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
        addToBotAbstract(() -> {
            AbstractCard card = p.hand.getBottomCard();
            if (card != null) {
                addToBot(new ExhaustSpecificCardAction(card, p.hand));
                addToBot(new MakeTempCardInHandAction(this.makeStatEquivalentCopy()));
            }
        });
        
        addToBotAbstract(() -> {
            if (isStanceNell()) {
                addToTop(new DrawCardAction(magicNumber));
            }
        });
        addToBotAbstract(() -> {
            freeToPlayOnce = false;
        });
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }
}
