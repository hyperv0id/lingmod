package lingmod.events;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import lingmod.ModCore.ResourceType;
import lingmod.interfaces.CampfireSleepEvent;
import lingmod.relics.Beans_DuskRelic;

@CampfireSleepEvent
public class Beans_Dusk extends PhasedEvent {

    public static final String ID = makeID(Beans_Dusk.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    protected AbstractRelic relic = new Beans_DuskRelic();
    public static String NAME = eventStrings.NAME;

    // TODO: 图中是年，需要PS
    public static String IMG_PATH = makeImagePath("Beans_Dusk.png", ResourceType.EVENTS);

    public Beans_Dusk() {
        super(ID, eventStrings.NAME, IMG_PATH);
        registerPhase(Phases.BEGIN, new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[1] + relic.name, (i) -> {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2),
                            (float) (Settings.HEIGHT / 2), relic);
                    transitionKey(Phases.CHOOSE_1);
                }, relic).addOption(OPTIONS[2], (i) -> {
                    transitionKey(Phases.CHOOSE_2);
                }));

        registerPhase(Phases.CHOOSE_1, new TextPhase(DESCRIPTIONS[1])
                .addOption(OPTIONS[0], (i) -> {
                    AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                    openMap();
                }));

        registerPhase(Phases.CHOOSE_2, new TextPhase(DESCRIPTIONS[2])
                .addOption(OPTIONS[0], (i) -> {
                    AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                    openMap();
                }));
        transitionKey(Phases.BEGIN);
    }

    public enum Phases {
        BEGIN, CHOOSE_1, CHOOSE_2
    }
}
