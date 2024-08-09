package lingmod.cards.skill;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.List;
import java.util.stream.Collectors;

import static lingmod.ModCore.makeID;
import static lingmod.util.Wiz.isStanceNell;

/**
 * 本回合翻倍 & 卡牌++
 */
@AutoAdd.Ignore
public class YaGaoMengYuan_C1 extends YaGaoMengYuan {
    public static final String ID = makeID(YaGaoMengYuan_C1.class.getSimpleName());
    public static final CardStrings cs = CardCrawlGame.languagePack.getCardStrings(ID);

    public YaGaoMengYuan_C1() {
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
        addToBot(new GainEnergyAction(EnergyPanel.getCurrentEnergy()));
        boolean nellStance = isStanceNell();
        List<AbstractCard> cards =
                p.hand.group.stream()
                        .filter(c -> c.cost >= 0) // 排除X牌，大部分状态诅咒
                        .filter(c -> c.type != CardType.CURSE)
                        .filter(c -> c.type != CardType.STATUS)
                        .filter(c -> !nellStance || c.type == CardType.SKILL) // 梦中只判断技能牌
                        .collect(Collectors.toList());
        addToBotAbstract(() -> cards.forEach(c -> c.costForTurn++));
    }
}
