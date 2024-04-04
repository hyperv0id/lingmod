package lingmod.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.ModCore;
import org.apache.logging.log4j.Logger;

import static lingmod.ModCore.makeID;

public class LightPower extends AbstractEasyPower{
    //    public static final String NAME = "LightPower";
    public static final String NAME = "弦惊";
    public static final String ID = makeID("LightPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false; //  是否回合后消失
    public static final Logger logger = ModCore.logger;


    public LightPower(AbstractCreature owner, int amount) {
        super(ID, NAME, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        int cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        if(cardsPlayed <= 1)return;
        AbstractCard lastCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(cardsPlayed - 2);
        sameIdAction(lastCard, card, action);
//        diffTypeAction(lastCard, card, action);
    }

    protected void sameIdAction(AbstractCard lastCard, AbstractCard now, UseCardAction action){
        if(!lastCard.cardID.equals(now.cardID))return;
        now.upgrade(); // 升级本张卡牌
        // TODO: 手牌中所有同号牌发光
    }

    protected void diffTypeAction(AbstractCard lastCard, AbstractCard card, UseCardAction action) {
        if(lastCard.cardID.equals(card.cardID))return;
        AbstractPlayer player = AbstractDungeon.player;
        PoeticMoodPower pmp = null;
//        pmp = (PoeticMoodPower) player.getPower(PoeticMoodPower.ID);
//        if(pmp == null) {
            pmp = new PoeticMoodPower(player, 1);
            player.addPower(pmp);
//        }
//        pmp.addAmount(1);
    }
}
