package lingmod.relics;

import basemod.BaseMod;
import basemod.interfaces.StartActSubscriber;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import lingmod.character.Ling;

import static lingmod.ModCore.makeID;

public class Shu_SeedRelic extends AbstractEasyRelic implements ClickableRelic, StartActSubscriber {

    public static final String ID = makeID("Shu_SeedRelic");

    public Shu_SeedRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT, Ling.Enums.LING_COLOR);
        this.counter = 6;
        BaseMod.subscribe(this);
        getUpdatedDescription();
    }

    @Override
    public void onRightClick() {
        if (this.grayscale)
            return;
        if (AbstractDungeon.getCurrMapNode().getRoom() instanceof MonsterRoom) {
            this.flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new RegenPower(AbstractDungeon.player, this.counter)));
            this.grayscale = true;
            this.counter = 0;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], this.counter);
    }

    @Override
    public void receiveStartAct() {
        this.grayscale = false;
        this.counter = 6;
    }
}
