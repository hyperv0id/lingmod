package lingmod.cards.attack;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.TianZhuiAction;
import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;

/**
 * 天坠: 4*2, 记录伤害值，之后的伤害增加X点
 */
@AutoAdd.Ignore
public class TianZhui extends AbstractEasyCard {
    public static final String ID = makeID(TianZhui.class.getSimpleName());

    public TianZhui() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 8;
    }

    @Override
    public void upp() {
        this.upgradeDamage(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TianZhuiAction(m, new DamageInfo(p, damage, DamageType.NORMAL)));
    }
}
