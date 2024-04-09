package lingmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.powers.DoubleCardPower;

import static lingmod.ModCore.makeID;

/**
 * 重进酒：下一张卡牌打出两次
 */
public class ChongJinJiuCard extends AbstractPoetCard {

    public final static String ID = makeID(ChongJinJiuCard.class.getSimpleName());
    public ChongJinJiuCard(){
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        this.baseMagicNumber = 1;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        super.use(abstractPlayer, abstractMonster);
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DoubleCardPower(abstractPlayer, 1)));
    }

    @Override
    public void upp() {
        this.exhaust = false;
    }
}
