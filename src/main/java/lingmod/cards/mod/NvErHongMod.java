package lingmod.cards.mod;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NvErHongMod extends AbsLingCardModifier {
    private final int dmg;

    public NvErHongMod(int dmg) {
        this.dmg = dmg;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + dmg;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new NvErHongMod(this.dmg);
    }

}
