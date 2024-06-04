package lingmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;

import java.util.ArrayList;

import static lingmod.ModCore.logger;

public final class LosePotionSlotAction extends AbstractGameAction {

    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        player.potionSlots--;
        ArrayList<AbstractPotion> potions = player.potions;
        int index = -1;
        for (int i = 0; i < potions.size(); i++) {
            if (potions.get(i) instanceof PotionSlot) {
                index = i;
                break;
            }
        }
        logger.info("lose potion action" + index);
        if(index != potions.size()-1)
            player.obtainPotion(index, copyNextPotion(potions, index));
        for (int i = index + 1; i < player.potionSlots; i++) {
            player.removePotion(potions.get(i));
            player.obtainPotion(i, copyNextPotion(potions, i));
        }
        potions.remove(player.potionSlots);

        player.adjustPotionPositions();
        this.isDone = true;
    }

    private final AbstractPotion copyNextPotion(ArrayList<AbstractPotion> potions, int index) {
        AbstractPotion abstractpotion = ((AbstractPotion) potions.get(index + 1)).makeCopy();
        return abstractpotion == null ? (AbstractPotion) new PotionSlot(index) : abstractpotion;
    }

    public LosePotionSlotAction() {
        duration = Settings.ACTION_DUR_MED;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
    }
}
