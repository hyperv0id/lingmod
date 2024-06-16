package lingmod.cards.power;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;
import lingmod.powers.YuGuoZhuoYingPower;

/**
 * 受伤后立刻获得等量护盾
 */
public class YuGuoZhuoYing extends AbstractEasyCard {
    public static final String ID = makeID(YuGuoZhuoYing.class.getSimpleName());

    public YuGuoZhuoYing() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
    }

    @Override
    public void upp() {
        // updateCost(-1);
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new YuGuoZhuoYingPower(p, magicNumber)));
    }
}