package lingmod.monsters.whoisreal;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.exordium.GremlinFat;
import lingmod.ModCore;

import static lingmod.ModCore.makeImagePath;

public class TouXian {
    public static final String spinePath = makeImagePath("who_is_real/bi_xian/enemy_1124_spbow", ModCore.ResourceType.MONSTERS);
    public static final String ID = ModCore.makeID(TouXian.class.getSimpleName());
    public static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);

    @SpirePatch(clz = GremlinFat.class, method = SpirePatch.CONSTRUCTOR)
    public static class XiaoZao_InitPatch {
        @SpirePostfixPatch
        public static void init(GremlinFat __inst) {
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
    public static class XiaoZao_AttackSlowAnimPatch {
        @SpirePostfixPatch
        public static void postfix(AbstractCreature __inst) {
            if (__inst instanceof GremlinFat) {
                __inst.state.setAnimation(0, "Attack", false);
                __inst.state.addAnimation(0, "Idle", true, 0F);
            }
        }
    }

    @SpirePatch(clz = AbstractCreature.class, method = "useFastAttackAnimation")
    public static class XiaoZao_AttackFastAnimPatch {
        @SpirePostfixPatch
        public static void postfix(AbstractCreature __inst) {
            if (__inst instanceof GremlinFat) {
                __inst.state.setAnimation(0, "Attack", false);
                __inst.state.addAnimation(0, "Idle", true, 0F);
            }
        }
    }
}
