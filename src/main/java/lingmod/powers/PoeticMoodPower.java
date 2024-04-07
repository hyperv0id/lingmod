package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lingmod.ModCore;
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;

/**
 * 诗意
 * 单例
 * 打出不同类型牌时增加1点，叠满后根据等级获得对应Buff
 */
public class PoeticMoodPower extends AbstractEasyPower{

    public static final String NAME = PoeticMoodPower.class.getSimpleName();
    public static final String ID = makeID(NAME);

    public static int THRESHOLD, BLOCK, RGZP, STRENGTH;
    static {
        THRESHOLD = 10; // 叠层
        BLOCK = 6; // 格挡
        RGZP = 1; // 人工制品
        STRENGTH = 1; // 力量
    };

    public static int level = 0; // 等级

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final Logger logger = ModCore.logger;
    public PoeticMoodPower(AbstractCreature owner, int amount) {
        super(ID, NAME, TYPE, false, owner, amount);
    }

    public PoeticMoodPower(AbstractCreature owner, int amount, int level) {
        super(ID, NAME, TYPE, false, owner, amount);
//        this.isTwoAmount = true;
        PoeticMoodPower.level = level;
    }



    @Override
    public void updateDescription() {
        /**      "叠满 #b",
         *       " 层获得 #b",
         *       " #y人工制品 与 #b",
         *       " #y格挡"
         */
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(DESCRIPTIONS[0], THRESHOLD));
        if(level >= 0)
            sb.append(String.format(DESCRIPTIONS[1], BLOCK));
        if(level >= 1)
            sb.append(String.format(DESCRIPTIONS[2], RGZP));
        if(level >= 2)
            sb.append(String.format(DESCRIPTIONS[3], STRENGTH));
        this.description = sb.toString();
    }

    protected void execute(){
        // owner.addPower(new StrengthPower(owner, 1));
        this.amount -= THRESHOLD;
        if(level >= 0)
            addToBot(new GainBlockAction(owner, owner, BLOCK)); // 获得护甲
        if(level >= 1)
            addToBot(new ApplyPowerAction(owner, owner, new ArtifactPower(owner, RGZP))); // 获得人工制品
        if(level >= 2)
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, STRENGTH)));
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        while (this.amount >= THRESHOLD){
            this.execute();
        }
    }
}
