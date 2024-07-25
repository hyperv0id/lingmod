package lingmod.cards.power;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.powers.Whoami_NianPower;

/**
 * 我是谁？
 */
@CardConfig(magic = 9)
public class Whoami_Nian extends AbstractEasyCard {
    public static final String NAME = Whoami_Nian.class.getSimpleName();
    public static final String ID = makeID(NAME);

    public Whoami_Nian() {
        super(ID, 1, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
    }


    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BufferPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new Whoami_NianPower(p)));
    }
}
