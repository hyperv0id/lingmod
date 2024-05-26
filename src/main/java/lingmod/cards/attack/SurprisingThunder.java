package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;

public class SurprisingThunder extends AbstractEasyCard {
    public static final String ID = makeID(SurprisingThunder.class.getSimpleName());

    public SurprisingThunder() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 20;
    }

    @Override
    public void upp() {
        upgradeDamage(7);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.LIGHTNING);
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (mo != m) {
                addToBot(new DamageAction(mo, new DamageInfo(p, damage / 2)));
            }
        }
    }

}
