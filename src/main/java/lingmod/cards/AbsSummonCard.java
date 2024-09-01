package lingmod.cards;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import lingmod.character.Ling;
import lingmod.relics.SanYiShiJian;
import lingmod.util.Wiz;

import static lingmod.ModCore.logger;

public abstract class AbsSummonCard extends AbstractEasyCard {
    public AbsSummonCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(cardID, cost, type, rarity, target, Ling.Enums.LING_COLOR);
        if(Wiz.adp() != null && Wiz.adp().hasRelic(SanYiShiJian.ID)) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

    @Override
    public void onRetained() {
        super.onRetained();
        super.onRetained();
        int cnt = (int) Wiz.adp().hand.group.stream().filter(c -> c instanceof AbstractEasyCard)
                .count();
        if (cnt >= 4)
            return;
        AbstractCard cp = this.makeCopy();
        if (upgraded)
            cp.upgrade();
        logger.info(name + " retained");
        CardModifierManager.addModifier(cp, new ExhaustMod());
        Wiz.atb(new MakeTempCardInHandAction(cp, 1));
    }
}
