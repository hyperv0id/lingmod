package lingmod.cards.attack;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.TransformDrawPileAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 黑子：打6 X 次，次数为黑子数量。
 * 在抽牌堆放入一张黑子
 */
@CardConfig(damage = 6, magic = 1)
@Credit(username = "阿尼鸭Any-a", platform = Credit.LOFTER, link = "https://anyaaaaa.lofter.com/post/1d814764_2b82a7434")
public class BlackPawn extends AbstractEasyCard {
    public final static String ID = makeID(BlackPawn.class.getSimpleName());

    public BlackPawn() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
        if (Wiz.isStanceNell())
            addToBot(new TransformDrawPileAction(this, cardStrings.EXTENDED_DESCRIPTION[0]));
    }

    @Override
    public void upp() {
        upgradeDamage(1);
    }
}
// "${ModID}:BlackPawn": {
// "NAME": "BlackPawn",
// "DESCRIPTION": ""
// }