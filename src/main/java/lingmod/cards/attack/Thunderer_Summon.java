package lingmod.cards.attack;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;
import lingmod.monsters.Thunderer_SummonMonster;
import lingmod.util.MonsterHelper;

import static lingmod.ModCore.makeID;

@AutoAdd.Ignore
@Credit(link = "https://www.pixiv.net/artworks/106018673", username = "小动物管理员", platform = "pixiv")
public class Thunderer_Summon extends AbstractEasyCard {

    public static final String ID = makeID(Thunderer_Summon.class.getSimpleName());

    public Thunderer_Summon() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        MonsterHelper.summonMonster(Thunderer_SummonMonster.class);
    }
}
