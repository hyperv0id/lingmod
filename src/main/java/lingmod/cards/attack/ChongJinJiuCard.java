package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DuplicationPower;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractPoemCard;
import lingmod.cards.mod.MirrorMod;

/**
 * 重进酒：打出卡牌时消耗，并替换为其复制
 */
public class ChongJinJiuCard extends AbstractPoemCard {

    public final static String ID = makeID(ChongJinJiuCard.class.getSimpleName());
    public String lastCardDesc = null;

    public AbstractCard lastCard = null;

    public ChongJinJiuCard() {
        this(null);
    }

    public ChongJinJiuCard(AbstractCard lastCard) {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY, 1);
        this.lastCard = lastCard;
        this.baseMagicNumber = 1;
        CardModifierManager.addModifier(this, new ExhaustMod());
        CardModifierManager.addModifier(this, new MirrorMod(this));
        this.initializeDescription();
    }

    // @Override
    // public void onPlayCard(AbstractCard c, AbstractMonster m) {
    // super.onPlayCard(c, m);

    // this.cardsToPreview = AbstractDungeon.actionManager.lastCard;
    // lastCard = AbstractDungeon.actionManager.lastCard;
    // }

    @Override
    public AbstractCard makeCopy() {
        return new ChongJinJiuCard(lastCard);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, m, new DuplicationPower(p, 1)));
    }

    @Override
    public void upp() {
        this.exhaust = false;
    }
}
