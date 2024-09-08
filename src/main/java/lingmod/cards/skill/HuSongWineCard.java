package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import java.util.HashMap;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.util.MonsterHelper;

/**
 * 敌人多段攻击，攻击次数减少 20%
 * 否则给予虚弱
 */
@CardConfig(magic = 20, magic2 = 1, wineAmount = 2)
@Credit(username = "枯荷倚梅cc", platform = "lofter", link = "https://anluochen955.lofter.com/post/1f2a08fc_2baf8eeb3")
public class HuSongWineCard extends AbstractEasyCard implements OnPlayerDamagedSubscriber {
    public static final String ID = makeID(HuSongWineCard.class.getSimpleName());
    public static HashMap<AbstractCreature, Integer> timesLost = new HashMap<>(); // 敌人失去了多少攻击计数

    public HuSongWineCard() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(10);
        upgradeSecondMagic(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster monster) {
        timesLost.clear();
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBotAbstract(() -> {
                    mo.createIntent();
                    if (!MonsterHelper.isAttackIntent(mo))
                        return;
                    if ((boolean) basemod.ReflectionHacks.getPrivate(mo, AbstractMonster.class, "isMultiDmg")) {
                        int real = ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentMultiAmt");
                        int multi = (int) (real * (100F - magicNumber) / 100F);
                        if (!timesLost.containsKey(mo))
                            timesLost.put(mo, real - multi); // 添加计数
                        ReflectionHacks.setPrivate(mo, AbstractMonster.class, "intentMultiAmt", multi);
                    }
                });
                addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, secondMagic, false)));
            }
        }
        BaseMod.subscribe(this);
    }

    @Override
    public int receiveOnPlayerDamaged(int i, DamageInfo damageInfo) {
        AbstractCreature key = damageInfo.owner;
        if (key == null) return i;
        if (timesLost.containsKey(key)) {
            Integer times = timesLost.get(key);
            if (times > 0) {
                timesLost.replace(key, times - 1);
                return 0;
            } else {
                timesLost.remove(key);
            }
        }
        if (timesLost.isEmpty()) BaseMod.unsubscribeLater(this); // 不再统计
        return i;
    }
}
