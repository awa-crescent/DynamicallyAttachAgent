# Minecraft Server插件：动态加载Agent

在运行中出现警告是正常现象，忽略即可。

本插件无需在启动时指定 -javaagent 和 jdk.attach.allowAttachSelf=true 即可动态加载agent，其底层实现是使用Unsafe修改sun.tools.attach.HotSpotVirtualMachine的ALLOW_ATTACH_SELF字段。

# 用法

在config.yml文件的字符串数组agent-configs中添加成员字符串，格式为

"-javaagent:{agent.jar文件的路径}={传递给agent的参数} [-D{系统属性键}={设置的值}]"

-javaagent指定agent路径和参数，使用空格作为分隔符，后面可以跟可选的任意数量的系统属性设置（System.setProperty），这些属性值以-D开头，也是一些Agent会读取的参数。如果没有可以不设置