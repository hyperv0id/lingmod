package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.BaseMod;
import basemod.interfaces.PostExhaustSubscriber;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.PoeticMoodPower;

/**
 * 随付笺咏醉屠苏: 召唤物被击倒/吸收/回收时令额外获得4(+1)点技力、攻击力+3%（攻击力加成最多叠加5层）
 */
@CardConfig(damage = 8, magic = 1, magic2 = 5)
@Credit(link = "https://www.pixiv.net/artworks/116639419", platform = Credit.PIXIV, username = "-Flaxencat-")
public class Feature_2_Card extends AbstractEasyCard implements PostExhaustSubscriber {
    public static final String ID = makeID(Feature_2_Card.class.getSimpleName());

    public Feature_2_Card() {
        super(ID, 5, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        BaseMod.subscribe(this);
    }

    @Override
    public void upp() {
        gain();
    }

    protected void gain() {
        if (this.cost > 0)
            updateCost(-1);
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            dmg(m, null);
        }
    }

    /**
     * @param card 消耗的卡牌
     */
    @Override
    public void receivePostExhaust(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null && p.hand != null && p.hand.contains(this) || p.limbo.contains(this)) {
            this.gain();
            addToBot(new ApplyPowerAction(p, p,
                    new PoeticMoodPower(p, 1)));
        }
    }
}