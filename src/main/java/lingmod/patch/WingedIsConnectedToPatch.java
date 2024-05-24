package lingmod.patch;

import java.util.ArrayList;
import java.util.Iterator;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;

import lingmod.cards.skill.DrunkButterfly;

@SpirePatch(clz = MapRoomNode.class, method = "wingedIsConnectedTo")
public class WingedIsConnectedToPatch {
    @SpireInsertPatch(rloc = 0)
    public static SpireReturn<Boolean> Insert(MapRoomNode _instance, MapRoomNode node) {
        ArrayList<MapEdge> edges = _instance.getEdges();
        Iterator<MapEdge> var3 = edges.iterator();
        MapEdge edge;
        do {
            if (!var3.hasNext())
                return SpireReturn.Continue();
            edge = (MapEdge) var3.next();
        } while (node.y != edge.dstY || !DrunkButterfly.canFly());
        return SpireReturn.Return(Boolean.valueOf(true));
    }

    public WingedIsConnectedToPatch() {
    }

}
