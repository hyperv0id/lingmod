package lingmod.potions;

import static lingmod.ModCore.makeID;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Bite;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import lingmod.ModCore;
import lingmod.character.Ling;

/**
 * 瓶装剑术：本次战斗中，所有打击防御的耗能变为0
 */
public class BottledSword extends AbstractEasyPotion implements PostBattleSubscriber {

    public static String ID = makeID(BottledSword.class.getSimpleName());

    public static ArrayList<CardGroup> getAllCardGroup() {
        ArrayList<CardGroup> cg = new ArrayList<>();
        cg.add(AbstractDungeon.player.hand);
        cg.add(AbstractDungeon.player.discardPile);
        cg.add(AbstractDungeon.player.drawPile);
        cg.add(AbstractDungeon.player.exhaustPile);
        return cg;
    }

    public BottledSword() {
        super(ID, PotionRarity.COMMON, PotionSize.ANVIL, new Color(0.4f, 0.2f, 0.5f, 1f),
                new Color(0.6f, 0.8f, 1.0f, 1f), null, Ling.Enums.PLAYER_LING, ModCore.characterColor);
    }

    public int getPotency(int ascensionlevel) {
        return 0;
    }

    /**
     * 将所有打击防御变为0费
     */
    @Override
    public void use(AbstractCreature arg0) {
        for (CardGroup cg : getAllCardGroup()) {
            cg.group.stream().filter(c -> isStarterSD(c)).forEach(c -> {
                c.cost = 0;
                c.costForTurn = 0;
                c.isCostModifiedForTurn = true;
            });
        }
        BaseMod.subscribe(this);
    }

    public String getDescription() {
        return strings.DESCRIPTIONS[0];
    }

    public static boolean isStarterSD(AbstractCard card) {
        return card.hasTag(CardTags.STARTER_DEFEND) ||
                card.hasTag(CardTags.STARTER_STRIKE) ||
                card.cardID.equals(Bite.ID);
    }

    @Override
    public void receivePostBattle(AbstractRoom arg0) {
        for (CardGroup cg : getAllCardGroup()) {
            cg.group.stream().filter(c -> isStarterSD(c)).forEach(c -> {
                c.cost = 1;
                c.costForTurn = 1;
                c.isCostModifiedForTurn = true;
            });
        }
        BaseMod.unsubscribe(this);
    }
}
