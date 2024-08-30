package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConstrictedPower;

import lingmod.actions.MyApplyPower_Action;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.util.MonsterHelper;

/**
 * “逍遥”：消耗所有技能牌，每张给予 8 缠绕
 */
@CardConfig(magic = 4, isSummon = true)
@Credit(username = "没有名字", platform = Credit.LOFTER, link = "https://gohanduck.lofter.com/post/1f3831ee_2b8230298")
public class Peripateticism extends AbstractEasyCard {
    public static final String ID = makeID(Peripateticism.class.getSimpleName());

    public Peripateticism() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBotAbstract(() -> {
            // 获取所有技能牌
            List<AbstractCard> cardsToExhaust = AbstractDungeon.player.hand.group.stream()
                    .filter(c -> c.type != CardType.ATTACK)
                    .collect(Collectors.toList());
            cardsToExhaust.forEach(c -> {
                // 消耗
                addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                // 对怪物施加缠绕效果
                MonsterHelper.allMonsters().forEach(mo -> addToTop(new MyApplyPower_Action(mo, p, new ConstrictedPower(mo, p, magicNumber))));
            });
            addToBotAbstract(() -> {
                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                    ConstrictedPower pw = (ConstrictedPower) mo.getPower(ConstrictedPower.POWER_ID);
                    if (pw != null) {
                        pw.atEndOfTurn(false);
                    }
                }
            });
        });
    }
}
