package lingmod.cards.skill;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;
import lingmod.monsters.Peripateticism_SummonMonster;
import lingmod.util.MonsterHelper;

import static lingmod.ModCore.makeID;

@AutoAdd.Ignore
@Credit(link = "https://www.pixiv.net/artworks/106018673", username = "小动物管理员", platform = "pixiv")
public class Peripateticism_Summon extends AbstractEasyCard {

    public static final String ID = makeID(Peripateticism_Summon.class.getSimpleName());

    public Peripateticism_Summon() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        MonsterHelper.summonMonster(Peripateticism_SummonMonster.class);
    }
}
