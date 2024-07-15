package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lingmod.cards.AbstractEasyCard;

import java.util.List;
import java.util.stream.Collectors;

import static lingmod.ModCore.makeID;
import static lingmod.util.Wiz.isStanceNell;

/**
 * 能量翻倍，手牌cost++，梦中：只处理技能牌
 */
public class YaGaoMengYuan extends AbstractEasyCard {
    public final static String ID = makeID(YaGaoMengYuan.class.getSimpleName());

    public YaGaoMengYuan() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
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

    @Override
    public void upp() {
    }
}
// "lingmod:YaGaoMengYuan": {
// "NAME": "YaGaoMengYuan",
// "DESCRIPTION": ""
// }