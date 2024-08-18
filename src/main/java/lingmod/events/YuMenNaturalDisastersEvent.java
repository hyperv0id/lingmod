package lingmod.events;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import lingmod.ModCore;
import lingmod.interfaces.CampfireSleepEvent;


@CampfireSleepEvent
@AutoAdd.Ignore
public class YuMenNaturalDisastersEvent extends AbstractImageEvent {
    public static final String ID = ModCore.makeID("YuMenNaturalDisastersEvent");
    public static final String IMG_PATH = ModCore.makeImagePath("events/YuMenNaturalDisastersEvent.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    public YuMenNaturalDisastersEvent() {
        super(NAME, DESCRIPTIONS[0], IMG_PATH);
        for (String op : OPTIONS) {
            this.imageEventText.setDialogOption(op);
        }
    }

    @Override
    protected void buttonEffect(int i) {
        openMap();
    }
}
