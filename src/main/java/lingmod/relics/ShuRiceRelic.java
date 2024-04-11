package lingmod.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static lingmod.ModCore.makeID;

public class ShuRiceRelic extends AbstractEasyRelic {

    public static final String ID = makeID("ShuRiceRelic");

    public static final float healAmount = 0.5f;

    public ShuRiceRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        AbstractPlayer player = AbstractDungeon.player;
        int hp = player.currentHealth;
        int maxHP = player.maxHealth;
        int heal = (int) (maxHP * healAmount);
        hp += heal;
        if (hp > maxHP) {
            int diff = (hp - maxHP);
            hp = maxHP - diff;
            player.maxHealth += diff;
        }
        player.currentHealth = hp;
    }
}
