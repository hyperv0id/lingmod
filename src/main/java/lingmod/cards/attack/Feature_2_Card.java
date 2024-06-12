package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.BaseMod;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import basemod.interfaces.PostExhaustSubscriber;
import lingmod.cards.AbstractPoemCard;
import lingmod.cards.mod.PoemMod;
import lingmod.cards.mod.WineMod;
import lingmod.util.CustomTags;

/**
 * 随付笺咏醉屠苏: 召唤物被击倒/吸收/回收时令额外获得4(+1)点技力、攻击力+3%（攻击力加成最多叠加5层）
 */
public class Feature_2_Card extends AbstractPoemCard implements PostExhaustSubscriber {
    public static final String ID = makeID(Feature_2_Card.class.getSimpleName());

    public Feature_2_Card() {
        super(ID, 5, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, 2);
        CardModifierManager.addModifier(this, new RetainMod());
        this.tags.add(CustomTags.WINE);
        CardModifierManager.addModifier(this, new WineMod(1));
        this.tags.add(CustomTags.POEM);
        CardModifierManager.addModifier(this, new PoemMod(2));
        baseDamage = 8;
        baseMagicNumber = 1;
        baseSecondMagic = 5;
        BaseMod.subscribe(this);
    }

    @Override
    public boolean canUpgrade() {
        return timesUpgraded < secondMagic;
    }

    @Override
    public void upp() {
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
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null && AbstractDungeon.player.hand.contains(this))
            this.upgrade();
        else if (card.isEthereal) // 虚无牌会在回合后消耗，但是不会触发上面的逻辑
            this.upgrade();
    }
}
