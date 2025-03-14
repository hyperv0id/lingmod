#!/bin/bash

JAR_NAME="LingMod.jar"
JAVA_HOME=$(which java)

set -e

# 定义函数来设置不同操作系统的游戏根目录和相关路径
set_sts_paths() {
    case "$(uname -s)" in
        Linux*)
            echo "设置Linux路径"
            SteamPath="${HOME}/.steam/steam/steamapps/"
            STSPath="${SteamPath}common/SlayTheSpire/"
            JAVA_HOME="${STSPath}jre/bin/java"
            ;;
        Darwin*)
            echo "设置macOS路径"
            SteamPath="${HOME}/Library/Application Support/Steam/steamapps/"
            STSPath="${SteamPath}common/SlayTheSpire/"
            JAVA_HOME="${STSPath}jre/Contents/Home/bin/java"  # macOS Java 路径更正
            ;;
        CYGWIN*|MINGW*|MSYS*)
            echo "设置Windows路径"
            SteamPath="D:/apps/Steam/steamapps/"  # 可以根据实际情况修改
            STSPath="${SteamPath}common/SlayTheSpire/"
            JAVA_HOME="${STSPath}jre/bin/java.exe"
            ;;
        *)
            echo "未知操作系统"
            ;;
    esac
    Uploader="${STSPath}mod-uploader.jar"

    echo "Steam路径: $SteamPath"
    echo "SlayTheSpire路径: $STSPath"
    if [ -z "${JAVA_HOME}" ] || [ ! -x "${JAVA_HOME}" ]; then
        echo "未找到指定的Java路径，使用系统默认Java."
        JAVA_HOME=$(which java)
        if [ -z "${JAVA_HOME}" ]; then
          echo "系统默认Java也未找到，请确保Java已安装。"
          return 1
        fi
    else
        echo "Java路径: $JAVA_HOME"
    fi
    echo "Uploader Path: $Uploader"
}

set_sts_paths

mkdir -p ./upload/content
mvn package
cp "./target/$JAR_NAME" "./upload/content"

# 需要启动Steam
"$JAVA_HOME" -jar "$Uploader" upload -w ./upload