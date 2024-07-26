package lingmod.util.dailymod;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.localization.RunModStrings;
import lingmod.ModCore;
import lingmod.util.TODO;

public class YuMen extends AbstractDailyMod {
    public static final String ID = ModCore.makeID(YuMen.class.getSimpleName());
    private static final RunModStrings modStrings = CardCrawlGame.languagePack.getRunModString(ID);

    public YuMen() {
        super(ID, modStrings.NAME, modStrings.DESCRIPTION, "", false);
        TODO.info("玉门模式未实现");
    }
}
