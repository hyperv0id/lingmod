package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.ModCore;
import lingmod.actions.ExhaustAllAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.attack.GuoJiaXianMei;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.util.Wiz;

/**
 * 征夫望乡: 消耗手牌所有打击/防御，一张裹甲衔枚牌库
 */
@CardConfig(magic = 5)
@Credit(platform = Credit.PIXIV, username = "vier122", link = "https://www.pixiv.net/artworks/104532090")
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
    public void applyPowers() {
        AbstractPlayer p = Wiz.adp();
        this.cardsToPreview = new GuoJiaXianMei();
        int cnt = p.hand.size();
        cardsToPreview.baseDamage = cardsToPreview.baseBlock = this.magicNumber * cnt;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExhaustAllAction(p.hand));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview));
    }

}