package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * 清平将连续攻击两次
 */
public class TranquilityPower extends AbstractEasyPower {
    public static final String POWER_NAME = TranquilityPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public TranquilityPower(AbstractMonster owner) {
        super(ID, powerStrings.NAME, null, false, owner, 0);
        loadTexture("AbsSummon");
    }
}