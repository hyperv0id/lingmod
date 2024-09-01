package lingmod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import lingmod.actions.MyApplyPower_Action;
import lingmod.interfaces.Credit;

import static lingmod.ModCore.makeID;

/**
 * 每回合 3 活力
 */
@Credit(username = "徵弦OwO", platform = "bilibili", link = "https://www.bilibili.com/video/BV1et4y1f7H1")
public class Beans_NianRelic extends AbstractEasyRelic {
    public static final String ID = makeID(Beans_NianRelic.class.getSimpleName());

    public Beans_NianRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.CLINK);

    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        this.flash();
        addToBot(new MyApplyPower_Action(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, 3)));
    }
}