package lingmod.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import lingmod.ModCore.ResourceType;
import lingmod.interfaces.CampfireSleepEvent;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

@CampfireSleepEvent
public class NianGuestStar extends PhasedEvent {

    public static final String ID = makeID(NianGuestStar.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String IMG_1 = makeImagePath("Nian_Ask.png", ResourceType.EVENTS);
    private static final String IMG_2 = makeImagePath("1721132190506.png", ResourceType.EVENTS);
    private static final String IMG_3 = makeImagePath("Nian_PreBattle.png", ResourceType.EVENTS);
    public static String NAME = eventStrings.NAME;

    public NianGuestStar() {
        super(ID, eventStrings.NAME, IMG_1);
        body = DESCRIPTIONS[0];
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        registerPhase("Nian_Ask",
                new TextPhase(DESCRIPTIONS[0])
                        .addOption(OPTIONS[0], (i) -> {
                            imageEventText.loadImage(IMG_2);
                            transitionKey("Nian_Ask2");
                        })
                        .addOption(OPTIONS[2],
                                (i) -> {
                                    imageEventText.loadImage(IMG_3);
                                    transitionKey("PRE_BATTLE");
                                }));

        registerPhase("Nian_Ask2",
                new TextPhase(DESCRIPTIONS[1])
                        .addOption(OPTIONS[0], (i) -> openMap())
                        .addOption(OPTIONS[1],
                                (i) -> {
                                    imageEventText.loadImage(IMG_3);
                                    transitionKey("PRE_BATTLE");
                                }));
        registerPhase("PRE_BATTLE",
                new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[1], (i) -> transitionKey("BATTLE")));
        // TODO: 替换为夕的画中生灵
        registerPhase("BATTLE", new CombatPhase(MonsterHelper.GREMLIN_GANG_ENC)
                .addRewards(true, AbstractRoom::addPotionToRewards));
        transitionKey("Nian_Ask");
    }
}
