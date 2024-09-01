package lingmod.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lingmod.ModCore.ResourceType;
import lingmod.interfaces.CampfireSleepEvent;
import lingmod.monsters.Wang_MountainGhost;
import lingmod.relics.Beans_LingRelic;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

@CampfireSleepEvent
public class Wang_MountainGhostEvent extends PhasedEvent {
    public static final String ID = makeID(Wang_MountainGhostEvent.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static String NAME = eventStrings.NAME;
    protected AbstractRelic relic = new Beans_LingRelic();
    static final String IMG_PATH = makeImagePath("Wang_MountainGhostEvent.png", ResourceType.EVENTS);

    public Wang_MountainGhostEvent() {
        super(ID, eventStrings.NAME, IMG_PATH);
        registerPhase("ENTER", new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[1], (i) -> transitionKey("INSIST"))
                .addOption(OPTIONS[0], (i) -> transitionKey("GIVE_UP")));
        registerPhase("GIVE_UP", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[3], (i) -> openMap()));
        registerPhase("INSIST", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[2], (i) -> transitionKey("BATTLE")));
        registerPhase("BATTLE", new CombatPhase(Wang_MountainGhost.ID).addRewards(true, (room) -> room.addRelicToRewards(AbstractRelic.RelicTier.RARE)));
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        transitionKey("ENTER");
    }
}
