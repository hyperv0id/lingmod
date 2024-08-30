package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;

import lingmod.actions.MyApplyPower_Action;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.WinePower;

/**
 * 加速敌人的意图，如果有人不再攻击，那么获得 !M! 酒
 */
@CardConfig(magic = 4)
@Credit(username = "rab_langdon 兔尘", platform = Credit.PIXIV, link = "https://www.pixiv.net/artworks/97025503")
public class QiangJinJiu extends AbstractEasyCard {
    public final static String ID = makeID(QiangJinJiu.class.getSimpleName());

    public QiangJinJiu() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBotAbstract(() -> AbstractDungeon.getMonsters().monsters.forEach(mo -> {
            mo.rollMove();
            // mo.applyTurnPowers();
            mo.createIntent();
            Intent intent = mo.intent;
            if (!intent.toString().contains("ATTACK")) {
                addToTop(new MyApplyPower_Action(p, p, new WinePower(p, 1)));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }
}
// "lingmod:QiangJinJiu": {
// "NAME": "QiangJinJiu",
// "DESCRIPTION": ""
// }