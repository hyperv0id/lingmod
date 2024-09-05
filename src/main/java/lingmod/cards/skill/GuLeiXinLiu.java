package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;
import lingmod.util.Wiz;

import java.util.Iterator;

import static lingmod.ModCore.makeID;

/**
 * 发掘一张牌
 */
@Credit(username = "-莫熠榆-", platform = Credit.BILI, link = "https://www.bilibili.com/video/BV1nw4m1Q7wp")
public class GuLeiXinLiu extends AbstractEasyCard {
    public final static String ID = makeID(GuLeiXinLiu.class.getSimpleName());

    public GuLeiXinLiu() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded)
            addToBot(new ExhumeAction(false));
        else {
            AbstractGameAction action = new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractCard derp;
                    if (this.duration == Settings.ACTION_DUR_FAST) {
                        if (AbstractDungeon.player.hand.size() == 10) {
                            AbstractDungeon.player.createHandIsFullDialog();
                            this.isDone = true;
                        } else if (Wiz.adp().exhaustPile.isEmpty()) {
                            this.isDone = true;
                        } else if (Wiz.adp().exhaustPile.size() == 1) {
                            if (((AbstractCard) Wiz.adp().exhaustPile.group.get(0)).cardID.equals("Exhume")) {
                                this.isDone = true;
                            } else {
                                AbstractCard c = Wiz.adp().exhaustPile.getTopCard();
                                c.unfadeOut();
                                c.setCostForTurn(0);
                                Wiz.adp().hand.addToHand(c);
                                if (AbstractDungeon.player.hasPower("Corruption") && c.type == CardType.SKILL) {
                                    c.setCostForTurn(-9);
                                }

                                Wiz.adp().exhaustPile.removeCard(c);

                                c.unhover();
                                c.fadingOut = false;
                                this.isDone = true;
                            }
                        } else {
                            Iterator<AbstractCard> c;
                            c = Wiz.adp().exhaustPile.group.iterator();

                            while (c.hasNext()) {
                                derp = (AbstractCard) c.next();
                                derp.stopGlowing();
                                derp.unhover();
                                derp.unfadeOut();
                            }

                            c = Wiz.adp().exhaustPile.group.iterator();

                            while (c.hasNext()) {
                                derp = (AbstractCard) c.next();
                                if (derp.cardID.equals("Exhume")) {
                                    c.remove();
                                }
                            }

                            if (Wiz.adp().exhaustPile.isEmpty()) {
                                this.isDone = true;
                            } else {
                                AbstractDungeon.gridSelectScreen.open(Wiz.adp().exhaustPile, 1, TEXT[0], false);
                                this.tickDuration();
                            }
                        }
                    } else {
                        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                            Iterator<AbstractCard> c;
                            for (c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); derp.unhover()) {
                                derp = (AbstractCard) c.next();
                                Wiz.adp().hand.addToHand(derp);
                                if (AbstractDungeon.player.hasPower("Corruption") && derp.type == CardType.SKILL) {
                                    derp.setCostForTurn(-9);
                                }

                                Wiz.adp().exhaustPile.removeCard(derp);
                            }

                            AbstractDungeon.gridSelectScreen.selectedCards.clear();
                            Wiz.adp().hand.refreshHandLayout();

                            for (c = Wiz.adp().exhaustPile.group.iterator(); c.hasNext(); derp.target_y = 0.0F) {
                                derp = (AbstractCard) c.next();
                                derp.unhover();
                                derp.target_x = (float) CardGroup.DISCARD_PILE_X;
                            }
                        }

                        this.tickDuration();
                    }
                }
            };
            addToBot(action);
        }
    }

    @Override
    public void upp() {
    }
}
// "lingmod:GuLeiXinLiu": {
// "NAME": "故垒新柳",
// "DESCRIPTION": "发掘 一张牌，并使其 自身不再 消耗"
// }
