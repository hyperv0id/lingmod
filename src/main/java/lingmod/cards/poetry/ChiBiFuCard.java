package lingmod.cards.poetry;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import lingmod.cards.AbstractPoetryCard;
import lingmod.util.PowerUtils;
import lingmod.util.Wiz;

import java.util.List;
import java.util.stream.Collectors;

import static lingmod.ModCore.makeID;

/**
 * 赤壁赋: 将你拥有的部分负面效果同步给敌方
 */
public class ChiBiFuCard extends AbstractPoetryCard {
    public static final String ID = makeID(ChiBiFuCard.class.getSimpleName());

    public ChiBiFuCard() {
        super(ID, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
    }

    @Override
    public void use_p(AbstractPlayer p, AbstractMonster m) {
        List<AbstractPower> debuff =
                p.powers.stream().filter(po -> !(po instanceof InvinciblePower) && po.type == AbstractPower.PowerType.DEBUFF).collect(Collectors.toList());
        // 通过实例构造新power，施加给敌人
        for (AbstractPower po : debuff) {
            AbstractPower p2a = PowerUtils.forkPower(po, m);
            if (p2a != null) {
                p2a.amount = Math.max(p2a.amount, 1);
                Wiz.applyToEnemy(m, p2a);
            }
        }
    }

}