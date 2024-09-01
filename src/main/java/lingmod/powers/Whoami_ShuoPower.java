package lingmod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DoubleTapPower;
import lingmod.actions.MyApplyPower_Action;

import static lingmod.ModCore.makeID;

public class Whoami_ShuoPower extends AbstractEasyPower {
    public static final String ID = makeID(Whoami_ShuoPower.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public int counter;

    public Whoami_ShuoPower(AbstractCreature owner, int counter) {
        super(ID, powerStrings.NAME, PowerType.BUFF, true, owner, 0);
        this.counter = counter;
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + counter + DESCRIPTIONS[1];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.type == CardType.ATTACK) {
            this.amount++;
            if (this.amount >= counter) {
                this.amount = 0;
                addToBot(new MyApplyPower_Action(owner, owner, new DoubleTapPower(owner, 1)));
            }
        }
    }
}
