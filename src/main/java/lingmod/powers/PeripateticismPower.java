package lingmod.powers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static lingmod.ModCore.makeID;

/**
 * 逍遥在攻击时额外给予等量缠绕
 */
public class PeripateticismPower extends AbstractEasyPower {
    public static final String POWER_NAME = PeripateticismPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);


    public PeripateticismPower(AbstractMonster owner) {
        super(ID, powerStrings.NAME, null, false, owner, 0);
    }
}