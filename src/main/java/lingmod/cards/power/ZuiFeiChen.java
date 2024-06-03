package lingmod.cards.power;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;
import lingmod.powers.ZuiFeiChenPower;

public class ZuiFeiChen extends AbstractEasyCard {

    public static final String NAME = ZuiFeiChen.class.getSimpleName();
    public final static String ID = makeID(NAME);
    public static final CardType TYPE = CardType.ATTACK;

    public static final String IMG_UP = getCardTextureString(NAME + "_0", TYPE);


    public ZuiFeiChen() {
        super(ID, 1, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 4;
        this.initializeDescription();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(-1);
        this.loadCardImage(IMG_UP);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ZuiFeiChenPower(p, magicNumber, 1)));
    }
}