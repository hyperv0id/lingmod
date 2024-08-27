package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.monsters.Thunderer_Summon;
import lingmod.util.MonsterHelper;

import static lingmod.ModCore.makeID;

/**
 * 特殊情况: summon Thunderer
 */
@CardConfig(magic = 1, damage = 5)
//@AutoAdd.Ignore
public class Thunderer_SummonCard extends AbstractEasyCard {
    public static final String NAME = Thunderer_SummonCard.class.getSimpleName();

    public static final String ID = makeID(NAME);

    public Thunderer_SummonCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster monster) {
        int cnt = MonsterHelper.cntSummons();
        if (cnt >= magicNumber) {
            String msg = String.format("我不能召唤超过 %d 个召唤物", magicNumber);
            addToBot(new TalkAction(true, msg, 2.0F, 2.0F));
        }
        addToBotAbstract(() -> {
            Thunderer_Summon mo = (Thunderer_Summon) MonsterHelper.spawnMonster(Thunderer_Summon.class);
            // mo.animX = 1200F * Settings.xScale;
            mo.init();
            mo.applyPowers();
            mo.setDamage(damage);
            AbstractDungeon.getCurrRoom().monsters.addMonster(0, mo);
        });
    }

    private int getSmartPosition(AbstractMonster m) {
        int position = 0;
        for (AbstractMonster mo :
                AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!(m.drawX > mo.drawX))
                break;
            ++position;
        }
        return position;
    }

    @Override
    public void upp() {
    }
}
