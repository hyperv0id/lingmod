package lingmod.util.audio;

import static lingmod.ModCore.makeID;

public enum Voices {
    VOICE_3_STAR_END("3星结束行动"),
    VOICE_COMBAT_1("作战中1"),
    VOICE_COMBAT_2("作战中2"),
    VOICE_COMBAT_3("作战中3"),
    VOICE_COMBAT_4("作战中4"),
    VOICE_ACTION_DEPART("行动出发"),
    VOICE_ACTION_FAIL("行动失败"),
    VOICE_ACTION_START("行动开始"),
    VOICE_OPERATOR_SELECT_1("选中干员1"),
    VOICE_OPERATOR_SELECT_2("选中干员2");

    private final String name;

    Voices(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String ID() {
        return makeID(this.name);
    }
}
