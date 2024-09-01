package lingmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static lingmod.ModCore.logger;

public class NostalgiaAndCuriosityAction extends AbstractGameAction {

    private boolean retrieveCard = false;
    private boolean upgraded;
    ArrayList<AbstractCard> cards;

    public NostalgiaAndCuriosityAction(ArrayList<AbstractCard> cards, boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
        this.cards = (ArrayList<AbstractCard>) cards.stream().map(c -> c.makeStatEquivalentCopy()).collect(Collectors.toList());
    }

    @Override
    public void update() {
        logger.debug("updating");
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(cards,
                    CardRewardScreen.TEXT[1], true);
            this.tickDuration();
        } else {
            if (!retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard
                            .makeStatEquivalentCopy();
                    if (upgraded) {
                        disCard.setCostForTurn(0);
                    }

                    disCard.current_x = -1000.0F * Settings.xScale;
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard,
                                (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard,
                                (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                retrieveCard = true;
            }

            this.tickDuration();
        }
    }

}
