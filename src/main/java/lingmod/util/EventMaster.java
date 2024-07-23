package lingmod.util;

import basemod.CustomEventRoom;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import lingmod.ModCore;

import java.util.ArrayList;

public class EventMaster {
    public static void triggerEvent(AbstractEvent event) {
        if (AbstractDungeon.currMapNode == null) return;

        RoomEventDialog.optionList.clear();
        MapRoomNode cur = AbstractDungeon.currMapNode;
        MapRoomNode mapRoomNode2 = new MapRoomNode(cur.x, cur.y);
        CustomEventRoom cer = new CustomEventRoom();
        mapRoomNode2.room = cer;
        ArrayList<MapEdge> curEdges = cur.getEdges();

        for (MapEdge edge : curEdges) {
            mapRoomNode2.addEdge(edge);
        }

        AbstractDungeon.dungeonMapScreen.dismissable = true;
        AbstractDungeon.nextRoom = mapRoomNode2;
        AbstractDungeon.setCurrMapNode(mapRoomNode2);

        try {
            AbstractDungeon.overlayMenu.proceedButton.hide();
            cer.event = event;
            cer.event.onEnterRoom();
        } catch (Exception var7) {
            ModCore.logger.info("Error Occurred while entering");
        }

        AbstractDungeon.scene.nextRoom(mapRoomNode2.room);
        if (mapRoomNode2.room instanceof EventRoom) {
            AbstractDungeon.rs = mapRoomNode2.room.event instanceof AbstractImageEvent ? AbstractDungeon.RenderScene.EVENT : AbstractDungeon.RenderScene.NORMAL;
        } else if (mapRoomNode2.room instanceof RestRoom) {
            AbstractDungeon.rs = AbstractDungeon.RenderScene.CAMPFIRE;
        } else {
            AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
        }
    }
}
