package lingmod.cards.attack;

import basemod.AutoAdd.Ignore;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.ExhaustToDiscardAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;

import static lingmod.ModCore.makeID;

/**
 * 消耗最左边的牌，弃牌堆中加入2张复制
 * 选择
 */
@CardConfig(damage = 5, magic = 2)
@Credit(platform = Credit.LOFTER, username = "一叶行.帆", link = "https://yiyexingfan.lofter.com/post/31fac472_2b463e1f3")
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
