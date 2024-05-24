package lingmod.Events;

import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;

import lingmod.ModCore;

public class BeansEvent extends AbstractImageEvent {

    public static final String ID = ModCore.makeID("BeansEvent");
    public static final String MAIN_IMG = ModCore.makeImagePath("events/BeansEvent.png");
    public static final String NIAN_IMG = ModCore.makeImagePath("events/BeansEvent_Nian.png");
    public static final String DUSK_IMG = ModCore.makeImagePath("events/BeansEvent_Dusk.png");
    public static final String LING_IMG = ModCore.makeImagePath("events/BeansEvent_Ling.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final Logger logger = ModCore.logger;
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public BeansEvent() {
        super(NAME, DESCRIPTIONS[0], MAIN_IMG);
        for (String op : OPTIONS) {
            this.imageEventText.setDialogOption(op);
        }
    }

    @Override
    protected void buttonEffect(int i) {
        switch (this.screenNum){
            case 0:
                mainScreen(i);
                break;
            case 1:
                secondScreen(i);
                break;
            default:
                this.openMap();
                break;
        }
    }

    private void secondScreen(int i) {
        this.openMap();
    }

    private void mainScreen(int i) {

        // float width = (float) Settings.WIDTH / 2;
        // float height = (float)Settings.HEIGHT / 2;
        // AbstractRelic relic = null;
        // AbstractPlayer player = AbstractDungeon.player;

        this.imageEventText.updateBodyText(DESCRIPTIONS[i+1]);
        this.screenNum = 1;
        switch (i){
            case 0:
                this.imageEventText.loadImage(NIAN_IMG);
                logger.warn(getClass().getSimpleName() + "UnImplemented: 选择年泡泡");
                break;
            case 1:
                this.imageEventText.loadImage(DUSK_IMG);
                logger.warn(getClass().getSimpleName() + "UnImplemented: 选择夕泡泡");
                break;
            case 2:
                this.imageEventText.loadImage(LING_IMG);
                logger.warn(getClass().getSimpleName() + "UnImplemented: 选择令泡泡");
                break;
            default:
                this.openMap();
                break;
        }
        this.imageEventText.clearRemainingOptions();
        // TODO: 添加新的子对话选项
        this.imageEventText.updateDialogOption(0, OPTIONS[OPTIONS.length - 1]);

    }
}
