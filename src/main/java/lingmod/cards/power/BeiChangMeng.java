package lingmod.cards.power;

import lingmod.actions.MyApplyPower_Action;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;
import lingmod.powers.BeiChangMengPower;

import static lingmod.ModCore.makeID;

/**
 * 离开梦境时，触发能力的回合结束效果
 */
@Credit(link = "https://wujiyan38048.lofter.com/post/3155e82f_2bb5b6163", platform = Credit.LOFTER, username = "五碳糖")
public class BeiChangMeng extends AbstractEasyCard {
    public final static String ID = makeID(BeiChangMeng.class.getSimpleName());

    public BeiChangMeng() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MyApplyPower_Action(p, p, new BeiChangMengPower(p)));
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
