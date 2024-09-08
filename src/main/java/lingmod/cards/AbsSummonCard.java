package lingmod.cards;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import lingmod.character.Ling;
import lingmod.util.Wiz;

import static lingmod.ModCore.logger;

public abstract class AbsSummonCard extends AbstractEasyCard {
    public AbsSummonCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(cardID, cost, type, rarity, target, Ling.Enums.LING_COLOR);
        this.selfRetain = true;
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public void onRetained() {
        int cnt = (int) Wiz.adp().limbo.group.stream().filter(c -> c instanceof AbsSummonCard)
                .count();
        cnt += (int) Wiz.adp().hand.group.stream().filter(c -> c instanceof AbsSummonCard)
                .count();
        if (cnt <= 3) {
            AbstractCard cp = this.makeCopy();
            if (upgraded)
                cp.upgrade();
            logger.info(this + " retained");
            CardModifierManager.addModifier(cp, new ExhaustMod());
            Wiz.atb(new MakeTempCardInHandAction(cp, 1));
        }
    }
}
