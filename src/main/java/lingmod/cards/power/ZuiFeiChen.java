package lingmod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.powers.ZuiFeiChenPower;

import static lingmod.ModCore.makeID;

/**
 * 每消耗牌，获得1酒
 */
@CardConfig(magic = 1)
public class ZuiFeiChen extends AbstractEasyCard {

    public static final String NAME = ZuiFeiChen.class.getSimpleName();
    public final static String ID = makeID(NAME);
    public static final CardType TYPE = CardType.POWER;


    public ZuiFeiChen() {
        super(ID, 1, TYPE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.initializeDescription();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ZuiFeiChenPower(p, magicNumber)));
    }
}