package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import static java.lang.Integer.max;
import static lingmod.ModCore.makeID;

/**
 * 斗篷扣Alt：回合结束时，获得卡牌总耗能的格挡（配合孤独诅咒可以多起一点防😜
 */
public class AltDouPengKou extends AbstractEasyRelic {
    public static final String ID = makeID(AltDouPengKou.class.getSimpleName());

    public AltDouPengKou() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        int block = AbstractDungeon.player.hand.group.stream()
                .mapToInt(c -> max(c.costForTurn, 0))
                .sum();
        addToBot(new GainBlockAction(AbstractDungeon.player, block));
    }
}
