package lingmod.relics;

import lingmod.character.Ling;

import static lingmod.ModCore.makeID;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.DEPRECATED, LandingSound.FLAT, Ling.Enums.LING_COLOR);
    }
}
