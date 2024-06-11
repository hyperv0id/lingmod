package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.unique.RandomCardFromDiscardPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.GuiXingAction;
import lingmod.cards.AbstractWineCard;

import static lingmod.ModCore.makeID;

/**
 * 打出酒时将此牌从弃牌堆放到手牌
 */
public class GuiXingWineCard extends AbstractWineCard {
    public static final String ID = makeID(GuiXingWineCard.class.getSimpleName());

    public GuiXingWineCard() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, 1);
        baseDamage = 3;
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
        if (!upgraded)
            addToBot(new RandomCardFromDiscardPileToHandAction());
        else addToBot(new GuiXingAction(false));
    }
}
