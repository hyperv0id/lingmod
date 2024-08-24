package lingmod.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lingmod.ModCore;
import lingmod.interfaces.CampfireSleepEvent;
import lingmod.monsters.MonsterSui_7_Ji;
import lingmod.relics.Beans_DuskRelic;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

@CampfireSleepEvent
public class Shu_HelpEvent extends PhasedEvent {
    public static final String ID = makeID(Shu_HelpEvent.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static String NAME = eventStrings.NAME;
    public static String IMG_PATH = makeImagePath("Shu_HelpHer.png", ModCore.ResourceType.EVENTS);
    public int prob = 50; // 成功率
    protected AbstractRelic relic = new Beans_DuskRelic();

    public Shu_HelpEvent() {
        super(ID, eventStrings.NAME, IMG_PATH);
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        registerPhase(Phases.PRE, new TextPhase(DESCRIPTIONS[0]).addOption(OPTIONS[0], (i) -> {
            registerPhase(Phases.ASK, new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[1], (ii) -> {
                if (Math.random() * 100 < prob) transitionKey(Phases.PRE_COMBAT);
                else transitionKey(Phases.FAILED);
            }));
            transitionKey(Phases.ASK);
        }));

        registerPhase(Phases.FAILED, new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[2], (i) -> openMap()));

        registerPhase(Phases.PRE_COMBAT, new TextPhase(DESCRIPTIONS[3]).addOption(OPTIONS[3], (i) -> transitionKey(Phases.COMBAT)));
        registerPhase(Phases.COMBAT, new CombatPhase(MonsterSui_7_Ji.ID));

        transitionKey(Phases.PRE);
    }

    enum Phases {
        PRE, ASK, HELP, FAILED, PRE_COMBAT, COMBAT
    }
}
