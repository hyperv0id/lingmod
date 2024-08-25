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
import lingmod.powers.WinePower;

import static lingmod.ModCore.makeID;

/**
 * 此牌是酒
 */
public class WineMod extends AbsLingCardModifier {
    public static final String ID = makeID(WineMod.class.getSimpleName());
    public static final UIStrings uis = CardCrawlGame.languagePack.getUIString(ID);
    public int amount;

    public WineMod(int wineAmount) {
        this.amount = wineAmount;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (amount <= 0)
            return rawDescription;
        String format = uis.TEXT[0];
        return String.format(format, amount, rawDescription);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new WinePower(p, amount)));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new WineMod(amount);
    }
}
