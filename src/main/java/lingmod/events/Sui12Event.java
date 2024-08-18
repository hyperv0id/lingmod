package lingmod.events;

import basemod.AutoAdd.Ignore;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lingmod.ModCore;
import lingmod.interfaces.CampfireSleepEvent;
import lingmod.relics.NianAncientCasting;
import lingmod.relics.Shu_SeedRelic;

import static lingmod.ModCore.logger;

/**
 * 事件：岁中十二人
 * "你小心翼翼地走进了这间昏暗的房间。昏暗的光线从门口照进来，隐约勾勒出正前方那幅巨大的古画轮廓。NL 当你走近一看，画上竟然描绘着十二个人的面孔，每一张都栩栩如生，仿佛随时会从画中走出。NL 古画下方摆放着一张石桌，桌面上布满灰尘，但中央却空出了12块块区域，似乎是专门用来摆放贡品的地方。NL #r献上贡品 吧，一股莫名的力量驱使着你。"
 * TODO: 有BUG不能拿遗物
 */
@Ignore
@CampfireSleepEvent
public class Sui12Event extends AbstractImageEvent {
    public static final String ID = ModCore.makeID("Sui12Event");
    public static final String IMG_PATH = ModCore.makeImagePath("events/Sui12Event.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private ScreenID screenID = ScreenID.INTRO;


    public Sui12Event() {
        super(NAME, DESCRIPTIONS[0], IMG_PATH);

        this.imageEventText.setDialogOption(OPTIONS[0]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (this.screenID) {
            case INTRO:
                introScreen(i);
                break;
            case MAIN_CHOSE:
                selectScreen(i);
                break;
            case EXIT:
            default:
                exit();
                break;
        }
    }

    private void exit() {
        this.openMap();
    }

    private void introScreen(int btn) {
        //        screenNum = 0;
        this.screenNum++;
        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
        this.imageEventText = new GenericEventDialog();
        for (int i = 1; i < OPTIONS.length; i++) {
            String op = OPTIONS[i];
            this.imageEventText.setDialogOption(op);
        }
        screenID = ScreenID.MAIN_CHOSE;
    }

    private void selectScreen(int buttonNo) {
        //        screenNum = 1;
        float width = (float) Settings.WIDTH / 2;
        float height = (float) Settings.HEIGHT / 2;
        AbstractRelic relic = null;
        AbstractPlayer player = AbstractDungeon.player;

        switch (buttonNo) {
            case 0: // 重岳
                logger.warn("UnImplemented: 大哥的 遗物：升级初始遗物");
                screenID = ScreenID.CHOSE_CHONGYUE;
                break;
            case 1: // 令
                logger.warn("UnImplemented: 令的 散佚诗简");
                screenID = ScreenID.CHOSE_LING;
                break;
            case 2: // 黍
                if (!player.hasRelic(Shu_SeedRelic.ID))
                    relic = new Shu_SeedRelic();
                screenID = ScreenID.CHOSE_SHU;
                break;
            case 3: // 年
                if (!player.hasRelic(NianAncientCasting.ID))
                    relic = new NianAncientCasting();
                screenID = ScreenID.CHOSE_NIAN;
                break;
            case 4: // 夕
                // TODO: CHOSE DUSK
                screenID = ScreenID.CHOSE_DUSK;
                break;
            default:
                screenID = ScreenID.EXIT;
                exit();
                break;
        }
        if (relic != null) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(width, height, relic);
        }
        this.screenNum = 1;

        //
        this.imageEventText.clearRemainingOptions();
        // TODO: 添加新的子对话选项
        this.imageEventText.updateDialogOption(0, OPTIONS[0]);
    }

    @SuppressWarnings("unused")
    private void secondScreen(int buttonNo) {
        //        screenNum = 2;
        exit();
        // TODO: 更多对话

        // this.screenNum = 2;
        // this.imageEventText.clearRemainingOptions();
        // this.imageEventText.updateDialogOption(0, OPTIONS[OPTIONS.length - 1]);
    }


    private enum ScreenID {
        INTRO, MAIN_CHOSE, CHOSE_CHONGYUE, CHOSE_LING, CHOSE_SHU, CHOSE_NIAN, CHOSE_DUSK, EXIT
    }
}
