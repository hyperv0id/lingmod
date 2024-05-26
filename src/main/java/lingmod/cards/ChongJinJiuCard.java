package lingmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import lingmod.cards.mod.MirrorMod;
import lingmod.powers.DoubleCardPower;
import lingmod.util.CardHelper;

import static lingmod.ModCore.makeID;

/**
 * 重进酒：打出卡牌时消耗，并替换为其复制
 */
public class ChongJinJiuCard extends AbstractPoetCard {

    public final static String ID = makeID(ChongJinJiuCard.class.getSimpleName());
    public String lastCardDesc = null;

    public AbstractCard lastCard = null;

    public ChongJinJiuCard() {
        this(null);
    }
    public ChongJinJiuCard(AbstractCard lastCard) {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        this.lastCard = lastCard;
        this.exhaust = true;
        this.baseMagicNumber = 1;
        CardModifierManager.addModifier(this, new MirrorMod());
        this.initializeDescription();
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);

        this.cardsToPreview = AbstractDungeon.actionManager.lastCard;
        lastCard = AbstractDungeon.actionManager.lastCard;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new ChongJinJiuCard(lastCard);
    }

    @Override
    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
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
