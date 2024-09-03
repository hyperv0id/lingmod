package lingmod.cards.attack;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.PlayCardFromDiscardPileAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;

import static lingmod.ModCore.makeID;

/**
 * 打出弃牌堆随机一张牌，将其 消耗
 */
@Credit(username = "枯荷倚梅cc", platform = "lofter", link = "https://anluochen955.lofter.com/post/1f2a08fc_2baf8eeb3")
public class GuiXingWineCard extends AbstractEasyCard {
    public static final String ID = makeID(GuiXingWineCard.class.getSimpleName());

    public GuiXingWineCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    @Override
    public void upp() {
        updateCost(-1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new PlayCardFromDiscardPileAction(m, true));
    }
}
