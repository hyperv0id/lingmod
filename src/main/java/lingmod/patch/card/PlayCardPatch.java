package lingmod.patch.card;

import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import lingmod.powers.Go_ReadAhead;

/**
 * from Pokemon Regions(MagicGuardPatch)
 */
public class PlayCardPatch {

    public static boolean NegateCardPlay() {
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().monsters != null) {

            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (monster.hasPower(Go_ReadAhead.ID)) {
                    AbstractPower power = monster.getPower(Go_ReadAhead.ID);
                    return power.amount >= 1;
                }
            }
        }

        return false;
    }

    @SpirePatch(
            clz = CardModifierPatches.CardModifierOnUseCard.class,
            method = "Insert"
    )
    public static class NegateUseCardMod {
        public NegateUseCardMod() {
        }

        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(CardModifierManager.class.getName()) && m.getMethodName().equals("onUseCard")) {
                        m.replace("{if(!(" + PlayCardPatch.class.getName() + ".NegateCardPlay())) {$proceed($$);}}");
                    }

                }
            };
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "useCard"
    )
    public static class NegateUseCard {
        public NegateUseCard() {
        }

        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(AbstractCard.class.getName()) && m.getMethodName().equals("use")) {
                        m.replace("{if(!(" + PlayCardPatch.class.getName() + ".NegateCardPlay())) {$proceed($$);}}");
                    }

                }
            };
        }
    }
}
