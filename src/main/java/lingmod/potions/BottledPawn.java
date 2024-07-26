package lingmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.PreservedInsect;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import lingmod.ModCore;
import lingmod.actions.LosePotionSlotAction;
import lingmod.character.Ling;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

public class BottledPawn extends AbstractEasyPotion {
    public static String ID = makeID(BottledPawn.class.getSimpleName());
    PreservedInsect pi;

    public BottledPawn() {
        super(ID, PotionRarity.COMMON, PotionSize.ANVIL, new Color(0.2f, 0.4f, 0.9f, 1f),
                new Color(0.6f, 0.8f, 1.0f, 1f), null, Ling.Enums.PLAYER_LING, ModCore.characterColor);
    }

    public int getPotency(int ascensionlevel) {
        return 0;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss);
    }

    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            addToBot(new LosePotionSlotAction());
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (AbstractDungeon.getCurrRoom().eliteTrigger) {
                    Wiz.addToBotAbstract(() -> {
                        mo.currentHealth = (int) ((float) mo.maxHealth * 0.5);
                        mo.healthBarUpdatedEvent();
                    });
                } else { // 普通怪物
                    addToBot(new InstantKillAction(mo));
                }
            }
        }

    }

    public String getDescription() {
        return strings.DESCRIPTIONS[0];
    }
}