package lingmod.cards.skill;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.CardTimeTravelAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;

import java.util.ArrayList;
import java.util.List;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

/**
 * 寻日峰：消耗任意张。再次打出：把消耗的放入手牌
 */
@Credit(platform = Credit.PIXIV, username = "超级甜食", link = "https://www.pixiv.net/artworks/113725572")
public class XunRiFeng extends AbstractEasyCard {
    public static final String NAME = XunRiFeng.class.getSimpleName();
    public static final String ID = makeID(NAME);
    public List<AbstractCard> exhaustedCards;

    public XunRiFeng() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaustedCards = new ArrayList<>();
    }

    @Override
    public void upp() {
        updateCost(-1);
    }

    @Override
    public void triggerOnGlowCheck() {
        if (exhaustedCards.isEmpty()) {
            glowColor = BLUE_BORDER_GLOW_COLOR;
        } else {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (exhaustedCards.isEmpty()) {
            addToBot(new CardTimeTravelAction(this));
            logger.info(NAME + ": 选择牌消耗");
        } else {
            addToBotAbstract(() -> {
                exhaustedCards.forEach(c -> {
                    player.exhaustPile.removeCard(c);
                    c.unfadeOut();
                    // 不能超过手牌上限
                    // TODO: 没有特效
                    if (player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                        player.hand.addToHand(c);
                    } else {
                        player.discardPile.addToTop(c);
                    }
                    c.unhover();
                });
                player.hand.refreshHandLayout();
                exhaustedCards.clear();
            });
            logger.info(NAME + ": 回到手牌");
        }
    }

    public void addCard(AbstractCard c) {
        this.exhaustedCards.add(c);
    }
}