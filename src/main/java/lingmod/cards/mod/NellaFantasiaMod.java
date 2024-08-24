package lingmod.cards.mod;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.stances.NeutralStance;
import lingmod.stance.NellaFantasiaStance;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 这张卡牌是诗
 * <p>
 * </p>
 * 打出：切换诗-普通姿态
 * <p>
 * </p>
 * 离开：获得[E]
 */
public class NellaFantasiaMod extends AbsLingCardModifier {

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
        if (Wiz.isStanceNell()) {
            addToBot(new ChangeStanceAction(NeutralStance.STANCE_ID));
        } else {
            addToBot(new ChangeStanceAction(new NellaFantasiaStance()));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new NellaFantasiaMod();
    }

}
