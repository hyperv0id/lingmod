package lingmod.actions;

import java.util.ArrayList;
import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import lingmod.cards.attack.GuiXingWineCard;

public class GuiXingAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
        TEXT = uiStrings.TEXT;
    }

    private final boolean upgrade;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> sameID_Cards = new ArrayList<>();

    public GuiXingAction(boolean upgrade) {
        this.upgrade = upgrade;
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator c;
        AbstractCard derp;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
            } else if (this.p.discardPile.isEmpty()) {
                this.isDone = true;
            } else if (this.p.discardPile.size() == 1) {
                if (((AbstractCard) this.p.discardPile.group.get(0)).cardID.equals("Exhume")) {
                    this.isDone = true;
                } else {
                    AbstractCard card = this.p.discardPile.getTopCard();
                    card.unfadeOut();
                    this.p.hand.addToHand(card);
                    if (AbstractDungeon.player.hasPower("Corruption") && card.type == AbstractCard.CardType.SKILL) {
                        card.setCostForTurn(-9);
                    }

                    this.p.discardPile.removeCard(card);
                    if (this.upgrade && card.canUpgrade()) {
                        card.upgrade();
                    }

                    card.unhover();
                    card.fadingOut = false;
                    this.isDone = true;
                }
            } else {
                c = this.p.discardPile.group.iterator();

                while (c.hasNext()) {
                    derp = (AbstractCard) c.next();
                    derp.stopGlowing();
                    derp.unhover();
                    derp.unfadeOut();
                }

                c = this.p.discardPile.group.iterator();

                while (c.hasNext()) {
                    derp = (AbstractCard) c.next();
                    if (derp.cardID.equals(GuiXingWineCard.ID)) {
                        c.remove();
                        this.sameID_Cards.add(derp);
                    }
                }

                if (this.p.discardPile.isEmpty()) {
                    this.p.discardPile.group.addAll(this.sameID_Cards);
                    this.sameID_Cards.clear();
                    this.isDone = true;
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.p.discardPile, 1, TEXT[0], false);
                    this.tickDuration();
                }
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); derp.unhover()) {
                    derp = (AbstractCard) c.next();
                    this.p.hand.addToHand(derp);
                    if (AbstractDungeon.player.hasPower("Corruption") && derp.type == AbstractCard.CardType.SKILL) {
                        derp.setCostForTurn(-9);
                    }

                    this.p.discardPile.removeCard(derp);
                    if (this.upgrade && derp.canUpgrade()) {
                        derp.upgrade();
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
                this.p.discardPile.group.addAll(this.sameID_Cards);
                this.sameID_Cards.clear();

                for (c = this.p.discardPile.group.iterator(); c.hasNext(); derp.target_y = 0.0F) {
                    derp = (AbstractCard) c.next();
                    derp.unhover();
                    derp.target_x = (float) CardGroup.DISCARD_PILE_X;
                }
            }

            this.tickDuration();
        }
    }
}
