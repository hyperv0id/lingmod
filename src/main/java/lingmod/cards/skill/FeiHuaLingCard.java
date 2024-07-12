package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.AutoAdd;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.util.CustomTags;

/**
 * 飞花令：丢弃手牌中所有诗，再抽取相同数量的牌
 * FIXME: 代码修改
 */
@AutoAdd.Ignore
@CardConfig(poemAmount = 1)
public class FeiHuaLingCard extends AbstractEasyCard{
    public static final String ID = makeID(FeiHuaLingCard.class.getSimpleName());
    public FeiHuaLingCard(){
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }
    int upgrade = 0;

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        ArrayList<AbstractCard> cards = abstractPlayer.hand.group;
        int times = upgrade - 1; // 不统计自己
        for (AbstractCard c: cards) {
            if(
                AbstractEasyCard.class.isAssignableFrom(c.getClass()) ||
                c.hasTag(CustomTags.POEM)
            ){
                addToBot(new DiscardSpecificCardAction(c));
                times++;
            }
        }
        addToBot(new FastDrawCardAction(abstractPlayer, times));
    }

    @Override
    public void upp() {
        upgrade++;
    }
}
