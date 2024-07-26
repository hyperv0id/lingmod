package lingmod.cards.mod;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import lingmod.powers.PoeticMoodPower;

import static lingmod.ModCore.makeID;

/**
 * 打出诗类卡牌时获得 诗兴
 */
public class PoemMod extends AbsLingCardModifier {
    public static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(makeID("PoemMod"));
    public int amount = 0;

    public PoemMod(int amount) {
        this.amount = amount;
    }


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format(uiString.TEXT[0], amount, rawDescription);
    }


    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p,
                new PoeticMoodPower(p, amount)));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PoemMod(amount);
    }
}
