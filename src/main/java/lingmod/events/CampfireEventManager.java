package lingmod.events;

import basemod.AutoAdd;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import lingmod.interfaces.CampfireSleepEvent;

import java.util.ArrayList;
import java.util.List;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.modID;

public class CampfireEventManager implements CustomSavable<List<String>> {
    public static List<AbstractEvent> sleepEvents = new ArrayList<>(); // 在篝火处睡觉会触发的事件
    public static List<String> sleptEvents = new ArrayList<>(); // 已经触发过的事件

    public static AbstractEvent rollEvent() {
        if (sleepEvents.isEmpty()) {
            // 如果角色专属事件用光了，那么使用本体事件
            return AbstractDungeon.generateEvent(AbstractDungeon.eventRng);
        }
        int len = sleepEvents.size();
        int r = AbstractDungeon.eventRng.random(len - 1);
        AbstractEvent e = sleepEvents.get(r);
        logger.info("Visit Event: " + e.getClass().getSimpleName());
        sleepEvents.remove(e);
        sleptEvents.add(e.getClass().getName()); // 设置为看过了
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
            if (sleptEvents != null && sleptEvents.contains(event.getClass().getName()))
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
        return sleptEvents;
    }

    @Override
    public void onLoad(List<String> strings) {
        sleptEvents.clear();
        if (strings != null)
            sleptEvents = strings;
        initSleepEvents();
    }

}
