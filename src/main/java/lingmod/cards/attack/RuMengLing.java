package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.AutoAdd.Ignore;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.actions.ExhaustToDiscardAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

/**
 * 消耗最左边的牌，弃牌堆中加入2张复制
 * 选择
 */
@CardConfig(damage = 5, magic = 2, poemAmount = 2)
@Ignore
public class RuMengLing extends AbstractEasyCard {
    public final static String ID = makeID(RuMengLing.class.getSimpleName());

    public RuMengLing() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
        dmg(m, null);
        if (!upgraded) {
            addToBotAbstract(() -> {
                if (!p.hand.isEmpty()) {
                    AbstractCard card = p.hand.getBottomCard();
                    addToTop(new MakeTempCardInDiscardAction(card, magicNumber));
                    addToTop(new ExhaustSpecificCardAction(card, p.hand));
                }
            });
        } else {
            // 选一张
            addToBot(new ExhaustToDiscardAction(p, magicNumber));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }
}
