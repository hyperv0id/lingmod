package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.BlockReturnPower;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 造成 12 点 伤害，如果手牌中每一张牌都是攻击牌，那么对敌人施加随机 debuff 3 次
 */
@CardConfig(damage = 9, magic = 3)
public class TianZhuiCard extends AbstractEasyCard {
    public static final String ID = makeID(TianZhuiCard.class.getSimpleName());

    public TianZhuiCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean allAtk = true;
        for (AbstractCard c : p.hand.group) {
            if (c.type != CardType.ATTACK) {
                allAtk = false;
                break;
            }
        }
        dmg(m, null);
        if (allAtk) {
            this.addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
            Wiz.applyToEnemy(m, new BlockReturnPower(m, magicNumber));
        }
    }
}
