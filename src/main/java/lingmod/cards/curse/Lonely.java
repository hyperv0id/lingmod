package lingmod.cards.curse;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;

/**
 * 孤独：可被打出。在手牌中时，未打出的牌耗能++
 */
public class Lonely extends AbstractEasyCard {

    public static final String ID = makeID(Lonely.class.getSimpleName());

    public Lonely() {
        super(ID, 1, CardType.CURSE, CardRarity.SPECIAL, CardTarget.ALL);
        dontTriggerOnUseCard = true;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        // TODO Auto-generated method stub
        super.triggerOnEndOfPlayerTurn();
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            card.cost++;
            card.costForTurn++;
            card.isCostModified = true;
            card.isCostModifiedForTurn = true;
        }
    }

    @Override
    public void upp() {
        updateCost(-1);
    }

    @Override
    public void use(AbstractPlayer arg0, AbstractMonster arg1) {
    }
}
