package lingmod.potions;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;

import basemod.AutoAdd.Ignore;
import lingmod.ModCore;
import lingmod.character.Ling;
import lingmod.events.DoujinshiPlot;

@Ignore
public class ForgetPotion extends AbstractEasyPotion {
    public static String ID = makeID(ForgetPotion.class.getSimpleName());

    public ForgetPotion() {
        super(ID, PotionRarity.COMMON, PotionSize.ANVIL, new Color(0.4f, 0.2f, 0.5f, 1f),
                new Color(0.2f, 0.2f, 0.4f, 0.5f), null, Ling.Enums.PLAYER_LING, ModCore.characterColor);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    public int getPotency(int ascensionlevel) {
        return 0;
    }

    /**
     * 在事件中改变敌人的对话
     */
    @Override
    public void use(AbstractCreature arg0) {
        if (DoujinshiPlot.__inst == null) return;
        DoujinshiPlot.__inst.transitionKey(DoujinshiPlot.Phases.DOUJINSHI);
        DoujinshiPlot.__inst.imageEventText.loadImage(makeImagePath("events/DoujinshiPlot_1.png"));
    }

    public String getDescription() {
        return strings.DESCRIPTIONS[0];
    }
}
