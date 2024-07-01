package lingmod.cards.power;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractWineCard;
import lingmod.interfaces.CardConfig;
import lingmod.powers.HuSongPower;

/**
 * 回合结束时获得 !M! 格挡，失去生命后增加1点
 */
@CardConfig(magic = 2)
public class HuSongWineCard extends AbstractWineCard {
    public static final String ID = makeID(HuSongWineCard.class.getSimpleName());

    public HuSongWineCard() {
        super(ID, 1, CardType.POWER, CardRarity.COMMON, CardTarget.SELF, 3);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster mo) {
        addToBot(new ApplyPowerAction(p, mo, new HuSongPower(p, magicNumber)));
    }
}
