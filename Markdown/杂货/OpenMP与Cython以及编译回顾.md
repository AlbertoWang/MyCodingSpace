# macOS 下的 OpenMP 以及 Python 调用编译 C/C++ 的插件: Cython
## 一些需要知道的定义
* [Clang](http://clang.llvm.org/get_started.html): 在 macOS 上，苹果为了优化编译 (降低内存消耗)，提供了一种编译器前端: **Clang**，其后端是底层虚拟机（**[LLVM](http://llvm.org/)**，它最早以C/C++为实现对象，而目前它已支持包括ActionScript、Ada、D语言、Fortran、GLSL、Haskell、Java字节码、Objective-C、Swift、Python、Ruby、Crystal、Rust、Scala以及C#等语言）
* [OpenMP](https://clang-omp.github.io/): (线程级)并行计算用的 C/C++ 库
* [Cython](https://cython.org/): 一个让 Python 可以调用 C/C++ 代码的玩意儿，常用来给 Python 加速，需要用到 **.pyx** 文件作为媒介（这玩意有点 makefile 内味儿）

## 安装与环境变量配置
1. 安装
    在 macOS 环境下，可以采用 HomeBrew 先安装 LLVM (Clang支持):
    ```sh
    brew install llvm
    ```
    再安装 OpenMP:
    ```sh
    brew install libomp
    ```
    此时如果需要编译，需要添加 ```-I``` 和 ```-L``` 参数分别绑定 **include** 和 **library**，如下:
    ```sh
    clang filename.c -fopenmp -I<此处替换为 libomp 安装路径(不含<>)/include> -L<此处替换为 libomp 安装路径(不含<>)/lib>
    ```
2. 为了解决上面繁琐的参数，直接把需要的 include 和 library 添加到环境变量(我这里用的是zsh，命令行直接 ```vim .zshrc``` 进入修改)
    参照官网推荐配置，如果使用 HomeBrew 安装的 LLVM 与 OpenMP 的话，安装路径应该与下面类似
    ```sh
    # LLVM
    export PATH=/usr/local/opt/llvm/bin:$PATH
    # OpenMP
    export C_INCLUDE_PATH=/usr/local/Cellar/libomp/10.0.0/include:$C_INCLUDE_PATH
    export CPLUS_INCLUDE_PATH=/usr/local/Cellar/libomp/10.0.0/include:$CPLUS_INCLUDE_PATH
    export LIBRARY_PATH=/usr/local/Cellar/libomp/10.0.0/lib:$LIBRARY_PATH
    # macOS 中的环境变量为 DYLD_LIBRARY_PATH, 其他 unix 系统中为 LD_LIBRARY_PATH
    export DYLD_LIBRARY_PATH=/usr/local/Cellar/libomp/10.0.0/lib:$DYLD_LIBRARY_PATH
    ```
## OpenMP 简单使用
参照[这里](https://www.ibm.com/developerworks/cn/aix/library/au-aix-openmp-framework/index.html)

## 编译
使用 OpenMP 进行并行化计算需要使用参数 ```-fopenmp```，即编译时的命令为:
```sh
clang filename.c -fopenmp
```
或者使用 **gcc**:
```sh
gcc filename.c -fopenmp
```
或者使用 **g++** *（需要 ```-lstdc++```，不然会报 std 找不到的错）*:
```sh
g++ filename.cpp -fopenmp -lstdc++
```
