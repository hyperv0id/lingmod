package lingmod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.YuGuoZhuoYingPower;

import static lingmod.ModCore.makeID;

/**
 * 回合结束时，获得手牌总耗能的格挡
 */
@Credit(username = "-Z-05", platform = Credit.BILI, link = "https://www.bilibili.com/video/BV1ZC411x7sM")
@CardConfig(magic = 1)
public class YuGuoZhuoYing extends AbstractEasyCard {
    public static final String ID = makeID(YuGuoZhuoYing.class.getSimpleName());

    public YuGuoZhuoYing() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void upp() {
        updateCost(-1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new YuGuoZhuoYingPower(p, magicNumber, upgraded)));
    }
}