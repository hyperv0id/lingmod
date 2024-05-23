package lingmod.powers;

import static lingmod.ModCore.makeID;
import static lingmod.powers.AbstractEasyPower.I18N.getName;

import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import lingmod.ModCore;
import lingmod.character.VoiceMaster;

/**
 * 诗意
 * 单例
 * 打出不同类型牌时增加1点，叠满后根据等级获得对应Buff
 */
public class PoeticMoodPower extends AbstractEasyPower {

    public static final String CLASS_NAME = PoeticMoodPower.class.getSimpleName();
    public static final String ID = makeID(CLASS_NAME);
    public static int powerGained = 0;
    public static int N_Threshold, N_Dexterity, N_Buffer, N_Artifact, N_Strength;

    static {
        N_Threshold = 12; // 叠层
        N_Dexterity = 1; // 敏捷
        N_Buffer = 1; // 缓冲
        N_Artifact = 1; // 人工制品
        N_Strength = 1; // 力量
    }

    ;

    public static int level = 1; // 等级

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final Logger logger = ModCore.logger;

    public PoeticMoodPower(AbstractCreature owner, int amount) {
        super(ID, getName(ID), TYPE, false, owner, amount);
        if(amount > powerGained) powerGained = amount;
    }

    public PoeticMoodPower(AbstractCreature owner, int amount, int level) {
        super(ID, getName(ID), TYPE, false, owner, amount);
        if(amount > powerGained) powerGained = amount;
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
        sb.append(String.format(DESCRIPTIONS[0], N_Threshold));
        if (level >= 1)
            sb.append(String.format(DESCRIPTIONS[1], N_Dexterity));
        if (level >= 2)
            sb.append(String.format(DESCRIPTIONS[2], N_Buffer));
        if (level >= 3)
            sb.append(String.format(DESCRIPTIONS[3], N_Artifact));
        if (level >= 4)
            sb.append(String.format(DESCRIPTIONS[4], N_Strength));
        this.description = sb.toString();
    }

    protected void execute() {
        VoiceMaster.getInstance().onAttack();
        this.amount -= N_Threshold;
        if (level >= 1)
            addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, N_Dexterity))); // 获得敏捷
        if (level >= 2)
            addToBot(new ApplyPowerAction(owner, owner, new BufferPower(owner, N_Buffer))); // 缓冲
        if (level >= 3)
            addToBot(new ApplyPowerAction(owner, owner, new ArtifactPower(owner, N_Artifact))); // 获得人工制品
        if (level >= 4)
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, N_Strength))); // 力量
    }

    @Override
    public void onVictory() {
        // TODO Auto-generated method stub
        super.onVictory();
        powerGained = 0;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        powerGained += stackAmount; // 记录
        while (this.amount >= N_Threshold) {
            this.execute();
        }
    }
}
