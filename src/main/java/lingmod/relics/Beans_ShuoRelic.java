package lingmod.relics;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomSavable;

/**
 * 每斩杀5个敌人，战斗开始时获得1力量
 */
public class Beans_ShuoRelic extends AbstractEasyRelic implements CustomSavable<Integer> {
    public static final String ID = makeID(Beans_ShuoRelic.class.getSimpleName());
    public int counter2 = 0;

    public Beans_ShuoRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.counter)));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Beans_ShuoRelic();
    }

    public void onMonsterDeath(AbstractMonster m) {
        AbstractPlayer p = AbstractDungeon.player;
        this.counter2++;
        if (this.counter2 > 5) {
            this.counter2 -= 5;
            this.counter++;
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1)));
        }
    }

    @Override
    public void onLoad(Integer arg0) {
        this.counter2 = arg0;
    }

    @Override
    public Integer onSave() {
        return this.counter2;
    }
}