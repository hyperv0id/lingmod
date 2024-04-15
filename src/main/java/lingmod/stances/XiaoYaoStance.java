package lingmod.stances;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

import static lingmod.ModCore.makeID;

public class XiaoYaoStance extends AbstractStance {

    public static final String STANCE_ID = "XiaoYaoForm";
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString("XiaoYaoForm");
    private static long sfxId = -1L;
    private int drawNum = 0; // 每回合多抽卡数
    private AbstractPlayer player;

    public XiaoYaoStance(){
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        this.updateDescription();
    }

    @Override
    public void onEndOfTurn() {
        if(player != null)
        {
            drawNum += 1;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player,
                    new DrawCardNextTurnPower(player, drawNum)));
        }
    }

    @Override
    public void onEnterStance() {
        // TODO
        player = AbstractDungeon.player;
        super.onEnterStance();
    }

    @Override
    public void onExitStance() {
        // TODO
        super.onExitStance();
        player = null;
        drawNum = 0;
    }

    @Override
    public void updateDescription() {
    }
}
