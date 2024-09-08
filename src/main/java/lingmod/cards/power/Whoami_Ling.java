package lingmod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;
import lingmod.powers.CantApplyPowerPower;

import static lingmod.ModCore.makeID;

/**
 * 我是谁？
 * 不能获得新能力，被自己DEBUFF时失去此能力
 * 清除所有异常
 */
@Credit(username = "柞木不朽", link = "https://www.bilibili.com/video/BV1xa4y1C7vV", platform = "bilibili")
public class Whoami_Ling extends AbstractEasyCard {
    public static final String NAME = Whoami_Ling.class.getSimpleName();
    public static final String ID = makeID(NAME);

    public Whoami_Ling() {
        super(ID, 3, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            addToBot(new RemoveAllPowersAction(p, true));
        //AbstractDungeon.getMonsters().monsters.forEach(mo -> addToBot(new ApplyPowerAction(mo, mo,
        //        new CantApplyPowerPower(mo))));
        addToBot(new ApplyPowerAction(p, p, new CantApplyPowerPower(p)));
    }

}
