package lingmod.powers;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static lingmod.ModCore.makeID;
import static lingmod.powers.AbstractEasyPower.I18N.getName;

/**
 * 诗兴
 * 打出不同类型牌时增加1点，叠满后根据等级获得对应Buff
 */
public class PoeticMoodPower extends AbstractEasyPower implements PostBattleSubscriber {

    public static final String CLASS_NAME = PoeticMoodPower.class.getSimpleName();
    public static final String ID = makeID(CLASS_NAME);
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static int powerGained = 0;
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public PoeticMoodPower(AbstractCreature owner, int amount) {
        super(ID, getName(ID), TYPE, false, owner, amount);
        if (powerGained == 0)
            powerGained = amount;
        BaseMod.subscribe(this);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        powerGained += stackAmount;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        powerGained = 0; // 清空计数
        BaseMod.unsubscribeLater(this);
    }

}
