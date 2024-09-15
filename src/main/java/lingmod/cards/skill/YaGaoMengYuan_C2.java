package lingmod.cards.skill;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;

import static lingmod.ModCore.makeID;
import static lingmod.util.Wiz.isStanceNell;

/**
 * 能量上限++ & 手牌翻倍
 */
@AutoAdd.Ignore
public class YaGaoMengYuan_C2 extends YaGaoMengYuan {
    public static final String ID = makeID(YaGaoMengYuan_C2.class.getSimpleName());
    public static final CardStrings cs = CardCrawlGame.languagePack.getCardStrings(ID);

    public YaGaoMengYuan_C2() {
        super(YaGaoMengYuan.ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        this.cardID = ID;
        this.name = cs.NAME;
        this.rawDescription = cs.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void onChoseThisOption() {
        super.onChoseThisOption();
        AbstractPlayer p = AbstractDungeon.player;
        addToBotAbstract(() -> p.energy.energy++);
        addToBot(new GainEnergyAction(1));
        boolean nellStance = isStanceNell();

        p.hand.group.stream()
                .filter(c -> c.cost >= 0) // 排除X牌，大部分状态诅咒
                .filter(c -> c.type != CardType.CURSE)
                .filter(c -> c.type != CardType.STATUS)
                .filter(c -> !nellStance || c.type == CardType.SKILL) // 梦中只判断技能牌
                .forEach(c -> {
                    c.cost <<= 1;
                    c.costForTurn = c.cost;
                });
    }
}
