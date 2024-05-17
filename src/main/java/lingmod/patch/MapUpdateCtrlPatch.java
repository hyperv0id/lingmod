package lingmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;

import lingmod.cards.skill.DrunkButterfly;

/**
 * MapUpdateCtrlPatch
 */
@SpirePatch(clz = DungeonMapScreen.class, method = "updateControllerInput")
public class MapUpdateCtrlPatch {

    @SpireInsertPatch(rloc = 31, localvars = { "flightMatters" })
    public static SpireReturn<Boolean> Insert(DungeonMapScreen _instance, @ByRef boolean[] flightMatters) {
        if (DrunkButterfly.canFly()) {
            flightMatters[0] = true;
        }

        return SpireReturn.Continue();
    }
}