package lingmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.ModCore;
import lingmod.util.CardHelper;
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;

public class LightPower extends AbstractEasyPower {
    //    public static final String NAME = "LightPower";
    public static final String NAME = "弦惊";
    public static final String ID = makeID("LightPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false; //  是否回合后消失
    public static final Logger logger = ModCore.logger;

    public boolean upgradeBoth = false;


    public LightPower(AbstractCreature owner, int amount) {
        super(ID, NAME, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        this.isPostActionPower = true;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {

        AbstractPlayer player = AbstractDungeon.player;
        int level = AbstractDungeon.ascensionLevel;
        this.flash();
        addToBot(new ApplyPowerAction(player, player, new PoeticMoodPower(player, 1, level)));
        AbstractCard lastCard = CardHelper.lastCard(true);
        if(lastCard == null) return;
//        sameIdAction(lastCard, card, action);
        diffTypeAction(lastCard, card, action);
    }

    protected void sameIdAction(AbstractCard lastCard, AbstractCard now, UseCardAction action) {
        if (!lastCard.cardID.equals(now.cardID)) return;
        AbstractPlayer player = AbstractDungeon.player;
        this.flash();
        if (lastCard.canUpgrade()) {
            // 升级特效
            addToBot(new UpgradeSpecificCardAction(lastCard));
            // TODO: 动画时间延长
            //            float x = now.current_x;
            //            float y = now.current_y;
            //            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(now.makeStatEquivalentCopy(), x, y));
            //            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
            //            now.upgrade(); // 升级本张卡牌
        }
        if (!lastCard.exhaust) {
            // 消耗特效
        }
        // TODO: 手牌中所有同号牌发光
    }

    protected void diffTypeAction(AbstractCard lastCard, AbstractCard card, UseCardAction action) {
        if (lastCard.cardID.equals(card.cardID)) return;
    }
}
