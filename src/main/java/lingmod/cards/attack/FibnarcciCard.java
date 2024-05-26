package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;

/**
 * 清平：打5，下次增加3点,升级后打8增加5点。是fibonacci数列
 */
public class FibnarcciCard extends AbstractEasyCard{
    public static final String ID = makeID(FibnarcciCard.class.getSimpleName());

    public static final int COST = 1;

    public static final int F1 = 1;
    public static final int F2 = 1;
    public static final int F3 = 2;


    public FibnarcciCard() {
        super(ID, COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = F3;
        this.baseSecondDamage = F2;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL)));
        int t = baseDamage;
        baseDamage += baseSecondDamage;
        baseSecondDamage = t;
    }
    @Override
    public void upp() {
        upgradeDamage(secondDamage);
        upgradeSecondDamage(damage);
    }
}
