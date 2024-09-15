package lingmod.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import lingmod.stance.NellaFantasiaStance;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 离开梦境时触发 能力、遗物、手牌 的回合结束效果
 */
public class BeiChangMengPower extends AbstractEasyPower {
    public static final String POWER_NAME = BeiChangMengPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public BeiChangMengPower(AbstractCreature owner, int amount) {
        super(ID, powerStrings.NAME, PowerType.DEBUFF, false, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        super.onChangeStance(oldStance, newStance);
        AbstractPlayer p = AbstractDungeon.player;
        if (oldStance.ID.equals(NellaFantasiaStance.STANCE_ID)) {
            for (AbstractMonster abstractMonster : MonsterHelper.allMonstersNotSummon()) {
                Wiz.applyToEnemy(abstractMonster, new WeakPower(abstractMonster, amount, false));
            }
        }
    }
}
