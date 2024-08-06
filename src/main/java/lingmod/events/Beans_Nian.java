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
import lingmod.relics.Beans_NianRelic;

@CampfireSleepEvent
public class Beans_Nian extends PhasedEvent {

    public static final String ID = makeID(Beans_Nian.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static String NAME = eventStrings.NAME;
    public static String IMG_PATH = makeImagePath("Beans_Nian_1.png", ResourceType.EVENTS);
    protected AbstractRelic relic = new Beans_NianRelic();

    public Beans_Nian() {
        super(ID, eventStrings.NAME, IMG_PATH);

        registerPhase(Phases.BEGIN, new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[1] + relic.name, (i) -> {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2),
                            (float) (Settings.HEIGHT / 2), relic);
                    this.imageEventText.loadImage(makeImagePath("Beans_Nian_2.png", ResourceType.EVENTS));
                    transitionKey(Phases.CHOOSE_1);
                }, relic).addOption(OPTIONS[2], (i) -> {
                    this.imageEventText.loadImage(makeImagePath("Beans_Nian_2.png", ResourceType.EVENTS));
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

    static enum Phases {
        BEGIN, CHOOSE_1, CHOOSE_2
    }
}
