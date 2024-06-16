package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawPower;

public class DrawDownPower extends DrawPower {

    /**
     * 
     * @param owner  持有者
     * @param amount 持有数量，绝对值表示减少数量
     */
    public DrawDownPower(AbstractCreature owner, int amount) {
        super(owner, -Math.abs(amount));
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this));
    }

    @Override
    public void onRemove() {
        AbstractPlayer var10000 = AbstractDungeon.player;
        var10000.gameHandSize += Math.abs(this.amount);
    }

}
