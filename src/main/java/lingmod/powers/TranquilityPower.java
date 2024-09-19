package lingmod.powers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static lingmod.ModCore.makeID;

/**
 * 清平将连续攻击两次
 */
public class TranquilityPower extends AbstractEasyPower {
    public static final String POWER_NAME = TranquilityPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public TranquilityPower(AbstractMonster owner) {
        super(ID, powerStrings.NAME, PowerType.BUFF, false, owner, 0);
        loadTexture("AbsSummon");
    }
}