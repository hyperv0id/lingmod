package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.monsters.AbsSummonMonster;
import lingmod.powers.WinePower;
import lingmod.util.CustomTags;

import static lingmod.ModCore.makeID;

@CardConfig(damage = 16, magic = 2)
public class JiangXiangNaTie extends AbstractEasyCard {
    public final static String ID = makeID(JiangXiangNaTie.class.getSimpleName());

    public JiangXiangNaTie() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        tags.add(CustomTags.WINE);
    }

    public void applyPowers() {
        AbstractPower wine = AbstractDungeon.player.getPower(WinePower.POWER_ID);
        if (wine == null) {
            super.applyPowers();
            return;
        }
        int cache = wine.amount;
        wine.amount *= baseMagicNumber;
        super.applyPowers();
        wine.amount = cache;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {

        AbstractPower wine = AbstractDungeon.player.getPower(WinePower.POWER_ID);
        if (wine == null) {
            super.calculateCardDamage(mo);
            return;
        }
        int cache = wine.amount;
        wine.amount *= baseMagicNumber;
        super.calculateCardDamage(mo);
        wine.amount = cache;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (m instanceof AbsSummonMonster) {
            addToBotAbstract(() -> m.increaseMaxHp(this.damage, true));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(4);
        upgradeMagicNumber(1);
    }
}
// "lingmod:JiangXiangNaTie": {
// "NAME": "JiangXiangNaTie",
// "DESCRIPTION": ""
// }
