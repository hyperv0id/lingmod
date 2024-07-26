package lingmod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.powers.Whoami_ShuoPower;

import static lingmod.ModCore.makeID;

/**
 * 打出5次牌后获得双发
 */
public class Whoami_Shuo extends AbstractEasyCard {
    public static final String NAME = Whoami_Shuo.class.getSimpleName();
    public static final String ID = makeID(NAME);

    public Whoami_Shuo() {
        super(ID, 1, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseMagicNumber = 5;
    }


    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Whoami_ShuoPower(p, magicNumber)));
    }
}
