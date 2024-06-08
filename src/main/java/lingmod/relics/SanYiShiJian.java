package lingmod.relics;

import lingmod.ModCore;

/**
 * TODO: 你的三个技能效果改为生成召唤物
 */
public class SanYiShiJian extends AbstractEasyRelic{
    public static final String ID = ModCore.makeID(SanYiShiJian.class.getSimpleName());
    public SanYiShiJian() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK);
    }
}