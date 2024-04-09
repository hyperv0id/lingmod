package lingmod.cards;

import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static lingmod.ModCore.makeID;

/**
 * 丢弃手牌中所有诗，再抽取
 */
public class FeiHuaLingCard extends AbstractPoetCard{
    public static final String ID = makeID(FeiHuaLingCard.class.getSimpleName());
    public FeiHuaLingCard(){
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }
    int upgrade = 0;

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        ArrayList<AbstractCard> cards = abstractPlayer.hand.group;
        int times = upgrade - 1; // 不统计自己
        for (AbstractCard c: cards) {
            if(AbstractPoetCard.class.isAssignableFrom(c.getClass())){
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
