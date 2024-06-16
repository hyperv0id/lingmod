package lingmod.cards.power;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;
import lingmod.powers.ZuiFeiChenPower;

/**
 * 每打出/消耗/丢弃 5/4 张牌，获得1酒
 */
public class ZuiFeiChen extends AbstractEasyCard {

    public static final String NAME = ZuiFeiChen.class.getSimpleName();
    public final static String ID = makeID(NAME);
    public static final CardType TYPE = CardType.POWER;


    public ZuiFeiChen() {
        super(ID, 1, TYPE, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 5;
        this.initializeDescription();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(-1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ZuiFeiChenPower(p, magicNumber, 1)));
    }
}