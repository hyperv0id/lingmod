package lingmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import lingmod.ModCore;
import lingmod.actions.LosePotionSlotAction;
import lingmod.character.Ling;

import static lingmod.ModCore.makeID;

public class BottledPawn extends AbstractEasyPotion {
    public static String ID = makeID(BottledPawn.class.getSimpleName());

    public BottledPawn() {
        super(ID, PotionRarity.COMMON, PotionSize.ANVIL, new Color(0.2f, 0.4f, 0.9f, 1f),
                new Color(0.6f, 0.8f, 1.0f, 1f), null, Ling.Enums.PLAYER_LING, ModCore.characterColor);
    }

    public int getPotency(int ascensionlevel) {
        return 0;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) && !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite);
    }

    public void use(AbstractCreature target) {
        if(AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            addToBot((AbstractGameAction) new LosePotionSlotAction());
            for (AbstractMonster mo: AbstractDungeon.getCurrRoom().monsters.monsters) {
                addToBot(new InstantKillAction(mo));
            }
        }

    }

    public String getDescription() {
        return strings.DESCRIPTIONS[0];
    }
}