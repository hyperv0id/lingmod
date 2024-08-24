package lingmod.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lingmod.ModCore;
import lingmod.interfaces.CampfireSleepEvent;
import lingmod.relics.Beans_DuskRelic;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

@CampfireSleepEvent
public class AGiftEvent extends PhasedEvent {
    public static final String ID = makeID(AGiftEvent.class.getSimpleName());

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static String NAME = eventStrings.NAME;
    public static String IMG_PATH = makeImagePath("", ModCore.ResourceType.EVENTS);
    protected AbstractRelic relic = new Beans_DuskRelic();

    public AGiftEvent() {
        super(ID, eventStrings.NAME, IMG_PATH);
        registerPhase("INTRO", new TextPhase(DESCRIPTIONS[0]).addOption(OPTIONS[0], (i) -> transitionKey("SELECT")));
        registerPhase("SELECT", new TextPhase(DESCRIPTIONS[1])
                .addOption(OPTIONS[1], (i) -> transitionKey("DO_NOTHING"))
                .addOption(OPTIONS[2], (i) -> transitionKey("HELP"))
        );
        registerPhase("DO_NOTHING", new TextPhase(DESCRIPTIONS[3]).addOption(OPTIONS[1], (i) -> openMap()));
        registerPhase("HELP", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[2], (i) -> transitionKey("BATTLE")));
        registerPhase("BATTLE", new CombatPhase(ID));
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        transitionKey("INTRO");
    }
}
