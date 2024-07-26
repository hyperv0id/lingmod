package lingmod.monsters;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.ModCore;
import lingmod.ModCore.ResourceType;

import java.util.ArrayList;

import static lingmod.ModCore.makeImagePath;

/**
 * 二哥你又在下棋哦，陪我喝点酒好不好
 */
public class MonsterSui_2_Wang extends AbstractMonster {
    public static final String ID = ModCore.makeID(MonsterSui_2_Wang.class.getSimpleName());
    public static final MonsterStrings ms = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final float orbOffset = 225.0F; // 棋盘宽高
    protected final static String IMG_PATH = makeImagePath("MonsterSui_2_Wang.png", ResourceType.MONSTERS);
    @SuppressWarnings("rawtypes")
    public ArrayList[][] orbs; // 棋盘大小
    protected boolean firstMove = true;

    public MonsterSui_2_Wang() {
        this(0.0F, 0.0F);
    }

    public MonsterSui_2_Wang(float x, float y) {
        super(ms.NAME, ID, 200, -5.0F, 0.0F, 260.0F, 265.0F, null, x, y);
        this.img = ImageMaster.loadImage(IMG_PATH);
        this.orbs = new ArrayList[3][3];
        this.type = EnemyType.ELITE;
        this.dialogX = (this.hb_x - 70.0F) * Settings.scale;
        this.dialogY -= (this.hb_y - 55.0F) * Settings.scale;
    }

    /**
     *
     */
    @Override
    public void takeTurn() {
        if (firstMove) {
            // 创建棋盘，空白棋子，根据攻击来源换成 黑色/白色。黑色越多，攻击力越高
            firstMove = false;
        }
        // 1. 给最容易被连上的1-2个棋子加盾
    }

    /**
     * @param i
     */
    @Override
    protected void getMove(int i) {

    }
}
