package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.powers.PoeticMoodPower;

import static java.lang.Integer.max;
import static lingmod.ModCore.makeID;

/**
 * 手摇鼓Alt：战斗开始时，获得4诗意
 */
public class AltShouYaoGu extends AbstractEasyRelic {
    public static final String ID = makeID(AltShouYaoGu.class.getSimpleName());
    public static final int NUM = 4;
    public AltShouYaoGu() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.flash();
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(player, player, new PoeticMoodPower(player, NUM)));
    }
}
