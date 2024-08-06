package lingmod.events;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import lingmod.ModCore.ResourceType;
import lingmod.interfaces.CampfireSleepEvent;
import lingmod.interfaces.Credit;
import lingmod.relics.Beans_ShuoRelic;

@CampfireSleepEvent
@Credit(platform = "lofter", username = "快要饿死的星河", link = "https://xinghe43472.lofter.com/post/4cfb6ad3_2b8125334")
public class Beans_Shuo extends PhasedEvent {

    public static final String ID = makeID(Beans_Shuo.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static String NAME = eventStrings.NAME;
    public static String IMG_PATH = makeImagePath("Beans_Shuo.png", ResourceType.EVENTS);
    protected AbstractRelic relic = new Beans_ShuoRelic();

    public Beans_Shuo() {
        super(ID, eventStrings.NAME, IMG_PATH);
        registerPhase(Phases.BEGIN, new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[1] + relic.name, (i) -> {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2),
                            (float) (Settings.HEIGHT / 2), relic);
                    this.imageEventText.loadImage(makeImagePath("Beans_Shuo_1.png", ResourceType.EVENTS));
                    transitionKey(Phases.CHOOSE_1);
                })
                .addOption(OPTIONS[2], (i) -> {
                    transitionKey(Phases.CHOOSE_2);
                }));

        registerPhase(Phases.CHOOSE_1, new TextPhase(DESCRIPTIONS[1])
                .addOption(OPTIONS[2], (i) -> openMap()));
        registerPhase(Phases.CHOOSE_2, new TextPhase(DESCRIPTIONS[2])
                .addOption(OPTIONS[2], (i) -> openMap()));

        transitionKey(Phases.BEGIN);
    }

    public enum Phases {
        BEGIN, CHOOSE_1, CHOOSE_2
    }
}
