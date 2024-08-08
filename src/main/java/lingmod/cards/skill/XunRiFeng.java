package lingmod.cards.skill;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.CardTimeTravelAction;
import lingmod.cards.AbstractEasyCard;

import java.util.ArrayList;
import java.util.List;

import static lingmod.ModCore.makeID;

/**
 * 寻日峰：消耗任意张，抽等量。再次打出：把消耗的放入手牌
 */
public class XunRiFeng extends AbstractEasyCard {
    public static final String NAME = XunRiFeng.class.getSimpleName();
    public static final String ID = makeID(NAME);
    public List<AbstractCard> exHaustedCards;

    public XunRiFeng() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exHaustedCards = new ArrayList<>();
    }

    @Override
    public void upp() {
        updateCost(-1);
    }

    @Override
    public void triggerOnGlowCheck() {
        if (exHaustedCards.isEmpty()) {
            glowColor = BLUE_BORDER_GLOW_COLOR;
        } else {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (exHaustedCards.isEmpty()) {
            addToBot(new CardTimeTravelAction(this));
        } else {
            addToBotAbstract(() -> {
                exHaustedCards.forEach(c -> {
                    player.exhaustPile.removeCard(c);
                    c.unfadeOut();
                    // 不能超过手牌上限
                    if (player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                        player.hand.addToHand(c);
                    } else {
                        player.discardPile.addToTop(c);
                    }
                    c.unhover();
                });
                player.hand.refreshHandLayout();
                exHaustedCards.clear();
            });
        }
    }

    public void addCard(AbstractCard c) {
        this.exHaustedCards.add(c);
    }
}