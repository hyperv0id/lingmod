package lingmod.relics;

import lingmod.CharacterFile;

import static lingmod.ModFile.makeID;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, CharacterFile.Enums.LING_COLOR);
    }
}
