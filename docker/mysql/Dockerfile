##
## docker run -idt --name mud-mysql -h mudmysqlhost -e MYSQL_ROOT_PASSWORD=123456 --net docker_eureka-net --expose 3306 -p 3308:3306 mud/mysql:mud
##
FROM oraclelinux:7-slim
ENV PACKAGE_URL https://repo.mysql.com/yum/mysql-5.7-community/docker/x86_64/mysql-community-server-minimal-5.7.18-1.el7.x86_64.rpm

# Install server
RUN rpmkeys --import http://repo.mysql.com/RPM-GPG-KEY-mysql \
  && yum install -y $PACKAGE_URL \
  && yum install -y libpwquality \
  && rm -rf /var/cache/yum/*
RUN mkdir /docker-entrypoint-initdb.d

VOLUME /var/lib/mysql

COPY docker-entrypoint.sh /entrypoint.sh
# 指定建表初始化脚本
COPY mud.sql /docker-entrypoint-initdb.d/init.sql
# 执行权限，不处理报错
RUN chmod +x entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]

EXPOSE 3306 33060
CMD ["mysqld"]