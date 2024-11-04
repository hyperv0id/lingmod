package lingmod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 手链：能量上限++，但是回合结束时获得 3 缠绕
 */
public class BraceletsRelic extends AbstractEasyRelic {
    public static final String ID = makeID(BraceletsRelic.class.getSimpleName());
    public boolean valid = false;

    public BraceletsRelic() {
        super(ID, RelicTier.BOSS, LandingSound.HEAVY);
        this.counter = 3;
    }

    @Override
    public void atBattleStart() {
        valid = false;
    }

    @Override
    public void atTurnStart() {
        if (!valid) {
            valid = true;
            return;
        }
        this.flash();
        Wiz.applyToSelf(new ConstrictedPower(Wiz.adp(), Wiz.adp(), counter));
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }


}
