package lingmod.relics;

import lingmod.LingCharacter;

import static lingmod.ModCore.makeID;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, LingCharacter.Enums.LING_COLOR);
    }
}
