package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static lingmod.ModCore.makeID;

/**
 * 古旧铸物 事件 每层3缓冲，每战斗6盾
 */
public class NianAncientCasting extends AbstractEasyRelic {

    public static final String ID = makeID("NianAncientCasting");

    public int actNum; // 地图在哪里
    public static final int BUFFER_NUM = 3; // 获得几层缓冲
    public static final int BLOCK_NUM = 4; // 获得几层格挡

    public NianAncientCasting() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
        this.actNum = AbstractDungeon.actNum;
        this.counter = BUFFER_NUM;
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
        if (this.actNum != AbstractDungeon.actNum) {
            this.counter = BUFFER_NUM;
        }
        this.actNum = AbstractDungeon.actNum;
        addToBot(new GainBlockAction(AbstractDungeon.player, BLOCK_NUM));
    }
}
