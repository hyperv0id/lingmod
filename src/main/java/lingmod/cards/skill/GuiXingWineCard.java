package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.unique.RandomCardFromDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractWineCard;
import lingmod.util.CustomTags;

import static lingmod.ModCore.makeID;

/**
 * 打出酒时将此牌从弃牌堆放到手牌
 */
public class GuiXingWineCard extends AbstractWineCard {
    public static final String ID = makeID(GuiXingWineCard.class.getSimpleName());
    public GuiXingWineCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, 2);
    }

    @Override
    public void upp() {
        updateCost(-1);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if(c.hasTag(CustomTags.WINE)) {
            addToBot(new DiscardToHandAction(this));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RandomCardFromDiscardPileToHandAction());
    }
}
