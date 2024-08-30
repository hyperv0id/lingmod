package lingmod.cards.power;

import basemod.AutoAdd;
import lingmod.actions.MyApplyPower_Action;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.powers.NvErHongPower;

import static lingmod.ModCore.makeID;

/**
 * 回合结束时，消耗所有酒，提升手牌中攻击牌的攻击力
 */
@AutoAdd.Ignore
@CardConfig(magic = 1)
public class NvErHong extends AbstractEasyCard {
    public final static String ID = makeID(NvErHong.class.getSimpleName());

    public NvErHong() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MyApplyPower_Action(p, p, new NvErHongPower(p, magicNumber)));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
// "lingmod:NvErHong": {
// "NAME": "NvErHong",
// "DESCRIPTION": ""
// }