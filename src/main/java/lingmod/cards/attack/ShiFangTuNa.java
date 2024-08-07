package lingmod.cards.attack;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

import static lingmod.ModCore.makeID;

/**
 * 统计敌人数量，造成 !D! X 次
 */
@CardConfig(damage = 7, magic = 1)
public class ShiFangTuNa extends AbstractEasyCard {
    public static final String ID = makeID(ShiFangTuNa.class.getSimpleName());

    public ShiFangTuNa() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        isMultiDamage = true;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseMagicNumber = (int) AbstractDungeon.getMonsters().monsters.stream()
                .filter(m -> !m.isDeadOrEscaped())
                .count();
        super.calculateCardDamage(mo);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            dmg(m, null);
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}
