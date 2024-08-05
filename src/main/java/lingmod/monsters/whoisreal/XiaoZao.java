package lingmod.monsters.whoisreal;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import lingmod.ModCore.ResourceType;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

public class XiaoZao extends CustomMonster {
    public static final String ID = makeID(XiaoZao.class.getSimpleName());
    public static final String spinePath = makeImagePath("who_is_real/xiao_zao/enemy_1122_sphond", ResourceType.MONSTERS);
    public static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);

    public XiaoZao(float x, float y) {
        super(monsterStrings.NAME, ID, 24, -4.0F, 12.0F, 130.0F, 194.0F, (String) null, x, y);

        loadAnimation(spinePath + ".atlas", spinePath + ".json", 2F);
        skeleton.setFlipY(true);
        state.setAnimation(0, "Attack", false);
        AnimationState.TrackEntry e = state.addAnimation(0, "Idle", true, 0F);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public XiaoZao() {
        this(-160F, 12F);
    }

    @Override
    public void takeTurn() {

    }

    @Override
    protected void getMove(int i) {

    }
}
