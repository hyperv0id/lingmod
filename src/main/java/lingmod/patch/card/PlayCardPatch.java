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
import lingmod.monsters.AbsSummonMonster;
import lingmod.powers.Go_ReadAhead;
import lingmod.util.Wiz;

import static lingmod.ModCore.logger;

/**
 * from Pokemon Regions(MagicGuardPatch)
 */
public class PlayCardPatch {

    public static boolean NegateCardPlay() {
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().monsters != null) {

            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (monster.hasPower(Go_ReadAhead.ID)) {
                    AbstractPower power = monster.getPower(Go_ReadAhead.ID);
                    logger.info("NegateCardPlay: {}", power.amount);
                    return power.amount >= 1;
                }
            }
        }

        return false;
    }


    public static boolean checkSummon(AbstractMonster mo) {
        if (mo == null || mo.isDeadOrEscaped())
            return false;
        logger.info("checked summon monster: {}", mo.name);

        return mo instanceof AbsSummonMonster;
    }

    @SpirePatch(clz = CardModifierPatches.CardModifierOnUseCard.class, method = "Insert")
    public static class NegateUseCardMod {
        public NegateUseCardMod() {
        }

        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(CardModifierManager.class.getName())
                            && m.getMethodName().equals("onUseCard")) {
                        m.replace("{if(!(" + PlayCardPatch.class.getName() + ".NegateCardPlay())) {$proceed($$);}}");
                    }

                }
            };
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "useCard", paramtypez = {AbstractCard.class,
            AbstractMonster.class, int.class})
    public static class NegateUseCard {
        public NegateUseCard() {
        }

        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(AbstractCard.class.getName()) && m.getMethodName().equals("use")) {
                        String expr1 = PlayCardPatch.class.getName() + ".NegateCardPlay()";
                        String expr2 = PlayCardPatch.class.getName() + ".checkSummon($2)";
                        String expr3 = Wiz.class.getName() + ".useCardOnSummon($0, $2)";
                        // String expr_full = "if(%s && %s) {$proceed($$);}";
                        String expr_full = "if(%s && %s) {$proceed($$);} else if(%s){%s;}";
                        String format = String.format(expr_full, "!(" + expr1 + ")", "!(" + expr2 + ")", expr2, expr3);
                        logger.info("Summon UseCard Expr: {}", format);
                        m.replace(format);
                    }
                }
            };
        }
    }
}
