package lingmod.cards.skill;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.monsters.Peripateticism_SummonMonster;
import lingmod.relics.SanYiShiJian;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;


@Credit(link = "https://www.pixiv.net/artworks/106018673", username = "小动物管理员", platform = "pixiv")
@CardConfig(summonClz = Peripateticism_SummonMonster.class, magic = 20, magic2 = 6)
public class Peripateticism_Summon extends AbstractEasyCard {

    public static final String ID = makeID(Peripateticism_Summon.class.getSimpleName());

    public Peripateticism_Summon() {
        this(false);
    }

    public Peripateticism_Summon(boolean hasRelic) {
        super(ID, 1, CardType.SKILL, hasRelic ? CardRarity.BASIC : CardRarity.SPECIAL, CardTarget.SELF);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(5);
        upgradeSecondMagic(3);
    }

    @Override
    public AbstractCard makeCopy() {
        if (Wiz.isPlayerLing() && Wiz.adp().hasRelic(SanYiShiJian.ID)) {

            try {
                return this.getClass().getConstructor(boolean.class).newInstance(false);
            } catch (Exception ignore) {
            }
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardConfig cc = this.getClass().getAnnotation(CardConfig.class);
        MonsterHelper.summonMonster(cc.summonClz(), magicNumber, secondMagic);
    }
}
