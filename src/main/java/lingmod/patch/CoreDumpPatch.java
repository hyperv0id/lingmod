package lingmod.patch;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import lingmod.util.FeiShuQA;
import lingmod.util.FeiShuQAItem;
import lingmod.util.ModConfig;
import lingmod.util.Wiz;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static lingmod.ModCore.logger;

/**
 * 此patch用于在程序闪退后自动打开浏览器填写问卷。
 */
@SpirePatch(cls = CoreDumpPatch.CLS_NAME, method = "maybeExit")
public class CoreDumpPatch {
    public static final String CLS_NAME =
            "com.evacipated.cardcrawl.modthespire.patches.HandleCrash";

    @SpirePrefixPatch
    public static void Prefix() {
        try {
            Throwable crash = ReflectionHacks.getPrivateStatic(Class.forName(CLS_NAME), "crash");
            if (crash != null && Wiz.isPlayerLing() && ModConfig.browseWhenCrash) {
                String trace = tryTraceStack(crash);
                // 获取异常的堆栈信息
                String errMsg = tryGetErrorMessage(crash);
                final int MAX_LENGTH = 16000 / 2; // 定义最大长度
                if (errMsg.length() > MAX_LENGTH) {
                    errMsg = errMsg.substring(0, MAX_LENGTH); // 裁剪到最大长度
                }
                // web表单链接
                String link = new FeiShuQA()
                        .with(FeiShuQAItem.REPORT_TYPE, "BUG（闪退）")
                        .with(FeiShuQAItem.MOD_ENABLED, FeiShuQA.getModEnabled())
                        .with(FeiShuQAItem.ERR_CAUSE, "自动采集\n" + trace + "\n" + errMsg)
                        .makeLink();
                openBrowser(link);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    private static String tryGetErrorMessage(Throwable crash) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            crash.printStackTrace(pw); // 将堆栈跟踪输出到 PrintWriter
        }

        // 获取堆栈跟踪信息的字符串
        return sw.toString();
    }


    /**
     * This method is used to open the browser with the given URL.
     *
     * @param url The URL to open in the browser.
     */
    public static void openBrowser(String url) {
        try {
            Runtime rt = Runtime.getRuntime();
            String os = System.getProperty("os.name").toLowerCase();
            String[] cmd;

            if (os.contains("win")) {
                // Windows
                cmd = new String[]{"rundll32", "url.dll,FileProtocolHandler", url};
            } else if (os.contains("mac")) {
                // macOS
                cmd = new String[]{"open", url};
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux
                cmd = new String[]{"xdg-open", url};
            } else {
                // 未知操作系统
                logger.error("Unsupported operating system, Cannot open browser");
                return;
            }

            rt.exec(cmd);
            logger.error("Attempted to open URL: {}", url);
        } catch (Exception e) {
            logger.error("Error opening URL: {}", e.getMessage());
        }
    }

    private static String tryTraceStack(Throwable exception) {
        String trace = "";
        try {
            trace = getModsInStacktrace(exception);
        } catch (Exception ignored) {
        }
        return trace;
    }

    /**
     * 打印在异常堆栈中涉及的模块信息。
     *
     * @param exception 要处理的异常
     *                  <p>
     *                  该方法收集异常相关的类，获取其代码源位置，及其对应的模块信息（如果存在）。
     */
    private static String getModsInStacktrace(Throwable exception) {
        StringBuilder output = new StringBuilder();
        Set<String> classes = new HashSet<>();
        addClassesInThrowable(exception, classes);
        Set<URL> urls = new HashSet<>();

        for (String className : classes) {
            try {
                Class<?> cls = Class.forName(className);
                URL url = cls.getProtectionDomain().getCodeSource().getLocation();
                if (url != null) {
                    urls.add(url);
                }
            } catch (NoClassDefFoundError | ClassNotFoundException var6) {
                output.append(String.format("Failed to get mod info for class: %s", className)).append(System.lineSeparator());
                // logger.error("Failed to get mod info for class: {}", className, var6); // 已移除
            }
        }

        Set<String> modInfoLines = new HashSet<>();

        for (URL url : urls) {
            if (url != null) {
                Arrays.stream(Loader.MODINFOS).filter(
                                (m) -> url.equals(m.jarURL)).findFirst()
                        .ifPresent((modInfo) -> {
                            if (!modInfo.ID.equals("basemod")) {
                                modInfoLines.add(String.format("%s (%s)", modInfo.ID, modInfo.ModVersion));
                            }
                        });
            }
        }

        if (!modInfoLines.isEmpty()) {
            output.append("Mods in stacktrace:").append(System.lineSeparator());

            for (String modId : modInfoLines.stream().sorted().collect(Collectors.toList())) {
                output.append(" - ").append(modId).append(System.lineSeparator());
            }
        }

        return output.toString(); // 返回构造的字符串
    }


    /**
     * 将异常及其被抑制的异常和原因中的类名添加到给定的集合中。
     *
     * @param exception 要处理的异常
     * @param classes   用于存储类名的集合
     *                  <p>
     *                  该方法递归处理异常及其所有子异常，将类名添加到集合中。
     */
    private static void addClassesInThrowable(Throwable exception, Set<String> classes) {
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            classes.add(stackTraceElement.getClassName());
        }

        for (Throwable suppressed : exception.getSuppressed()) {
            if (suppressed != null) {
                addClassesInThrowable(suppressed, classes);
            }
        }

        Throwable cause = exception.getCause();
        if (cause != null) {
            addClassesInThrowable(cause, classes);
        }
    }


}
