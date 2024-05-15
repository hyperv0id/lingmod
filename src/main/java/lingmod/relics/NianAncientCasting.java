package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import static lingmod.ModCore.makeID;

public class NianAncientCasting extends AbstractEasyRelic{

    public static final String ID = makeID("NianAncientCasting");

    public int actNum; // 地图在哪里
    public static final int BUFFER_NUM = 3; // 获得几层缓冲
    public static final int TURN_BLOCK = 4; // 获得几层缓冲

    public NianAncientCasting(){
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
        this.actNum = AbstractDungeon.actNum;
        this.counter = BUFFER_NUM;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        if(this.actNum != AbstractDungeon.actNum) {
            this.counter = BUFFER_NUM;
        }
        this.actNum = AbstractDungeon.actNum;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if(damageAmount > 0 && this.counter > 0) {
            this.flash();
            this.counter--;
            damageAmount = 0;
        }
        return damageAmount;
    }

    @Override
    public void onPlayerEndTurn() {
        this.flash();
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new GainBlockAction(player, TURN_BLOCK));
    }
}
