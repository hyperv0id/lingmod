package lingmod.relics;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 * 最大生命值+7
 * 回合结束+1生命值
 */
public class Beans_ShuoRelic extends AbstractEasyRelic implements ClickableRelic {
    public static final String ID = makeID(Beans_ShuoRelic.class.getSimpleName());

    public Beans_ShuoRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        logger.info("===============Why MaxHP does not increase");
        AbstractDungeon.player.increaseMaxHp(7, true);
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        this.counter++;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Beans_ShuoRelic();
    }

    @Override
    public void onRightClick() {
        if (this.counter > 0)
            this.grayscale = false;
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1)));
        this.counter--;
        if (this.counter <= 0)
            this.grayscale = true;
    }
}