package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;
/**
 * “逍遥”：给予 !D! 点缠绕。
 * 如果血量低于一半，那么 消耗 此牌 NL 消耗时抽 !M! 张牌，获得 !M! [E]
 */
public class Peripateticism extends AbstractEasyCard{
    public static final String ID = makeID(Peripateticism.class.getSimpleName());

    public Peripateticism() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 10;
    }


    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        addToBot(new GainEnergyAction(this.magicNumber)); // 获得能量
        addToBot(new MakeTempCardInDiscardAction(makeCopy(), 1)); // 创建复制
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new ConstrictedPower(m, p, damage)));
        // addToBot(new DamageAction(m, new DamageInfo(p, damage)));
        if(p.currentHealth <= p.maxHealth/2) {
            addToBot(new ExhaustSpecificCardAction(this, p.hand));
        }
    }
}
