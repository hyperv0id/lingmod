package lingmod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 所有手牌费用随机化，使用红包算法
 */
public class Beans_LingRelic extends AbstractEasyRelic implements ClickableRelic {
    public static final String ID = makeID(Beans_LingRelic.class.getSimpleName());

    public int totalCost, cardCnt;

    public Beans_LingRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.grayscale = false;
    }

    @Override
    public void onRightClick() {
        if (this.grayscale) return;
        Wiz.addToBotAbstract(() -> Wiz.redistributeCardCosts(AbstractDungeon.player.hand.group));
        this.grayscale = true;
    }


}