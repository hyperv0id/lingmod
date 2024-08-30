package lingmod.relics;

import static lingmod.ModCore.makeID;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import lingmod.actions.MyApplyPower_Action;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

import basemod.BaseMod;
import basemod.interfaces.StartActSubscriber;
import lingmod.character.Ling;

public class Shu_SeedRelic extends AbstractEasyRelic implements ClickableRelic, StartActSubscriber {

    public static final String ID = makeID("Shu_SeedRelic");

    public Shu_SeedRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT, Ling.Enums.LING_COLOR);
        this.counter = 6;
        BaseMod.subscribe(this);
    }

    @Override
    public void onRightClick() {
        if (this.grayscale)
            return;
        if (AbstractDungeon.getCurrMapNode().getRoom() instanceof MonsterRoom) {
            this.flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new MyApplyPower_Action(AbstractDungeon.player, AbstractDungeon.player,
                    new RegenPower(AbstractDungeon.player, this.counter)));
            this.grayscale = true;
            this.counter = 0;
        }
    }

    @Override
    public void receiveStartAct() {
        this.grayscale = false;
        this.counter = 6;
    }
}
