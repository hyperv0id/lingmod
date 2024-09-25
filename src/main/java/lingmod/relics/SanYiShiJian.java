package lingmod.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.ModCore;
import lingmod.util.Wiz;

import static lingmod.ModCore.logger;

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
    }

    /**
     * 二层如果还没有获得诗简，那么必出诗简
     */
    @SpirePatch(clz = AbstractDungeon.class, method = "initializeRelicList")
    public static class BossRelicPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractDungeon __inst) {
            if (Wiz.isPlayerLing() && !Wiz.adp().hasRelic(SanYiShiJian.ID)) {
                AbstractDungeon.bossRelicPool.set(0, SanYiShiJian.ID);
                logger.info("BOSS遗物添加诗简");
            }
        }

    }
}