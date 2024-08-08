package lingmod.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import lingmod.ModCore.ResourceType;
import lingmod.interfaces.CampfireSleepEvent;
import lingmod.interfaces.Credit;
import lingmod.relics.Beans_LingRelic;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

@CampfireSleepEvent
@Credit(platform = "bilibili", username = "念语losia", link = "https://www.bilibili.com/video/BV1gQ4y1A74t")
public class Beans_Ling extends PhasedEvent {

    public static final String ID = makeID(Beans_Ling.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    protected AbstractRelic relic = new Beans_LingRelic();
    public static String NAME = eventStrings.NAME;

    public Beans_Ling() {
        super(ID, eventStrings.NAME, makeImagePath("Beans_Ling_1.png", ResourceType.EVENTS));

    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        registerPhase(Phases.BEGIN, new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[1] + relic.name, (i) -> {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2),
                            (float) (Settings.HEIGHT / 2), relic);
                    this.imageEventText.loadImage(makeImagePath("Beans_Ling_2.png", ResourceType.EVENTS));
                    transitionKey(Phases.CHOOSE_1);
                }, relic).addOption(OPTIONS[2], (i) -> {
                    this.imageEventText.loadImage(makeImagePath("Beans_Ling_2.png", ResourceType.EVENTS));
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

    enum Phases {
        BEGIN, CHOOSE_1, CHOOSE_2
    }
}
