package lingmod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static lingmod.ModCore.makeID;

public class DuskDrawRelic extends AbstractEasyRelic{
    public static final String ID = makeID("DuskDrawRelic");
    public  DuskDrawRelic(){
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        // TODO: 变化一张牌
    }
}
