package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbsSummonCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.relics.SanYiShiJian;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 清平：造成 !D! 点伤害，将一张牌变为清平。 保留时：对随机敌人打出
 */
@CardConfig(damage = 9, magic = 18, magic2 = 3)
@Credit(link = "https://www.pixiv.net/artworks/106018673", username = "小动物管理员", platform = "pixiv")
public class Tranquility extends AbsSummonCard {

    public static final String ID = makeID(Tranquility.class.getSimpleName());

    public Tranquility() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
    }

    @Override
    public AbstractCard makeCopy() {
        if (Wiz.adp() != null && Wiz.adp().hasRelic(SanYiShiJian.ID)) {
            return new Tranquility_Summon();
        }
        return super.makeCopy();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        if (Wiz.adp() != null && Wiz.adp().hasRelic(SanYiShiJian.ID)) {
            Tranquility_Summon ps = new Tranquility_Summon();
            for (int i = 0; i < timesUpgraded; i++) {
                ps.upgrade();
            }
        }
        return super.makeStatEquivalentCopy();
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.NONE);
        addToBotAbstract(() -> this.dontTriggerOnUseCard = false);
    }
}
