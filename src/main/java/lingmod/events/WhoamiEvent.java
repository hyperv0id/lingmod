package lingmod.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import lingmod.cards.attack.Whoami_Dusk;
import lingmod.cards.power.Whoami_Ling;
import lingmod.cards.power.Whoami_Nian;
import lingmod.cards.power.Whoami_Shuo;
import lingmod.cards.skill.Whoami_Wang;
import lingmod.interfaces.CampfireSleepEvent;

import static lingmod.ModCore.makeID;


@CampfireSleepEvent
public class WhoamiEvent extends AbstractImageEvent {
    public static final String EVENT_NAME = WhoamiEvent.class.getSimpleName();
    public static final String ID = makeID(EVENT_NAME);
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String MAIN_ING = "images/events/theNest.jpg";
    // public static final String MAIN_ING = makeImagePath("events/Whoami_Main.png");


    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private WhoamiEvent.CurScreen curScreen = CurScreen.CHOICE;

    AbstractCard[] cards = {
        new Whoami_Shuo(),
        new Whoami_Wang(),
        new Whoami_Ling(),
        new Whoami_Nian(),
        new Whoami_Dusk(),
    };

    public WhoamiEvent() {
        super(NAME, DESCRIPTIONS[0], MAIN_ING);
        for (int i = 0; i < cards.length; i++) {
            this.imageEventText.setDialogOption(OPTIONS[i], cards[i]);
        }
    }


    @Override
    protected void buttonEffect(int no) {
        // TODO: 图像没改
        switch (this.curScreen) {
            case CHOICE:
                choseCard(no);
                this.curScreen = CurScreen.RESULT;
                break;
            case RESULT:
                this.openMap();
                break;
            default:
                this.openMap();
                break;
        }
    }

    private void choseCard(int btn) {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cards[btn].makeCopy(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        
        this.imageEventText.updateBodyText(DESCRIPTIONS[btn+1]);
        imageEventText.setDialogOption(OPTIONS[OPTIONS.length-1]);
        this.imageEventText.clearRemainingOptions();
    }


    private static enum CurScreen {
        CHOICE,
        RESULT;

        private CurScreen() {
        }
    }
}
