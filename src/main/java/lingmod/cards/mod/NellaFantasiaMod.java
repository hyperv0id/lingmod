package lingmod.cards.mod;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import basemod.abstracts.AbstractCardModifier;
import lingmod.stance.NellaFantasiaStance;

public class NellaFantasiaMod extends AbstractCardModifier {

    public static final String ID = makeID(NellaFantasiaMod.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    public NellaFantasiaMod() {
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format(uiStrings.TEXT[0], rawDescription);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        if (AbstractDungeon.player.stance.ID.equals(NellaFantasiaStance.STANCE_ID)) {
            NellaFantasiaStance.remainTurn++;
            NellaFantasiaStance.giveNellPowerToAll(); // 尝试给所有敌人都附上DEBUFF
        } else {
            addToBot(new ChangeStanceAction(new NellaFantasiaStance()));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new NellaFantasiaMod();
    }

}
