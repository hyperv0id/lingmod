package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;
/**
 * “逍遥”：造成 !D! 点伤害。
 * 如果血量低于一半，那么 消耗 此牌 NL 消耗时抽 !M! 张牌，获得 !M! [E]
 */
public class PeripateticismCard extends AbstractEasyCard{
    public static final String ID = makeID(PeripateticismCard.class.getSimpleName());

    public PeripateticismCard() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 10;
        this.baseMagicNumber = 1;
    }


    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new GainEnergyAction(this.magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage)));
        if(p.currentHealth <= p.maxHealth/2) {
            addToBot(new ExhaustSpecificCardAction(this, p.hand));
        }
    }
}
