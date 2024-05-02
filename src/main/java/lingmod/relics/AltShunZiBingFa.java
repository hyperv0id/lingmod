package lingmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;

import static lingmod.ModCore.makeID;

/**
 * 孙子兵法：不打出攻击牌，下回合的第一张攻击牌免费打出
 */
public class AltShunZiBingFa extends AbstractEasyRelic {
    public static final String ID = makeID(AltShunZiBingFa.class.getSimpleName());
    public static final int cardNum = 1; // 打出多少张牌无效
    public static final int freeNum = 1; // 多少张牌免费
    public static final AbstractCard.CardType type = AbstractCard.CardType.ATTACK;


    protected int cardPlayed = 0; // 本回合打出的牌数量
    protected boolean flag = false; // 能否免费打牌

    public AltShunZiBingFa() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
        this.counter = cardNum;
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        if (flag) {
            AbstractPlayer player = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(player, player, new FreeAttackPower(player, freeNum)));
        }
        this.flag = true;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (c.type == AbstractCard.CardType.ATTACK)
            cardPlayed++;
        this.counter = cardNum - cardPlayed;
        flag = this.counter > 0;
    }
}
