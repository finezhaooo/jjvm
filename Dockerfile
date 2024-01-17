# 基础镜像
FROM openjdk:8
# 作者
MAINTAINER 2haooo
# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 添加应用
ADD target/jjvm-1.0-SNAPSHOT.jar /jjvm-1.0-SNAPSHOT.jar

## 在镜像运行为容器后执行的命令
ENTRYPOINT ["sh","-c","java -jar /jjvm-1.0-SNAPSHOT.jar"]