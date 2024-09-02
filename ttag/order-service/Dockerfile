# 基础镜像
FROM openjdk:17-buster

WORKDIR /app
# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 拷贝jar包
ADD target/order-service.jar .

EXPOSE 8084
# 入口
ENTRYPOINT ["java", "-Dspring.profiles.active=pro", "--add-opens=java.base/java.lang=ALL-UNNAMED", "--add-opens=java.base/java.lang.invoke=ALL-UNNAMED", "-jar", "/app/order-service.jar"]