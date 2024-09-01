package lingmod.cards.power;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import lingmod.actions.MyApplyPower_Action;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.Whoami_NianPower;

import static lingmod.ModCore.makeID;

/**
 * 我是谁？
 */
@CardConfig(magic = 9)
@Credit(username = "柞木不朽", link = "https://www.bilibili.com/video/BV1xa4y1C7vV", platform = "bilibili")
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
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MyApplyPower_Action(p, p, new BufferPower(p, magicNumber)));
        addToBot(new MyApplyPower_Action(p, p, new Whoami_NianPower(p, magicNumber)));
    }
}
