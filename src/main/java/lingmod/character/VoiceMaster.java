package lingmod.character;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * 用于管理音效
 */
public class VoiceMaster {
    private volatile static VoiceMaster instance;
    public AbstractCreature owner;
    protected String sound;

    private VoiceMaster() {
        owner = AbstractDungeon.player;
    }

    public static VoiceMaster getInstance() {
        if (instance == null) {
            synchronized (VoiceMaster.class) {
                if (instance == null || instance.owner == null) {
                    instance = new VoiceMaster();
                }
            }
        }
        return instance;
    }

    public void select() {
        if (Math.random() > 0.5F) CardCrawlGame.sound.play("cn_topolect/选中干员1");
        else CardCrawlGame.sound.play("cn_topolect/选中干员2");
    }

    public void beginBattle() {
        if (Math.random() > 0.5F) CardCrawlGame.sound.play("cn_topolect/行动开始");
        else CardCrawlGame.sound.play("cn_topolect/行动出发");
    }

    public void death() {
        CardCrawlGame.sound.play("cn_topolect/行动失败");
    }

    public void victory() {
        CardCrawlGame.sound.play("cn_topolect/3星结束行动");
    }

    public void onAttack() {
        int r4 = 1 + (int) (Math.random() * 4);
        CardCrawlGame.sound.play("cn_topolect/作战中" + r4);
    }
}
