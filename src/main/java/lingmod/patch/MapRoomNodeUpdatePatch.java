package lingmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;

import lingmod.cards.skill.DrunkButterfly;

/**
 * MapRoomNodeUpdatePatch
 */
@SpirePatch(clz = MapRoomNode.class, method = "update")
public class MapRoomNodeUpdatePatch {
    public MapRoomNodeUpdatePatch() {
    }

    @SpireInsertPatch(rloc = 74)
    public static SpireReturn<Boolean> Insert(MapRoomNode _instance) {
        if (DrunkButterfly.canFly() && AbstractDungeon.player.getRelic("WingedGreaves").counter > 0) {
            ++AbstractDungeon.player.getRelic("WingedGreaves").counter;
        }

        return SpireReturn.Continue();
    }
}