package lingmod.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import lingmod.ModCore;

/**
 * 挑山人大战掌柜中，玩家无法造成伤害
 */
public class InvincibleForPlayer extends InvinciblePower {
    public static final String ID = ModCore.makeID(InvincibleForPlayer.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public InvincibleForPlayer(AbstractCreature owner) {
        super(owner, 0);
        this.name = powerStrings.NAME;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        // 无来源 或者是 特殊类型
        if (info.type != DamageInfo.DamageType.NORMAL || info.owner == null) {
            return damageAmount;
        }

        // 来自玩家
        if (info.owner instanceof AbstractPlayer || info.owner.isPlayer) {
            damageAmount = 0;
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }
}
