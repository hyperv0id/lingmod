package lingmod.cards.curse;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;

/**
 * 孤独：可被打出。在手牌中时，打出的牌耗能++
 */
public class Lonely extends AbstractEasyCard{

    public static final String ID = makeID(Lonely.class.getSimpleName());
    public boolean trigger = false;
    
    public Lonely() {
        super(ID, 1, CardType.CURSE, CardRarity.SPECIAL, CardTarget.ALL);
        dontTriggerOnUseCard = true;
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        trigger = true;
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        trigger = false;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if(!trigger) return;
        c.cost++;
        c.isCostModified = true;
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer arg0, AbstractMonster arg1) {
    }    
}
