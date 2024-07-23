package lingmod.cards.attack;

import basemod.AutoAdd.Ignore;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

import java.util.stream.Collectors;

import static lingmod.ModCore.makeID;

/**
 * TODO: 还没想好，大概是每少一种牌，增加15点伤害
 */
@Ignore
@CardConfig(damage = 9, magic = 10)
public class DaMoFeiHuo extends AbstractEasyCard {
    public final static String ID = makeID(DaMoFeiHuo.class.getSimpleName());

    public DaMoFeiHuo() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int kinds = AbstractDungeon.player.hand.group.stream()
                .filter(c -> c.type != CardType.CURSE && c.type != CardType.STATUS).map(c -> c.type)
                .collect(Collectors.toSet()).size();
        int add = Math.max(0, 3 - kinds);
        int real = baseDamage;
        baseDamage += add * magicNumber;
        super.calculateCardDamage(mo);
        baseDamage = real;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void upp() {
    }
}
// "lingmod:DaMoFeiHuo": {
// "NAME": "DaMoFeiHuo",
// "DESCRIPTION": ""
// }
