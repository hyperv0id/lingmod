package lingmod.cards.attack;

import static lingmod.ModCore.logger;
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
public class TianZhui extends AbstractEasyCard{
    public static final String ID = makeID(TianZhui.class.getSimpleName());

    public TianZhui() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 4;
        this.baseMagicNumber = 2;
    }

    @Override
    public void upp() {
        this.upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            calculateCardDamage(m);
            logger.info("---TianZhui------------Damage: " + damage);
            addToBot(new TianZhuiAction(m, new DamageInfo(p, damage, DamageType.NORMAL)));
        }
    }
}
