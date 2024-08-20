package lingmod.relics;

import basemod.interfaces.StartActSubscriber;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static lingmod.ModCore.makeID;

/**
 * 古旧铸物 事件 每层3缓冲，每战斗6盾
 */
public class NianAncientCasting extends AbstractEasyRelic implements StartActSubscriber {

    public static final String ID = makeID("NianAncientCasting");
    public static final int BUFFER_NUM = 3; // 获得几层缓冲
    public static final int BLOCK_NUM = 9; // 获得几层格挡

    public NianAncientCasting() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
        this.counter = BUFFER_NUM;
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (counter <= 0) return damageAmount;
        if (AbstractDungeon.player.currentBlock >= damageAmount) return damageAmount;
        this.flash();
        counter--;
        return 0;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new GainBlockAction(AbstractDungeon.player, BLOCK_NUM));
    }

    @Override
    public void receiveStartAct() {
        this.flash();
        this.counter = BUFFER_NUM;
    }
}
