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
 * 右键获得1力量，战斗胜利增加计数
 */
public class Beans_ShuoRelic extends AbstractEasyRelic implements ClickableRelic {
    public static final String ID = makeID(Beans_ShuoRelic.class.getSimpleName());

    public Beans_ShuoRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        this.counter = 3;
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
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1)));
        this.grayscale = --this.counter <= 0;
    }
}