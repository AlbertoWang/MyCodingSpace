# Django 初体验
## Django 安装
* 预装了 Anaconda 的环境下使用命令 ```conda install Django==3.0.3``` (最新稳定版本)  
* 未安装 Anaconda 可以使用 ```pip install Django```  

## 项目初始化及运行
1. 生成 Django 项目目录 (Workspace)   
  使用命令 ```django-admin startproject DjangoProjectName``` 在当前目录下生成 Django 框架项目的文件夹 DjangoProjectName  
  在本文档中，创建名为 Django_workspace 的 Django 项目，下文中的 Django_workspace 都是指代例子创建的 Django 项目名，并用来代替前文命令中的 ```DjangoProjectName```   


2. 启动服务器  
  **/Django_workspace** 目录下，使用命令 ```python manage.py runserver localhost:8000```  
  8000 为浏览器访问端口号，可自定义，若命令中不添加 *localhost:8000* 则默认端口号 *8000*。


3. 验证成功  
  浏览器地址输入上文中服务地址 ```localhost:8000```   

## 项目开发
### 生成 Django 应用 (App)  
* Django App 基本介绍  
Django 应用于 Django 项目的关系比较像 JavaEE 中 Service 与整个项目的关系，一个项目里可以包括多个 Service ，Service 可以独立出来给其他项目复用  
* Django App 创建方式  
进入 **Django_workspace** 目录 ，使用命令 ```python manage.py startapp AppName``` 在 **/Django_workspace** 目录中创建新应用 AppName  
在本文档中，创建名为 demo 的 Django App ，下文中的 demo 都是指代例子创建的 Django App 名称，并用来代替前文命令中的 AppName  
Django_workspace 与 demo 的关系相当于存在一个项目中独立的模块应用， demo 服务于 Django_workspace  


### 项目结构  
进入新建的 demo 目录，里面的
* views.py 为视图
* models.py 为模型
* 新建 urls.py 为 URL 映射(相当于 JavaEE 中的 servlet )  
    在 **/Django_workspace/Django_workspace/urls.py** 的 ```urlpatterns``` 中添加一个新元素 ```path('demo/', include('demo.urls')),``` ，即可将上一步 demo 中的 URL 映射添加到整个 Django 项目中  
    以上工作进行完毕后，可按前文重新启动服务，浏览器访问 **http://localhost:8000/demo/** 即可出现 views.py 中的内容  
    *若提示 ```include``` 方法未找到，可添加包 ```from django.urls import include```*  
  
    ***函数 path() 具有四个参数，两个必须参数：route 和 view，两个可选参数：kwargs 和 name***  
    ***route 是一个匹配 URL 的准则 (类似正则表达式)。当 Django 响应一个请求时，它会从 urlpatterns 的第一项开始，按顺序依次匹配列表中的项，直到找到匹配的项。这些准则不会匹配 GET 和 POST 参数或域名。例如，URLconf 在处理请求 localhost:8000/demo 时，它会尝试匹配 demo 。处理请求 localhost:8000/demo/?page=3 时，也只会尝试匹配 demo***  
    
    
* 在 **/Django_workspace/Django_workspace/** 中的 ```settings.py``` 为配置文件  


### 数据库配置  
在 Django 中，数据库中的 Table 直接由 Django 的 manage.py 代为创建。 Django 为使用者提供了较为快速的用户管理、权限控制、日志记录等功能，这些相关的 Table 都是自动生成在数据库内的  

* 准备工作  
    1. 下载相关驱动  
        本文档采用 MySQL 数据库，在命令行输入 ```conda install mysqlclient``` 即可  

    2. 修改 settings.py 中的部分代码  
        ```python
        DATABASES = {
            'default': {
                'ENGINE': 'django.db.backends.mysql', #数据库引擎，此处选用mysql
                'NAME': 'database_name',              #数据库名
                'USER': 'root',                       #数据库用户名
                'PASSWORD': 'password',               #数据库密码
                'HOST':'localhost',                   #数据库地址
                'PORT':'3306',                        #数据库端口号
            }
        }
        ```
        *注 : 如果需要在 Python 文件中添加中文注释，需要在文件开头添加*  
        ```Python 
        # -*- coding: UTF-8 -*-
        ```  


* 数据库中 Table 的创建  
    由于使用了 Django 框架，就不必再以 JavaEE 的传统解决方案 (需求分析 -> 数据库模型建立 -> 相关```create table```语句的 SQL 语言编写/可视化创建 Table ) 在数据库内进行相关 Table 的创建，而是将数据库的 Table 创建过程锁定于 Python 代码中，具体方法如下  
    1. 修改 models.py  
        在 **/Django_workspace/demo/models.py** 中添加需要创建的 Table ，相关代码如下 
        ```python
        class TableName(models.Model):
            col_name_1 = models.CharField(max_length=20)
        ```  
        其中 ```col_name_1``` 为 Table 的列名，```.CharField(max_length=20)``` 为指定 Table 的列数据类型，其参数 ```max_length``` 即为 Table 的字段长度  
        为了下文用例代码的便捷，在本文档中的 **/Django_workspace/demo/models.py** 实际内容如下  
        ```python
            from django.db import models
            class Test(models.Model):
                name = models.CharField(max_length=20)
                gender = models.BooleanField()
        ```
        *```CharField```相当于 SQL 中的 ```varchar``` 类型，除此之外还有 ```IntegerField```、```BigIntegerField```、```DateField```、```DateTimeField```等方法，依次对应到 SQL 中的各个数据类型*  
    2. 修改 settings.py 文件
        在 **/Django_workspace/Django_workspace/settings.py** 中的 ```INSTALLED_APPS``` 内添加应用名 (在本文档的例子中即为 demo ) ，如下  
        ```python
            INSTALLED_APPS = (
                'django.contrib.admin',
                'django.contrib.auth',
                'django.contrib.contenttypes',
                'django.contrib.sessions',
                'django.contrib.messages',
                'django.contrib.staticfiles',
                'AppName',                        # 添加此项
            )
        ```  
        依次在命令行中输入以下命令使数据库进行创建 Table 的操作  
        * ```python manage.py migrate```  
        (此命令会生成一系列位于 **/Django_workspace/Django_workspace/__pycache__** 与 **/Django_workspace/demo/__pycache__** 内的文件，并直接在数据库内生成 Django 默认与用户管理、权限管理等相关的 Table ，相当于先在数据库内生成一些通用的 Table 。若将这些文件删除则需要重新运行此命令)  
        * ```python manage.py makemigrations demo```  
        (此命令会使 demo 中声明的 model 生成一些位于 **/Django_workspace/demo/migrations** 的 Python 文件。若将这些文件删除则需要重新运行此命令)   
        * ```python manage.py migrate demo```  
        (此命令会生成一系列位于 ***/Django_workspace/demo/migrations/__pycache__** 的文件并，直接在数据库内生成 model 中声明需要创建的 Table 以及一个名为 django_migrations 用于记录对 Django_workspace 内 App (例子中为应用名为demo) 的 model 生成记录的 Table。如果需要重新在数据库内建立这些 Table ，需要删除本条命令产生的结果，并重新运行本条与本条命令；如果需要修改 Table 的结构，可以修改 models.py 文件的内容，并重新执行上一条命令与本条命令)  

        ***Django 项目内的文件与数据库并不自动同步，如果你越过了 Django 而直接修改了数据库的相关 Table ，将会导致 Django 内关于数据库创建以及修改的相关缓存文件 (第二条与第三条命令产生的文件) 与数据库不符，从而无法继续使用。因此不要直接修改数据库的 Table 结构，如有需要请通过 Django 修改 models.py 的形式修改数据库的 Table 结构***  


* 数据库中简单的 CRUD  
    1. 在 **/Django_workspace/demo** 目录中新建 db_operate.py 文件，用来编写数据库 CRUD 的代码  
    2. 修改 **/Django_workspace/demo/urls.py** 文件，  
    3. CRUD 基本写法  
        1. 所需头文件  
            ```python
                from django.http import HttpResponse
                from demo.models import Test        # 前面定义好数据库内的 model
            ```
        2. INSERT  
            ```python
                def testdb(request):
                    test = Test(name='qwe',gender=0)
                    test.save()
                    return HttpResponse("<p>Success</p>")
            ```
  

### 前后端交互方法  
这一部分相当于 Java Spring 中的 Thymeleaf (模版引擎) ，同样采用服务器装配好 HTML 后发送给浏览器进行渲染的方式，因此 Django 内的 HTML 同样可以内嵌相关的 Python 语法进行模板化前端编程  


* 准备工作  
    1. 在 **/Django_workspace/demo** 的同级目录下创建目录 **/Django_workspace/templates** 用来保存 HTML 文件  
    2. 在 settings.py 中修改 ```TEMPLATES``` 中的 ```DIRS``` 属性为 ```BASE_DIR+"/templates",``` ，如下  
    ```python
        TEMPLATES = [
            {
                'BACKEND': 'django.template.backends.django.DjangoTemplates',
                'DIRS': [BASE_DIR+"/templates",],   #此处的 templates 即为上一步中创建目录的名字
                'APP_DIRS': True,
                'OPTIONS': {
                    'context_processors': [
                        'django.template.context_processors.debug',
                        'django.template.context_processors.request',
                        'django.contrib.auth.context_processors.auth',
                        'django.contrib.messages.context_processors.messages',
                    ],
                },
            },
        ]
    ```

    3. 在 views.py 中添加需要返回给前端 HTML 的 ```context``` ，详细方法见下节  


* 交互方式  
    1. 在 Python 中，需要返回给 HTML 的数据以字典形式 ```contest={}``` 保存，最终的返回值 ```render(request, 'index.html', context)``` 相当于 Javaweb 中的 ```HttpSession.setAttribute(" HTML 变量名", Java 变量名)```  
    2. 在 HTML 中，由 Django 返回给前端的数据，在 HTML 中以 ```{{变量名}}``` 的形式出现
    3. HTML 中 Django 的模版标签
        1. 选择  
            ```HTML
                {% if condition1 %}
                嵌入 HTML 代码 ...
                {% elif condiiton2 %}
                嵌入 HTML 代码 ...
                {% else %}
                嵌入 HTML 代码 ...
                {% endif %}
            ```
        2. 遍历 (与 JSP 中的 ```c:foreach``` 神似)  
            ```HTML
                <ul>
                {% for item in item_list %}
                    <li>{{ item.name }}</li>
                {% endfor %}
                </ul>
            ```
        3. 对比 (两个 var 可以为模板变量，即 Java 中的对象)  
            ```HTML
                {% ifequal var1 var2 %}
                    <h1>euqal</h1>
                {% else %}
                    <h1>not equal</h1>
                {% endifequal %}
            ```  
        4. 继承 (类似 Java 中的类继承，但作用于 HTML 中)  
            除了被 ```{% block block_name}``` 包围的部分以外，继承全部父 HTML 中的内容， ```{% block block_name}``` 以内的部分需要重写   
            ```HTML
                <!--base.html-->
                <body>
                    <h1>Hello World!</h1>
                      {% block code_segment %}
                         <p>不被继承部分</p>
                      {% endblock %}
                </body>
            ```  
            ```HTML
                <!--extend.html-->
                {% extends "base.html" %}
                {% block code_segment %}
                    <p>自定义新部分</p>
                {% endblock %}
            ```
