package lingmod.cards.power;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.MyApplyPower_Action;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;
import lingmod.powers.Whoami_ShuoPower;

import static lingmod.ModCore.makeID;

/**
 * 打出5次牌后获得双发
 */
@Credit(username = "柞木不朽", link = "https://www.bilibili.com/video/BV1xa4y1C7vV", platform = "bilibili")
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
        addToBot(new MyApplyPower_Action(p, p, new Whoami_ShuoPower(p, magicNumber)));
    }
}
