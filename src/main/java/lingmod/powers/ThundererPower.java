package lingmod.powers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static lingmod.ModCore.makeID;

/**
 * 弦惊将攻击所有敌人
 */
public class ThundererPower extends AbstractEasyPower {
    public static final String POWER_NAME = ThundererPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public ThundererPower(AbstractMonster owner) {
        super(ID, powerStrings.NAME, PowerType.BUFF, false, owner, 0);
        loadTexture("AbsSummon");
    }
}