package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.actions.TransformDrawPileAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.util.Wiz;

/**
 * 黑子：打6 X 次，次数为黑子数量。
 * 在抽牌堆放入一张黑子
 */
@CardConfig(damage = 4, magic = 2)
@Credit(username = "阿尼鸭Any-a", platform = Credit.LOFTER, link = "https://anyaaaaa.lofter.com/post/1d814764_2b82a7434")
public class BlackPawn extends AbstractEasyCard {
    public final static String ID = makeID(BlackPawn.class.getSimpleName());

    protected static int realBaseDamage = 4;

    public BlackPawn() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        CardConfig conf = this.getClass().getAnnotation(CardConfig.class);
        if (conf != null)
            realBaseDamage = conf.damage();
    }

    @Override
    public void applyPowers() {
        int tmp = (int) Wiz.allCardsInBattle(false).stream().filter(c -> c.cardID.equals(BlackPawn.ID)).count();
        if (Wiz.adp().limbo.contains(this))
            tmp++;
        baseDamage = realBaseDamage + tmp * baseMagicNumber;
        super.applyPowers();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
        if (Wiz.isStanceNell())
            addToBot(new TransformDrawPileAction(this, cardStrings.EXTENDED_DESCRIPTION[0]));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}
// "${ModID}:BlackPawn": {
// "NAME": "BlackPawn",
// "DESCRIPTION": ""
// }