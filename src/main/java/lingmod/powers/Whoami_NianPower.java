package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

public class Whoami_NianPower extends AbstractEasyPower{
    public static final String POWER_NAME = Whoami_NianPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public Whoami_NianPower(AbstractCreature owner) {
        super(ID, POWER_NAME, PowerType.DEBUFF, false, owner, 0);
    }
    
    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if(card.type == CardType.ATTACK) {
            if(!owner.powers.stream().anyMatch(p->p.ID.equals(BufferPower.POWER_ID))) {
                addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            } else {
                this.flash();
                addToBot(new ReducePowerAction(owner, owner, BufferPower.POWER_ID, 1));
            }
        }
    }
}