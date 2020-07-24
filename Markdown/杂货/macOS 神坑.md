# macOS 神坑
## PCL 的编译与链接
1. 使用 Homebrew 安装了 CMake 与 PCL 后，直接运行如下命令对 PCL 的示例代码进行 CMake 会报错

```sh
mkdir build
cd build
cmake ..
```

报错
```
-- Could NOT find PkgConfig (missing: PKG_CONFIG_EXECUTABLE) 
CMake Error at /usr/local/share/pcl-1.9/PCLConfig.cmake:58 (message):
  simulation is required but glew was not found
Call Stack (most recent call first):
  /usr/local/share/pcl-1.9/PCLConfig.cmake:361 (pcl_report_not_found)
  /usr/local/share/pcl-1.9/PCLConfig.cmake:545 (find_external_library)
  CMakeLists.txt:5 (find_package)
```

* 原因：glew 相关外部库找不到
* 解决（*非靠谱方式*）：如果没有使用这部分代码带话，可以在 PCL 的 CMake 配置文件 ```/usr/local/Cellar/pcl/1.9.1_9/share/pcl-1.9/PCLConfig.cmake``` 中去掉这部分外部库的 find_package 部分，将 ```PCLConfig.cmake``` 中带有以下部分的代码注释掉

```
# macro(find_glew)
#   find_package(GLEW)
# endmacro(find_glew)

#  elseif("${_lib}" STREQUAL "glew")
#    find_glew()

# set(pcl_simulation_ext_dep opengl glew )
```

2. 成功进行 CMake 后，使用 make 命令进行编译链接，报错如下

```sh
In file included from /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/../include/c++/v1/complex:245:
/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/../include/c++/v1/cmath:317:9: error: 
      no member named 'signbit' in the global namespace
using ::signbit;

<此处忽略 n 个 no member named xxx in the xxx namespace 的错误>

fatal error: too many errors emitted, stopping now [-ferror-limit=]
20 errors generated.
make[2]: *** [CMakeFiles/pcd_write.dir/pcd_write.cpp.o] Error 1
make[1]: *** [CMakeFiles/pcd_write.dir/all] Error 2
make: *** [all] Error 2
```

* 原因：似乎是 macOS 下的 Xcode 使用的 clang 与命令行中使用的 clang 命令不一致导致的问题（参照 gayhub 上的[这个 issue](https://github.com/PointCloudLibrary/pcl/issues/2601#issuecomment-621889211)）

* 解决：修改 Xcode 使用的 SDK 为命令行同款，如下

```sh
#Check the current sdk
xcrun --show-sdk-path

#Change sdk
sudo xcode-select -s /Library/Developer/CommandLineTools          #Using CommandLineTools SDK
sudo xcode-select -s /Applications/Xcode.app/Contents/Developer   #Using XCode.app SDK(这是把 Xcode 的 SDK 搞回去，解决此问题过程中不需要这个命令)
```