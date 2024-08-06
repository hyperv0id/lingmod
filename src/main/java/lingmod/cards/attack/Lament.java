package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import lingmod.ModCore;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

/**
 * 悲词：统计你和敌人的负面效果，每个获得 !B! 格挡
 */
@CardConfig(block = 8, poemAmount = 2)
public class Lament extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(Lament.class.getSimpleName());

    public Lament() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        long cnt = p.powers.stream().filter(po -> po.type == PowerType.DEBUFF).count();
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            cnt += mo.powers.stream().filter(po -> po.type == PowerType.DEBUFF).count();
        }
        addToBot(new GainBlockAction(p, block * (int) cnt));
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }
}
