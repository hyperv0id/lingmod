package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import lingmod.ModCore;
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;

public class PoeticMoodPower extends AbstractEasyPower{

    public static final String NAME = "PoeticMoodPower";
    public static final String ID = makeID(NAME);
    public static final int TARGET = 15; // 叠层
    public static final int RGZP = 1; // 人工制品
    public static final int BLOCK = 6; // 格挡

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final Logger logger = ModCore.logger;
    public PoeticMoodPower(AbstractCreature owner, int amount) {
        super(ID, NAME, TYPE, false, owner, amount);
//        this.isTwoAmount = true;
    }




    @Override
    public void updateDescription() {
        /**      "叠满 #b",
         *       " 层获得 #b",
         *       " #y人工制品 与 #b",
         *       " #y格挡"
         */
        this.description = DESCRIPTIONS[0] + TARGET + DESCRIPTIONS[1] + RGZP +DESCRIPTIONS[2]+ BLOCK + DESCRIPTIONS[3];
    }

    protected void execute(){
        // owner.addPower(new StrengthPower(owner, 1));
        addToBot(new GainBlockAction(owner, owner, BLOCK));
        owner.addPower(new ArtifactPower(owner, RGZP));
    }

    public void addAmount(int i) {
        this.amount += i;
        while (amount >= TARGET){
            amount -= TARGET;
            execute();
        }
    }
}
