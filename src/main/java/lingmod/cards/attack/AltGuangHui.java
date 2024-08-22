package lingmod.cards.attack;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.powers.PoeticMoodPower;

import static lingmod.ModCore.makeID;

public class AltGuangHui extends AbstractEasyCard {
    public static final String ID = makeID(AltGuangHui.class.getSimpleName());

    public AltGuangHui() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
    }

    @Override
    public void upp() {
        updateCost(-1);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseDamage = PoeticMoodPower.powerGained;
        super.calculateCardDamage(mo);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
    }

}
