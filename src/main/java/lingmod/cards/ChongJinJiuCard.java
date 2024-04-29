package lingmod.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.mod.AppendDescMod;
import lingmod.powers.DoubleCardPower;
import lingmod.util.CardHelper;

import static lingmod.ModCore.makeID;

/**
 * 重进酒：作为上一张卡牌的复制打出
 */
public class ChongJinJiuCard extends AbstractPoetCard {

    public final static String ID = makeID(ChongJinJiuCard.class.getSimpleName());
    public String lastCardDesc = null;
    public ChongJinJiuCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        this.exhaust = true;
        this.baseMagicNumber = 1;
        this.initializeDescription();
        CardModifierManager.addModifier(this, new AppendDescMod());
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        this.cardsToPreview = AbstractDungeon.actionManager.lastCard;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractCard lastCard = CardHelper.lastCard(true);
        if(lastCard != null) {
            lastCard.use(p, m);
        } else {
            addToBot(new ApplyPowerAction(p, p, new DoubleCardPower(p, 1)));
        }
    }

    @Override
    public void upp() {
        this.exhaust = false;
    }
}
