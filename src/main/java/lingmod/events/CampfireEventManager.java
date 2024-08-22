package lingmod.events;

import basemod.AutoAdd;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import lingmod.character.Ling;
import lingmod.interfaces.CampfireSleepEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.modID;

public class CampfireEventManager implements CustomSavable<List<String>> {
    public static List<AbstractEvent> sleepEvents = new ArrayList<>(); // 在篝火处睡觉会触发的事件

    public static AbstractEvent rollEvent() {
        if (sleepEvents.isEmpty()) {
            // 如果角色专属事件用光了，那么使用本体事件
            return AbstractDungeon.generateEvent(AbstractDungeon.eventRng);
        }
        int len = sleepEvents.size();
        int r = AbstractDungeon.eventRng.random(len - 1);
        AbstractEvent e = sleepEvents.get(r);
        sleepEvents.remove(e); // 移除事件
        logger.info("Visit Event: " + e.getClass().getSimpleName());
        try {
            return e.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            return e;
        }
    }

    public static void initSleepEvents() {
        new AutoAdd(modID).packageFilter(Sui12Event.class).any(AbstractEvent.class, (info, event) -> {
            if (event.getClass().getAnnotation(CampfireSleepEvent.class) == null)
                return;
            String name = "";
            if (event instanceof AbstractImageEvent)
                name = ReflectionHacks.getPrivate(event, AbstractImageEvent.class, "title");
            if (name == null || name.isEmpty()) {
                name = event.getClass().getSimpleName();
            }
            logger.info("添加火堆睡觉事件：" + name);
            sleepEvents.add(event);
        });
    }

    @Override
    public List<String> onSave() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.chosenClass != Ling.Enums.PLAYER_LING) return null;
        return sleepEvents.stream().map(event -> event.getClass().getName()).collect(Collectors.toList());
    }

    @Override
    public void onLoad(List<String> strings) {
        sleepEvents.clear();
        if (AbstractDungeon.player != null && AbstractDungeon.player.chosenClass != Ling.Enums.PLAYER_LING) return;
        if (strings.isEmpty()) {
            initSleepEvents();
            return;
        }
        for (String clzString : strings) {
            try {
                Class<?> clz = Class.forName(clzString);
                AbstractEvent eve = (AbstractEvent) clz.newInstance();
                sleepEvents.add(eve);
            } catch (Exception e) {
                logger.error("Create Campfire Event Failed: " + clzString);
                logger.error("Create Campfire Event Failed: " + e.getLocalizedMessage());
                continue;
            }
        }
    }

}
