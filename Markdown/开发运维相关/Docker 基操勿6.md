# Docker
## Docker 基本命令
### ```docker run``` 命令
#### 相关参数
* ```-d``` 后台运行（detach）
* ```-t``` 分配伪终端（pseudo-TTY）
* ```-p <宿主机端口号>:<容器端口号>``` 端口映射（port）
* ```--name <ContainerName>``` 容器命名
### ```docker exec``` 命令
#### 相关参数
```docker exec -it <ContainerName 或 ContainerID> bash``` 进入容器伪终端
### ```docker logs``` 命令
#### 相关参数
```docker logs -f <ContainerName 或 ContainerID>``` 容器日志输出
## Dockerfile 共享镜像
### Dockerfile 基本内容
```txt
FROM <ImageName 或 ImageID>
ADD <需要添加到镜像的文件相对路径> <在 Image 里的路径>
RUN <需要运行的 shell 命令>
CMD ["<Image 内默认启动的进程，一般是 /bin/bash >"]
### 构建镜像
```docker build [选项] <Dockerfile路径>``` 的可选选项
* ```-t <DockerHub 账号>/<ImageName>:<版本号（tag）>
```
