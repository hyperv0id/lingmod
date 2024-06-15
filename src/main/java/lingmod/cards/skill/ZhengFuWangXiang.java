package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.ModCore;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.attack.GuoJiaXianMei;
import lingmod.util.Wiz;

/**
 * 征夫望乡: 消耗手牌所有打击/防御，一张裹甲衔枚牌库
 */
public class ZhengFuWangXiang extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(ZhengFuWangXiang.class.getSimpleName());

    public ZhengFuWangXiang() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
        this.cardsToPreview = new GuoJiaXianMei();
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 消耗手牌所有打防
        p.hand.group.stream().filter(Wiz::isStart_SD).forEach(c -> addToBot(new ExhaustSpecificCardAction(c,
                p.hand)));
        if (upgraded) {
            p.discardPile.group.stream().filter(Wiz::isStart_SD).forEach(c -> addToBot(new ExhaustSpecificCardAction(c,
                    p.discardPile)));
            p.drawPile.group.stream().filter(Wiz::isStart_SD).forEach(c -> addToBot(new ExhaustSpecificCardAction(c,
                    p.drawPile)));
        }
        addToBot(new MakeTempCardInHandAction(new GuoJiaXianMei()));
    }

}
