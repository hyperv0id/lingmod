package lingmod.monsters.whoisreal;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.exordium.GremlinWarrior;
import lingmod.ModCore;

/**
 * 给火大地精换个皮
 */
public class AYaoPatch {
    public static final String spinePath = ModCore.makeImagePath("who_is_real/a_yao/enemy_1123_spsbr",
            ModCore.ResourceType.MONSTERS);
    public static final String ID = ModCore.makeID(AYao.class.getSimpleName());
    public static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);

    @SpirePatch(clz = GremlinWarrior.class, method = SpirePatch.CONSTRUCTOR)
    public static class AYao_InitPatch {
        @SpirePostfixPatch
        public static void init(GremlinWarrior __inst) {
            ReflectionHacks.RMethod loadAnimation = ReflectionHacks.privateMethod(AbstractCreature.class, "loadAnimation",
                    String.class,
                    String.class,
                    float.class);
            loadAnimation.invoke(__inst, spinePath + ".atlas", spinePath + ".json", 2F);
            __inst.state.setAnimation(0, "Attack", false);
            __inst.flipHorizontal = true;
            __inst.name = monsterStrings.NAME;
        }
    }

    @SpirePatch(clz = AbstractCreature.class, method = "useSlowAttackAnimation")
    public static class AYao_AttackSlowAnimPatch {
        @SpirePostfixPatch
        public static void postfix(AbstractCreature __inst) {
            if (__inst instanceof GremlinWarrior) {
                __inst.state.setAnimation(0, "Attack", false);
                __inst.state.addAnimation(0, "Idle", true, 0F);
            }
        }
    }

    @SpirePatch(clz = AbstractCreature.class, method = "useFastAttackAnimation")
    public static class AYao_AttackFastAnimPatch {
        @SpirePostfixPatch
        public static void postfix(AbstractCreature __inst) {
            if (__inst instanceof GremlinWarrior) {
                __inst.state.setAnimation(0, "Attack", false);
                __inst.state.addAnimation(0, "Idle", true, 0F);
            }
        }
    }
}
