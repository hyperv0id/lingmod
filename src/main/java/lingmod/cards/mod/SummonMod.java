package lingmod.cards.mod;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;
import static lingmod.util.Wiz.atb;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
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
        card.selfRetain = true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.selfRetain = false;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format(uis.TEXT[0], rawDescription);
    }

    @Override
    public void onExhausted(AbstractCard card) {
        super.onExhausted(card);
        atb(new GainEnergyAction(1));
    }

    @Override
    public void onRetained(AbstractCard card) {
        super.onRetained(card);
        int cnt = (int) Wiz.adp().hand.group.stream().filter(c -> CardModifierManager.hasModifier(c, SummonMod.ID))
                .count();
        if (cnt >= 4)
            return;
        AbstractCard cp = card.makeCopy();
        if (card.upgraded)
            cp.upgrade();
        logger.info(card.name + " retained");
        CardModifierManager.addModifier(cp, new ExhaustMod());
        Wiz.atb(new MakeTempCardInHandAction(cp, 1));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SummonMod();
    }
}
