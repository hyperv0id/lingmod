package lingmod.monsters;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.CustomMonster;
import lingmod.util.MonsterHelper;
import lingmod.util.Wiz;

public abstract class AbsSummonMonster extends CustomMonster {

    public AbsSummonMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h,
            String imgUrl) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl);
        // isPlayer = true;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        showHealthBar();
        createIntent();
        MonsterHelper.MoveMonster(this, AbstractDungeon.player.drawX + 200.0F * Settings.scale,
                AbstractDungeon.player.drawY + 100.0F * Settings.scale);
    }

    @Override
    protected void getMove(int arg0) {
        setMove((byte) 0, Intent.ATTACK, damage.get(0).base);
    }

    @Override
    public void die() {
        super.die();
        die_summon();
    }

    public void die_summon() {
        Wiz.atb(new GainEnergyAction(1));
    }
}
