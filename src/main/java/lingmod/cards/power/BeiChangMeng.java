package lingmod.cards.power;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.AutoAdd.Ignore;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

/**
 * 回合结束时，如果敌人意图不能对你造成伤害，那么对其造成 !M! * 否认值 点伤害
 */
@Ignore
@CardConfig(magic = 2)
public class BeiChangMeng extends AbstractEasyCard {
    public final static String ID = makeID(BeiChangMeng.class.getSimpleName());

    public BeiChangMeng() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        
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