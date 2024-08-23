package lingmod.cards.mod;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.util.Wiz;

public class SummonMod extends AbsLingCardModifier {
    public static final String ID = makeID(SummonMod.class.getSimpleName());
    public static final UIStrings uis = CardCrawlGame.languagePack.getUIString(ID);

    public SummonMod() {
        super();
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.retain = true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.retain = false;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format(uis.TEXT[0], rawDescription);
    }

    @Override
    public void onRetained(AbstractCard card) {
        super.onRetained(card);
        AbstractCard cp = card.makeStatEquivalentCopy();
        logger.info(card.name + " retained");
        CardModifierManager.addModifier(cp, new ExhaustMod());
        Wiz.atb(new MakeTempCardInHandAction(cp, 1));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SummonMod();
    }
}
