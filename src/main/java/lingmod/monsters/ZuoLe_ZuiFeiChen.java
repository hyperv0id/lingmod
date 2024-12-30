package lingmod.monsters;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import lingmod.ModCore;
import lingmod.powers.NoDrawPlusPower;
import lingmod.ui.VideoBackground;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

/**
 * 挑山人大战掌柜的
 */
public class ZuoLe_ZuiFeiChen extends CustomMonster {
    public static final String ID = makeID(MountainPicker.class.getSimpleName());
    public static final int MAX_HP = 100;
    protected static final MonsterStrings ms = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = ms.NAME;
    protected static final String IMG_PATH = makeImagePath("ZuoLe_ZuiFeiChen.png", ModCore.ResourceType.MONSTERS);
    private boolean firstMove = true;
    public VideoBackground video;

    public ZuoLe_ZuiFeiChen() {
        super(NAME, ID, MAX_HP, -10.0F, -30.0F, 476.0F, 410.0F, IMG_PATH,
                -250.0F, 30.0F);
        this.hb = new Hitbox(img.getWidth() * Settings.scale, img.getHeight() * Settings.scale);
        this.hb_w = hb.width;
        this.hb_h = hb.height;
        this.type = EnemyType.NORMAL;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
    }

    @Override
    public void usePreBattleAction() {
        maintainRoom();
    }

    /**
     * 维护房间机制：
     * 1. 获得999护甲以免被玩家打死，TODO: 根据是否死亡，进入不同的事件
     * 2. 不能抽牌、弃牌、丢弃所有手牌
     * 3. TODO: 根据音轨打牌
     */
    void maintainRoom() {
        AbstractPlayer p = Wiz.adp();
        addToBot(new GainBlockAction(this, 999)); // 获得999护甲以免被玩家打死
        // 用于维护房间视觉
        addToTop(new ApplyPowerAction(p, this, new NoDrawPlusPower(p, 99))); // 99 回合内无法抽牌
        addToTop(new RemoveAllPowersAction(p, false)); // 移除所有能力，保证无法房间抽牌、弃牌
        addToTop(new DiscardAction(p, this, 10, false)); // 丢弃所有手牌
    }


    @Override
    public void takeTurn() {
        maintainRoom();
        if (this.firstMove) {
            firstMove = false;
            this.video = new VideoBackground(ModCore.makePath("video/Ling_3DPV.webm"));
            // 第一回合：切换背景
        }
    }

    @Override
    public void update() {
        super.update();
        // 检查视频是否播放完毕
    }

    @Override
    protected void getMove(int num) {
        // 不会攻击
        setMove((byte) 1, Intent.DEBUG);
    }
}
