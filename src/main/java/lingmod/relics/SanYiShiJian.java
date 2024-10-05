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
     * 二层如果还没有获得诗简，那么至少30%出诗简
     */
    @SpirePatch(clz = AbstractDungeon.class, method = "initializeRelicList")
    public static class BossRelicPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractDungeon __inst) {
            // 70%概率维持原遗物，30%概率必出诗简
            if (Math.random() > 0.3F) return;
            if (Wiz.isPlayerLing() && !Wiz.adp().hasRelic(SanYiShiJian.ID)) {
                for (int i = 0; i < AbstractDungeon.bossRelicPool.size(); i++) {
                    if (AbstractDungeon.bossRelicPool.get(i).equals(SanYiShiJian.ID)) {
                        // 避免出现
                        String rawID = AbstractDungeon.bossRelicPool.get(0);
                        AbstractDungeon.bossRelicPool.set(0, SanYiShiJian.ID);
                        AbstractDungeon.bossRelicPool.set(i, rawID);
                        logger.info("Add SanYiShiJian to BOSS Relic");
                        break;
                    }
                }
            }
        }

    }
}