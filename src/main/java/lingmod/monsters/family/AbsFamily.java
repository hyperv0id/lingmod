package lingmod.monsters.family;

import basemod.abstracts.CustomMonster;
import lingmod.actions.FastApplyPower_Action;
import lingmod.powers.FamilyAdjudicationPower;
import lingmod.util.Wiz;

/**
 * 岁一家的抽象类，用于阵营选择
 */
public abstract class AbsFamily extends CustomMonster {

    protected boolean isFirstTurn = true;
    public FamilyRole role = FamilyRole.UNSET;

    public AbsFamily(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
    }

    public AbsFamily(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY, boolean ignoreBlights) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY, ignoreBlights);
    }

    public AbsFamily(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl);
    }

    @Override
    public void useUniversalPreBattleAction() {
        super.useUniversalPreBattleAction();
        addToBot(new FastApplyPower_Action(Wiz.adp(), this, new FamilyAdjudicationPower(Wiz.adp())));
    }

    @Override
    public void takeTurn() {
        if (isFirstTurn) {
            // 改为借助玩家的能力判定势力划分: FamilyAdjudicationPower
            takeFirstTurn();
        } else if (role == FamilyRole.ALLY) {
            takeTurnAlly();
        } else {
            takeTurnEnemy();
        }
        isFirstTurn = false;
    }


    public void transRole(FamilyRole role) {
        this.role = role;
        if (role != FamilyRole.ENEMY) {
            halfDead = true;
            healthBarUpdatedEvent();
        }
    }

    /**
     * 作为玩家的盟友时，每回合的动作
     */
    protected abstract void takeTurnAlly();

    /**
     * 作为玩家的敌人时，每回合的动作
     */
    protected abstract void takeTurnEnemy();

    /**
     * 第一个回合的动作
     */
    protected abstract void takeFirstTurn();


    /**
     * 家庭角色，未设置、盟友、敌人
     */
    public enum FamilyRole {
        UNSET, ALLY, ENEMY,
    }
}
