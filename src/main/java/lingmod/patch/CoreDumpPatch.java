package lingmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.*;
import javassist.CtBehavior;
import lingmod.ModCore;

/**
 * 此patch用于在程序闪退后自动打开浏览器填写问卷。
 */
@SpirePatch(cls = CoreDumpPatch.CLS_NAME, method = "maybeExit")
public class CoreDumpPatch {
    public static final String CLS_NAME = "com.evacipated.cardcrawl.modthespire.patches.HandleCrash";
    public static final String QA_URL = "https://j5xd30acha.feishu.cn/share/base/form/shrcnDH36Z8MdZWvipiTZwDeqie";

    @SpireInsertPatch(locator = Locator.class)
    public static void Insert() {
        ModCore.logger.info("Normal Exit, No OPEN BROWSER");
        open_browser();
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

    public static class Locator extends SpireInsertLocator {
        public Locator() {
        }

        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher m = new Matcher.MethodCallMatcher(com.evacipated.cardcrawl.modthespire.Loader.class, "printMTSInfo");
            return LineFinder.findInOrder(ctBehavior, m);
        }
    }
}
