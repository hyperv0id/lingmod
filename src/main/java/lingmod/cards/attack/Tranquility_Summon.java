package lingmod.cards.attack;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.monsters.Tranquility_SummonMonster;
import lingmod.relics.SanYiShiJian;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

@CardConfig(summonClz = Tranquility_SummonMonster.class, magic = 10, magic2 = 1)
@Credit(link = "https://www.pixiv.net/artworks/106018673", username = "小动物管理员", platform = "pixiv")
public class Tranquility_Summon extends AbstractEasyCard implements CustomSavable<CardSave> {

    public static final String ID = makeID(Tranquility_Summon.class.getSimpleName());

    public Tranquility_Summon() {
        this(false);
    }

    public Tranquility_Summon(boolean hasRelic) {
        super(ID, 1, CardType.SKILL, hasRelic ? CardRarity.BASIC : CardRarity.SPECIAL, CardTarget.SELF);
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
    public void upp() {
        upgradeMagicNumber(2);
        upgradeSecondMagic(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardConfig cc = this.getClass().getAnnotation(CardConfig.class);
        MonsterHelper.summonMonster(cc.summonClz(), magicNumber, secondMagic);
    }

    @Override
    public CardSave onSave() {
        return new CardSave(Tranquility.ID, timesUpgraded, 0);
    }

    @Override
    public void onLoad(CardSave cardSave) {

    }
}
