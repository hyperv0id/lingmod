package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.ModCore;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.util.CardHelper;

/**
 * 长风彻: 0费打6，只有打防：抽3
 */
@Credit(username = "TODO")
@CardConfig(damage = 6, magic = 3)
public class ChangFengChe extends AbstractEasyCard {
    public static final String ID = ModCore.makeID(ChangFengChe.class.getSimpleName());

    public ChangFengChe() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, null);
        addToBotAbstract(() -> {
            if (abstractPlayer.hand.group.stream().allMatch(CardHelper::isStarterSD)) {
                addToBot(new DrawCardAction(magicNumber));
            }
        });
    }
}
