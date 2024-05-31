package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.actions.TianZhuiAction;
import lingmod.cards.AbstractEasyCard;

/**
 * 天坠: 4*2, 记录伤害值，之后的伤害增加X点
 */
public class TianZhui extends AbstractEasyCard {
    public static final String ID = makeID(TianZhui.class.getSimpleName());

    public TianZhui() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
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
