package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.powers.YuNiaoPower;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;
import static lingmod.util.MonsterHelper.calcIntentDmg;

/**
 * 梦为鱼鸟：计算怪物对你造成的伤害，给予等量伤害, TODO: 你的图像变成这个怪物，怪物攻击你时，同名怪物受到相同伤害。
 * 你梦中变成鸟便振翅直飞蓝天，你梦中变成鱼便摇尾潜入深渊
 */
@CardConfig(isDream = true, damage = 0)
public class YuNiaoDreamCard extends AbstractEasyCard {

    public static final String ID = makeID(YuNiaoDreamCard.class.getSimpleName());

    public YuNiaoDreamCard() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }


    @Override
    public void upp() {
        updateCost(-1);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        baseDamage = calcIntentDmg();
        super.calculateCardDamage(mo);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,
                new DamageInfo(abstractPlayer, damage, DamageInfo.DamageType.NORMAL)));
        if (Wiz.isStanceNell()) {
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new YuNiaoPower(abstractPlayer, abstractMonster)));
        }
    }
}
