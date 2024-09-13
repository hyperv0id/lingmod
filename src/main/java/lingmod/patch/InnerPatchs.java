package lingmod.patch;

import lingmod.powers.NI3Power;
import lingmod.relics.SanYiShiJian;

public class InnerPatchs {
    /**
     * 使用此数组索引写过的Patch,以免忘记
     */
    static final Class<?>[] patchs = new Class[]{
            NI3Power.QSWS_Damage_Patch.class,
            SanYiShiJian.BossRelicPatch.class,
    };
}
