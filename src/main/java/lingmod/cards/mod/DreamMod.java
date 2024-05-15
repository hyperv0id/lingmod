package lingmod.cards.mod;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lingmod.ModCore;

/**
 * 梦：打出x次后永久移除
 */
public class DreamMod extends  AbstractCardModifier{
    protected int limit = 1;
    protected boolean masterCardRemoved = false;

    public DreamMod(int limit) {
        super();
        this.limit = limit;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        ModCore.logger.info("MengMod: onUse Card func");
        --this.limit;
        card.initializeDescription();
        AbstractCard masterCard = StSLib.getMasterDeckEquivalent(card);
        if (masterCard != null) {
            DreamMod dreamMod = null;

            for(AbstractCardModifier mod : CardModifierManager.getModifiers(masterCard, this.identifier(null))) {
                if (mod instanceof DreamMod) {
                    dreamMod = (DreamMod) mod;
                    --dreamMod.limit;
                }
            }

            masterCard.initializeDescription();
            if (dreamMod != null && dreamMod.limit == 0) {
                AbstractDungeon.player.masterDeck.removeCard(masterCard);
                this.masterCardRemoved = true;
            }
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DreamMod(limit);
    }
}
