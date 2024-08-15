package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.SummonGremlinAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.monsters.whoisreal.AYao;

import static lingmod.ModCore.makeID;

/**
 * 我是谁？
 * 敌人失去50%生命，生成一个 墨魉
 */
public class Whoami_Dusk extends AbstractEasyCard {
    public static final String NAME = Whoami_Dusk.class.getSimpleName();
    public static final String ID = makeID(NAME);
    public AbstractMonster[] monsters = new AbstractMonster[3];

    public Whoami_Dusk() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (monsters.length == 0) {
            monsters = new AbstractMonster[]{
                    new AYao(),
                    new AYao(),
                    new AYao()
            };
        }
        int amt = m.currentHealth / 2;
        addToBot(new DamageAction(m, new DamageInfo(p, amt, DamageInfo.DamageType.HP_LOSS)));
        // addToBot(new ApplyPowerAction(p, p, new NoDebuffFromOther(p)));
        AbstractDungeon.actionManager.addToBottom(new SummonGremlinAction(this.monsters));
    }
}