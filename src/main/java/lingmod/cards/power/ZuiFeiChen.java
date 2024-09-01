package lingmod.cards.power;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.MyApplyPower_Action;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.ZuiFeiChenPower;

import static lingmod.ModCore.makeID;

/**
 * 每消耗牌，获得1酒
 */
@CardConfig(magic = 1)
@Credit(link = "https://www.pixiv.net/artworks/104592426", username = "Lemtun", platform = "pixiv")
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
        addToBot(new MyApplyPower_Action(p, p, new ZuiFeiChenPower(p, magicNumber)));
    }
}