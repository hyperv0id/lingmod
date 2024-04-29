package lingmod.cards;

import basemod.ReflectionHacks;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.mod.DreamMod;

import static lingmod.ModCore.makeID;
import static lingmod.util.MonsterHelper.calcIntentDmg;

/**
 * 梦为鱼鸟：造成怪物的伤害
 * 你梦中变成鸟便振翅直飞蓝天，你梦中变成鱼便摇尾潜入深渊
 */
public class MengWeiYuNiao extends AbstractEasyCard {

    public static final String ID = makeID(MengWeiYuNiao.class.getSimpleName());

    public MengWeiYuNiao() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        purgeOnUse = true;
        CardModifierManager.addModifier(this, new DreamMod(1));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        baseDamage = calcIntentDmg();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int totalDamage = calcIntentDmg();
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, totalDamage, DamageInfo.DamageType.NORMAL)));
    }
}