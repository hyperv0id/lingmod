package lingmod.util.dailymod;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.localization.RunModStrings;
import lingmod.ModCore;
import lingmod.util.TODO;

public class JiangNan extends AbstractDailyMod {

    public static final String ID = ModCore.makeID(JiangNan.class.getSimpleName());
    private static final RunModStrings modStrings = CardCrawlGame.languagePack.getRunModString(ID);
    public JiangNan() {
        super(ID, modStrings.NAME, modStrings.DESCRIPTION, "", false);
        TODO.info("江南模式未实现");
    }
}
