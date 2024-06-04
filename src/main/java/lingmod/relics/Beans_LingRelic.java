package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.powers.WinePower;

import static lingmod.ModCore.makeID;

/**
 * 战斗开始获得酒
 */
public class Beans_LingRelic extends AbstractEasyRelic {
    public static final String ID = makeID(Beans_LingRelic.class.getSimpleName());

    public Beans_LingRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);

    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        this.flash();
        addToBot(new ApplyPowerAction(p, p, new WinePower(p, 3), 3));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.grayscale = true;
    }
}