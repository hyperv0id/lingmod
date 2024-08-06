package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.ReflectionHacks;
import lingmod.cards.AbstractWineCard;
import lingmod.interfaces.CardConfig;
import lingmod.util.MonsterHelper;

/**
 * TODO: 无法修改多段
 * 敌人多段攻击，攻击次数减少 20%
 * 否则给予虚弱
 */
@CardConfig(magic = 30, magic2 = 1)
public class HuSongWineCard extends AbstractWineCard {
    public static final String ID = makeID(HuSongWineCard.class.getSimpleName());

    public HuSongWineCard() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, 3);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(10);
        upgradeSecondMagic(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster monster) {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBotAbstract(() -> {
                    mo.createIntent();
                    if (!MonsterHelper.isAttackIntent(mo))
                        return;
                    if ((boolean) basemod.ReflectionHacks.getPrivate(mo, AbstractMonster.class, "isMultiDmg")) {
                        int multi = ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentMultiAmt");
                        multi *= (100 - magicNumber);
                        multi /= 100;
                        ReflectionHacks.setPrivate(mo, AbstractMonster.class, "intentMultiAmt", multi);
                    }
                });
                addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, secondMagic, false)));
            }
        }
    }
}
