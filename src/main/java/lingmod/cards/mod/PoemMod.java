package lingmod.cards.mod;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.AbstractCardModifier;
import lingmod.powers.PoeticMoodPower;

public class PoemMod extends AbstractCardModifier {
    public PoemMod() {

    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p,
                new PoeticMoodPower(p, card.cost < 1 ? 1 : card.cost)));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PoemMod();
    }
}
