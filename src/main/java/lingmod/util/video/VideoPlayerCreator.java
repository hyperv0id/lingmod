//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lingmod.util.video;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class VideoPlayerCreator {
    private static Class<? extends VideoPlayer> videoPlayerClass;

    public VideoPlayerCreator() {
    }

    public static VideoPlayer createVideoPlayer() {
        initialize();
        if (videoPlayerClass == null) {
            return new VideoPlayerStub();
        } else {
            try {
                return (VideoPlayer) ClassReflection.newInstance(videoPlayerClass);
            } catch (ReflectionException var1) {
                var1.printStackTrace();
                return null;
            }
        }
    }

    private static void initialize() {
        if (videoPlayerClass == null) {
            String className = null;
            Application.ApplicationType type = Gdx.app.getType();
            if (type == ApplicationType.Android) {
                if (Gdx.app.getVersion() >= 12) {
                    className = "com.badlogic.gdx.video.VideoPlayerAndroid";
                } else {
                    Gdx.app.log("Gdx-Video", "VideoPlayer can't be used on android < API level 12");
                }
            } else if (type == ApplicationType.iOS) {
                className = "com.badlogic.gdx.video.VideoPlayerIos";
            } else if (type == ApplicationType.Desktop) {
                className = "com.badlogic.gdx.video.VideoPlayerDesktop";
            } else if (type == ApplicationType.WebGL) {
                className = "com.badlogic.gdx.video.VideoPlayerGwt";
            } else {
                Gdx.app.log("Gdx-Video", "Platform is not supported by the Gdx Video Extension");
            }

            try {
                videoPlayerClass = ClassReflection.forName(className);
            } catch (ReflectionException var3) {
                var3.printStackTrace();
            }

        }
    }
}
