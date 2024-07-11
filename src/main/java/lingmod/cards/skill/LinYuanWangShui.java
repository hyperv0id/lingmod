package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.EtheralThisTurnMod;
import lingmod.interfaces.CardConfig;

import static lingmod.ModCore.makeID;

/**
 * 临渊忘水：抽3/4张，回合结束消耗所有
 */
@CardConfig(magic = 3)
public class LinYuanWangShui extends AbstractEasyCard {
    public static final String ID = makeID(LinYuanWangShui.class.getSimpleName());

    public LinYuanWangShui() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        // 抽牌后所有手牌获得虚无
        addToBotAbstract(() -> {
            for (AbstractCard card : p.hand.group) {
                CardModifierManager.addModifier(card, new EtheralThisTurnMod());
            }
        });
        // AbstractPlayer p = AbstractDungeon.player;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

}
