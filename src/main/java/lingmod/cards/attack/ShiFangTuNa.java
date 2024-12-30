package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.monsters.AbsSummonMonster;
import lingmod.patch.PlayerPatch;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 统计敌人数量，造成 !D! X 次
 */
@CardConfig(damage = 4, magic = 1)
@Credit(username = "明日方舟", link = "https://www.bilibili.com/video/BV1mm4y1D7JJ")
public class ShiFangTuNa extends AbstractEasyCard {
    public static final String ID = makeID(ShiFangTuNa.class.getSimpleName());

    public ShiFangTuNa() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        isMultiDamage = true;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseMagicNumber = (int) AbstractDungeon.getCurrRoom().monsters.monsters.stream()
                .filter(m2 -> !m2.isDeadOrEscaped())
                .count();
        // 统计玩家和召唤物
        if (!Wiz.adp().isDeadOrEscaped()) baseMagicNumber++;
        AbsSummonMonster summon = PlayerPatch.getSummon();
        if (summon != null && !summon.isDeadOrEscaped()) baseMagicNumber++;
        super.calculateCardDamage(mo);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (magicNumber > 3) {
            this.addToBot(new SFXAction("ATTACK_BOWLING"));
        }
        for (int i = 0; i < magicNumber; i++) {
            dmg(m, null);
        }
    }

    @Override
    public void upp() {
        upgradeDamage(1);
    }
}
