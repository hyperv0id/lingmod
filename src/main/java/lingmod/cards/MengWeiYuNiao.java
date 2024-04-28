package lingmod.cards;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.util.MonsterHelper;
import lingmod.util.TODO;

import static lingmod.ModCore.makeID;

public class MengWeiYuNiao extends AbstractEasyCard {


    public static final String ID = makeID(MengWeiYuNiao.class.getSimpleName());


    public MengWeiYuNiao() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.exhaust = true;
        baseDamage = 7;
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int totalDamage = 0;

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                mo.createIntent();
                if (!MonsterHelper.isAttackIntent(mo)) continue;
                int moDamage = ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentDmg");
                if (moDamage == -1) continue;
                if (ReflectionHacks.getPrivate(mo, AbstractMonster.class, "isMultiDmg")) {
                    moDamage *= (int) ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentMultiAmt");
                }
                totalDamage += moDamage;
            }
        }
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, totalDamage, DamageInfo.DamageType.NORMAL)));
        TODO.info("永久移除 MengWeiYuNiao");
    }
}
