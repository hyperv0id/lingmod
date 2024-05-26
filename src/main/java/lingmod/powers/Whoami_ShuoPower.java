package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DoubleTapPower;

public class Whoami_ShuoPower extends AbstractEasyPower{
    public static final String ID = makeID(Whoami_ShuoPower.class.getSimpleName());
    public int counter;

    public Whoami_ShuoPower(AbstractCreature owner, int counter) {
        super(ID, Whoami_ShuoPower.class.getSimpleName(), PowerType.BUFF, true, owner, 0);
        this.counter = counter;
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + counter + DESCRIPTIONS[1];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if(card.type == CardType.ATTACK) {
            this.amount++;
            if(this.amount >= counter) {
                this.amount = 0;
                addToBot(new ApplyPowerAction(owner, owner, new DoubleTapPower(owner, 1)));
            }
        }
    }
}