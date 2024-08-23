package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.unique.RandomCardFromDiscardPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.actions.GuiXingAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;

/**
 * 弃牌堆一张酒放入手牌
 */
@Credit(username = "枯荷倚梅cc", platform = "lofter", link = "https://anluochen955.lofter.com/post/1f2a08fc_2baf8eeb3")
@CardConfig(wineAmount = 1, damage = 3)
public class GuiXingWineCard extends AbstractEasyCard {
    public static final String ID = makeID(GuiXingWineCard.class.getSimpleName());

    public GuiXingWineCard() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
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
        else
            addToBot(new GuiXingAction(false));
    }
}
