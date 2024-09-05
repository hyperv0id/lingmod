package lingmod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.ModCore;
import lingmod.cards.attack.JiangXiangNaTie;

/**
 * 你的三个技能效果改为生成召唤物
 */
public class SanYiShiJian extends AbstractEasyRelic {
    public static final String ID = ModCore.makeID(SanYiShiJian.class.getSimpleName());

    public SanYiShiJian() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void obtain() {
        super.obtain();
        AbstractDungeon.uncommonCardPool.addToBottom(new JiangXiangNaTie());
    }
}