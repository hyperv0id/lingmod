package lingmod.Events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lingmod.ModCore;
import lingmod.relics.NianOldCoinRelic;
import lingmod.relics.ShuRiceRelic;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Sui12Event extends AbstractImageEvent {

    public static final String ID = ModCore.makeID("Sui12Event");
    public static final String IMG_PATH = ModCore.makeImagePath("events/Sui12Event.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final Logger logger = ModCore.logger;
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public Sui12Event() {
        super(NAME, DESCRIPTIONS[0], IMG_PATH);
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

    private void mainScreen(int buttonNo) {
        float width = (float)Settings.WIDTH / 2;
        float height = (float)Settings.HEIGHT / 2;
        AbstractRelic relic = null;
        AbstractPlayer player = AbstractDungeon.player;

        switch (buttonNo){
            case 0: // 重岳
                logger.warn("UnImplemented: 大哥的 遗物");
                break;
            case 1: // 令
                logger.warn("UnImplemented: 令的 散佚诗简");
                break;
            case 2: // 黍
                if(!player.hasRelic(ShuRiceRelic.ID))
                    relic = new ShuRiceRelic();
                break;
            case 3: // 年
                if(!player.hasRelic(NianOldCoinRelic.ID))
                    relic =  new NianOldCoinRelic();
                break;
            case 4: // 夕
                logger.warn("UnImplemented: 夕的 画");
                break;
            default:
                this.openMap();
                break;
        }
        if(relic != null){
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(width, height ,relic);
        }
        this.screenNum = 1;

        this.imageEventText.clearRemainingOptions();
        // TODO: 添加新的子对话选项
        this.imageEventText.updateDialogOption(0, OPTIONS[OPTIONS.length - 1]);
    }
    private void secondScreen(int buttonNo){
        switch (buttonNo){
            default:
                this.openMap();
                break;
        }
        // TODO: 更多对话
        this.screenNum = 2;
        this.imageEventText.clearRemainingOptions();
        this.imageEventText.updateDialogOption(0, OPTIONS[OPTIONS.length - 1]);
    }
}
