package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.ModCore;
import lingmod.actions.ExhaustAllAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.attack.GuoJiaXianMei;
import lingmod.interfaces.CardConfig;
import lingmod.util.Wiz;

/**
 * 征夫望乡: 消耗手牌所有打击/防御，一张裹甲衔枚牌库
 */
@CardConfig(magic = 5)
public class ZhengFuWangXiang extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(ZhengFuWangXiang.class.getSimpleName());

    public ZhengFuWangXiang() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
        this.cardsToPreview = new GuoJiaXianMei();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        long cnt = p.hand.size();
        // 消耗手牌所有打防
        if (upgraded) {
            cnt += p.discardPile.group.stream().filter(Wiz::isStart_SD).count();
            p.drawPile.group.stream().filter(Wiz::isStart_SD).forEach(c -> addToBot(new ExhaustSpecificCardAction(c,
                    p.drawPile)));
            cnt += p.drawPile.group.stream().filter(Wiz::isStart_SD).count();
            p.drawPile.group.stream().filter(Wiz::isStart_SD).forEach(c -> addToBot(new ExhaustSpecificCardAction(c,
                    p.drawPile)));
        }
        addToBot(new ExhaustAllAction(p.hand));

        GuoJiaXianMei gjxm = new GuoJiaXianMei();
        gjxm.baseDamage = gjxm.baseBlock = this.magicNumber * (int) cnt;
        gjxm.cost = Math.min((int) cnt, gjxm.cost);
        gjxm.setCostForTurn(gjxm.cost);
        addToBot(new MakeTempCardInHandAction(gjxm));
    }

}