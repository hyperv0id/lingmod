package lingmod.patch;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import lingmod.ModCore;

/**
 * 此patch用于在程序闪退后自动打开浏览器填写问卷。
 */
@SpirePatch(cls = CoreDumpPatch.CLS_NAME, method = "maybeExit")
public class CoreDumpPatch {
    public static final String CLS_NAME = "com.evacipated.cardcrawl.modthespire.patches.HandleCrash";
    public static final String QA_URL = "https://j5xd30acha.feishu.cn/share/base/form/shrcnDH36Z8MdZWvipiTZwDeqie";

    @SpirePrefixPatch
    public static void Prefix() {
        try {
            if (ReflectionHacks.getPrivateStatic(Class.forName(CLS_NAME), "crash") != null) {
                // open_browser();
            }
        } catch (Exception e) {
            ModCore.logger.info(e.getMessage());
        }
    }

    public static void open_browser() {
        try {
            String url = QA_URL;
            Runtime rt = Runtime.getRuntime();

            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                // macOS
                rt.exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux
                // 优先使用 xdg-open
                String[] cmd = {"xdg-open", url};
                rt.exec(cmd);
            }
            ModCore.logger.error("Attempted to open URL: {}", url);
        } catch (Exception e) {
            ModCore.logger.error("Error opening URL: {}", e.getMessage());
        }
    }
}
