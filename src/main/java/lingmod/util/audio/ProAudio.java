package lingmod.util.audio;

import java.util.ArrayList;
import java.util.List;

import static lingmod.ModCore.makeID;

public class ProAudio {
    public Dialect dialect; // which dialect
    public String audioName;

    public ProAudio(Dialect d, Voices v) {
        this.dialect = d;
        this.audioName = v.toString();
    }

    public static List<ProAudio> getAllVoices() {
        List<ProAudio> res = new ArrayList<>();
        for (Dialect d : Dialect.values()) {
            for (Voices v : Voices.values()) {
                ProAudio p = new ProAudio(d, v);
                res.add(p);
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return dialect.name().toLowerCase() + "/" + audioName + ".ogg";
    }

    public String key() {
        return makeID(toString());
    }
}