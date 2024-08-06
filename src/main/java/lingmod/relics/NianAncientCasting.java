package lingmod.relics;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import basemod.interfaces.StartActSubscriber;

/**
 * 古旧铸物 事件 每层3缓冲，每战斗6盾
 */
public class NianAncientCasting extends AbstractEasyRelic implements StartActSubscriber {

    public static final String ID = makeID("NianAncientCasting");
    public static final int BUFFER_NUM = 3; // 获得几层缓冲
    public static final int BLOCK_NUM = 6; // 获得几层格挡
    public int actNum; // 地图在哪里

    public NianAncientCasting() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
        this.actNum = AbstractDungeon.actNum;
        this.counter = BUFFER_NUM;
        BaseMod.subscribe(this);
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (this.counter > 0) {
            this.flash();
            counter--;
            return 0;
        }
        return damageAmount;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        if (this.actNum != AbstractDungeon.actNum) {
            this.counter = BUFFER_NUM;
        }
        this.actNum = AbstractDungeon.actNum;
        addToBot(new GainBlockAction(AbstractDungeon.player, BLOCK_NUM));
    }

    /**
     *
     */
    @Override
    public void receiveStartAct() {
        this.flash();
        this.counter = BUFFER_NUM;
    }
}
