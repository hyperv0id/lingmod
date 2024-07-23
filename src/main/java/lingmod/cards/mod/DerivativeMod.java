package lingmod.cards.mod;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import static lingmod.ModCore.makeID;

/**
 * 化物：在梦中打出时会连续打出两次
 */
public class DerivativeMod extends AbsLingCardModifier {

    public static final String ID = makeID(DerivativeMod.class.getSimpleName());
    public static final UIStrings ui = CardCrawlGame.languagePack.getUIString(ID);

    public DerivativeMod() {
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format(ui.TEXT[0], rawDescription);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DerivativeMod();
    }

}
