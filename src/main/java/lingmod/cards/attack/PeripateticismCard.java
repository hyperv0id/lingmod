package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;

public class PeripateticismCard extends AbstractEasyCard{
    public static final String ID = makeID(PeripateticismCard.class.getSimpleName());

    public PeripateticismCard() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 10;
        this.baseMagicNumber = 1;
    }


    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new GainEnergyAction(this.magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer arg0, AbstractMonster arg1) {
        addToBot(new DamageAction(arg1, new DamageInfo(arg0, damage)));
    }
}
