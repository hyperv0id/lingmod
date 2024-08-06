package lingmod.relics;

import static lingmod.ModCore.makeID;

import lingmod.character.Ling;

/**
 * 一盏灯：灯挑夜，箭如雨，大漠飞火
 */
public class LightRelic extends AbstractEasyRelic {
    public static final String ID = makeID(LightRelic.class.getSimpleName());

    public LightRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, Ling.Enums.LING_COLOR);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
