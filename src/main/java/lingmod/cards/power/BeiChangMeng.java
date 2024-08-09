package lingmod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.powers.BeiChangMengPower;

import static lingmod.ModCore.makeID;

/**
 * 离开梦境时，触发能力的回合结束效果
 */
public class BeiChangMeng extends AbstractEasyCard {
    public final static String ID = makeID(BeiChangMeng.class.getSimpleName());

    public BeiChangMeng() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BeiChangMengPower(p)));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
//  "lingmod:BeiChangMeng": {
//    "NAME": "BeiChangMeng",
//    "DESCRIPTION": ""
//  }