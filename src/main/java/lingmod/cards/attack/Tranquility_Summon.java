package lingmod.cards.attack;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.monsters.Tranquility_SummonMonster;
import lingmod.util.MonsterHelper;

import static lingmod.ModCore.makeID;

@AutoAdd.Ignore
@CardConfig(summonClz = Tranquility_SummonMonster.class)
@Credit(link = "https://www.pixiv.net/artworks/106018673", username = "小动物管理员", platform = "pixiv")
public class Tranquility_Summon extends AbstractEasyCard {

    public static final String ID = makeID(Tranquility_Summon.class.getSimpleName());

    public Tranquility_Summon() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardConfig cc = this.getClass().getAnnotation(CardConfig.class);
        MonsterHelper.summonMonster(cc.summonClz());
    }
}
