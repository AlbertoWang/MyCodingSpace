# docker 下的 MongoDB 使用
## 安装与初始配置
### 配置前须知
1. MongoDB 与 MySQL 不同，MongoDB 的机制是*用户是与数据库绑定的，创建用户时必须制定用户所在的数据库*。因此管理用户的 root 用户需要绑定到用户管理的 ```admin``` 数据库中。
2. 不同数据库下可以存在相同用户名的用户。即在 ```admin``` 数据库与 ```sample``` 数据库中都可以存在一个叫做 root 的用户。
3. 在 MongoDB 的每次选择数据库时，使用 ```use <DBName>``` 命令后，使用 ```db.auth('<UserName>', '<Password>')``` 命令进行权限验证。
4. 在使用数据库内的 ```Table```（MongoDB 将其称为 ```Collection```）时，使用 ```db.<CollectionName>```前缀修饰查询语句。
### 具体流程
1. 启动 docker 服务后运行以下命令，拉取 MongoDB 镜像

    ```sh
    docker pull mongo
    ```

2. 启动 docker 镜像，部署运行容器，注意端口号映射

    ```sh
    docker run --name mongodb -p 27017:27017 -d mongo --auth
    ```

    如果是已经部署的容器，则运行以下命令来运行容器

    ```sh
    docker exec -it <ContainerID> mongo <DBName>
    ```

3. 查看容器是否成功部署

    ```sh
    docker ps -a
    ```

4. 进入 MongoDB 的命令行

    ```sh
    docker exec -it <ContainerId> mongo admin
    ```

5. 添加管理员用户

    ```
    db.createUser({
        user: 'root', 
        pwd: '<YourPassword>', 
        roles: [{ 
            role: "userAdminAnyDatabase", 
            db: "admin" 
        }] 
    });
    ```

6. 验证是否成功

    ```
    db.auth('root', '<YourPassword>');
    ```

7. 相关错误提示解决
   * ```too many users are authenticated``` : 有可能是在选择了一个数据库并验证了用户后再次选择了其他数据库造成的，此时需要先从 MongoDB 中 ```exit``` 并重新运行容器启动命令
   * ```not authorized on record to execute command``` : 权限不足，确定用户权限并重新进行 ```db.auth()``` 命令

## MongoDB 基本语法

1. 数据库相关操作
    * 查看已有数据库: ```show dbs```
    * 选择数据库: ```use <DBName>```
    * 查看 MongoDB 中全部用户 : ```db.system.users.find().pretty()```
2. 插入数据(INSERT): ```db.<CollectionName>.insert({ <JSON details> })```

    ```nosql
    db.sample_record.insert({
        "record_id": 1,
        "sample_type": "水质",
        "sample_details": {
            "H2O": 0.5,
            "NO": 1.3,
            "CO": 3
        }
    })
    ```

3. 更新数据(UPDATE): ```db.<CollectionName>.update({<QueryCondition>}, {$set: {NewDocument}})```
   
   更新用户: ```db.updateUser('<UserName>', {roles: [{role: '<RoleType>', db: '<DBName>'}, ...], pwd: '<Password>'})```

    ```nosql

    ```

4. 删除数据(DELETE): ```db.<CollectionName>.remove({<QueryCondition>})```

    删除用户: ```db.dropUser('<UserName>')```
   
   ```nosql

   ```

5. 查询数据(SELECT): ```db.<CollectionName>.find({<QueryCondition>})```

    ```nosql
    db.sample_record.find({sample_type: '水质'})
    ```

    查询指定列（域）: ```db.<CollectionName>.find({<QueryCondition>}: {<ColumnName>: 1})```，其中 ```<ColumnName>``` 的 value ≥ 1则为显示该列，为0则不显示

   * 相关逻辑表达式
       * ```$lt```: less than
       * ```$lte```: less than / equal
       * ```$gt```: greater than
       * ```$gte```: greater than / equal
       * ```$ne```: not equal
       * ```$in```, ```$nin```: in, not in. *后接 list*
       * ```$or```: 多字段查询，后接 list
       * ```$not```: 后接查询条件
       * ```$exists```: 后接 ```true``` 或 ```false```
    * 与 SQL 相对应
       * ```limit n```: .limit(n)
       * ```count(*)```: .count()
    * 创建索引
       * ```ensureIndex()```

## 可视化界面
可视化界面可采用 Studio 3T，不过这玩意要钱，可以自行百度破解方式。

*macOS版破解方式[在这](https://www.jianshu.com/p/038fe91affa2)*


1. 与 Navicat 类似，进入后新建 Connection。同样需要 host、port
2. 在 ```Authentication``` 选项卡中选择 ```Basic(SCRAM-SHA-256)``` 并填写在前面 MongoDB 中新建的用户名与密码，```Authentication DB``` 使用默认的 admin 即可
3. 测试连接成功后，进入界面与 Navicat 类似，不再赘述
   
*Studio 3T 支持使用 SQL 语法进行 NOSQL 查询*