# 认识Git

## 版本控制

版本控制是一种记录文件内容变化，方便以后查阅特定版本修改情况的系统

![image-20201010095923386](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010095923386.png)

## 版本控制的发展史

**本地版本控制系统**

采用简单的数据库记录文件的历次更新差异

![image-20201010100010522](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010100010522.png)

在硬盘上保存文件修改前后的变化

怎样让不同系统上的开发者协同工作？

**集中化的版本控制系统**

![image-20201010100121075](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010100121075.png)

有一个单一的集中管理的服务器，保存所有文件的修订版本。

而协同工作的人们都通过客户端连到这台服务器。

取出最新的文件或者提交更新

![image-20201010100238362](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010100238362.png)

优点

- 可以看到项目中的其他人在做什么
- 可以掌控每个开发者的权限
- 管理一个cvcs比在各客户端维护本地数据库容易

缺点

- 中央服务器的单点故障
- 磁盘发生损坏-可能丢失所有的数据



**分布式版本控制系统**

![image-20201010100515635](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010100515635.png)

客户端每次的克隆操作，都是对代码仓库的完整备份，不只是提取最新版本的文件快照，任何一处协同工作用的服务器发生故障，事后都可以用任何一个镜像的本地仓库恢复。

- 可以指定和若干不同的远端代码仓库进行交流

- 可以在同一个项目中，分别和不同工作小组的人相互协作

- 可以根据需要设定不同的协同流程

  如层次模型式的工作流，这在集中式系统中无法实现

## Git起源

Git设计目标

- 速度
- 简单的设计
- 对非线性开发模式的支持-需要上万个并行开发分支
- 完全分布式
- 能够高效管理超大规模项目

Git速度飞快，适合管理大项目，有难以置信的非线性分支管理系统

## Git工作原理

- 直接记录快照而非差异比较

  - CVS、SVN等大部分系统以文件变更列表的方式存储信息

    ![image-20201010101119223](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010101119223.png)

    将保存的信息看作是一组基本文件和每个文件随时间逐步累积的差异

  - Git把数据看做对小型文件系统的一组快照

    - 文件发生变化，对全部文件制作一个快照并保存这个快照的索引
    - 文件没有修改，保留一个链接指向之前存储的文件

    ![image-20201010101354492](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010101354492.png)

- 几乎所有操作都是本地执行

  - 大多数操作都只需要访问本地文件和资源

  - 在本地磁盘上就有项目的完整历史，操作块

    浏览项目的历史，Git不需外连到服务器去获取历史

  - 没有网络也能进行提交，有网络再上传。SVN、CVS就不能

- 保证完整性

  git中所有数据在存储前都计算校验和，然后以校验和来引用，不可能在git不知情时更改任何内容或目录内容

  Git用SHA-1散列方式计算校验和

  ![image-20201010101852410](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010101852410.png)

  Git数据库中保存的信息都是以文件内容的哈希值来索引

  校验和用在远距离通信中保证数据的完整性和准确性

- 一般只添加数据

  Git操作几乎只向Git数据库增加数据，很难执行不可逆操作

  未提交的更新有可能丢失或弄乱修改内容

  一旦提交快照到Git中，就难再丢失，如果还推送到其他仓库则更加安全了

- Git的三种状态

  - 已提交（committed）

    数据已经安全地保存在本地数据库中

  - 已修改（modified）

    修改了文件，但还没保存到数据库中

  - 已暂存（staged）

    对一个已修改文件的当前版本做了标记，使之包含在下次提交的快照中

## Git的工作流程

Git项目的三个工作区域

- Git仓库

  保存项目的元数据和对象数据库的地方，远程克隆仓库就是这里的数据

- 工作目录

  对项目的某个版本从仓库独立提取出来的内容，放在磁盘上供你使用或修改

- 暂存区域

  暂存区域是一个文件，保存了下次将提交的文件列表信息，一般在git仓库目录中



Git基本工作流程

- 在工作目录中修改文件
- 暂存文件，将文件的快照放入暂存区域
- 提交更新，找到暂存区域的文件，将快照永久性存储到Git仓库目录



## Git和SVN的区别

![image-20201010104256942](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010104256942.png)

# Git的基本使用

## 初始化Git

通过git config工具，定制自己的git环境，每台计算机只需要配置一次。配置有三个维度：全局、系统、用户

**定制信息**

- 用户信息
- 文本编辑器
- 检查配置信息

![image-20201010104432498](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010104432498.png)

## 你的第一个Git仓库

**获取Git仓库的两种方式**

通过命令创建全新的Git仓库

![image-20201010104535702](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010104535702.png)

克隆现有仓库

![image-20201010104550713](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010104550713.png)

Git克隆的是该Git仓库服务器上的几乎所有的数据，而不是仅仅复制完成你的工作所需要的文件

## Git功能

**提交更新**

工作目录下的每个文件两种状态：已跟踪或未跟踪

![image-20201010104723089](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010104723089.png)

在目录新建一个文件，未跟踪

git add操作后已跟踪，放入暂缓区

git commit建立快照放入存储区

继续修改，变成未修改状态

![image-20201010104839969](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010104839969.png)

查询提交历史

撤销更新

打标签



## 使用Git

### 基本配置

git自带一个git config的工具来帮助设置控制Git外观和行为的配置变量

这些变量存储在三个不同的位置：

- /etc/gitconfig文件：包含系统上每一个用户及他们仓库的通用配置。如果使用带有--system选项的git config时，它会从此文件读写配置变量
- ~/.gitconfig或~/.config/git/config文件：只针对当前用户。可以传递--global选项让Git读写此文件
- 当前使用仓库的Git目录中的config文件（就是.git/config)：针对该仓库

```shell
git config
```

windows系统中，Git会查找$HOME目录下（一般情况下是C:\users\$USER）的.gitconfig文件。Git同样也会寻找/etc/gitconfig文件，但只限于MSys的根目录下，即安装Git时所选的目标位置。

## 基本操作

### 新建仓库

创建一个新的目录，进入该目录下执行下面命令

```
git init
```

### 添加新的文件到暂缓区

在目录下创建a.txt文件

```shell
git add *.txt
```

此时文件进入暂存区，在下次提交和时候会将暂存区的文件提交到存储区

```shell
[root@single-k8s gitdemo]# git status
# 位于分支 master
#
# 初始提交
#
# 要提交的变更：
#   （使用 "git rm --cached <file>..." 撤出暂存区）
#
#	新文件：    a.txt
#
```

### 提交文件到存储区

此时文件被Git仓库进行了完整的存储

```shell
[root@single-k8s gitdemo]# git commit -m "第一次提交"
[master（根提交） c8672db] 第一次提交
 1 file changed, 1 insertion(+)
 create mode 100644 a.txt
```

查看状态

```shell
[root@single-k8s gitdemo]# git status -s
```

```shell
[root@single-k8s gitdemo]# git status
# 位于分支 master
无文件要提交，干净的工作区
```

### 忽略文件

有些文件无需纳入Git的管理，也不希望它们总出现在未跟踪文件列表。通常都是些自动生成的文件，比如日志文件，或者编译过程中创建的临时文件等。

创建一个名为.gitignore的文件，列出要忽略的文件的模式。

```shell
[root@single-k8s gitdemo]# cat .gitignore 
*.[oa]
*~
```

第一行告诉Git忽略所有以.o或.a结尾的文件。一般这类对象文件和存档文件都是编译过程中出现的。

第二行告诉Git忽略所有以~结尾的文件，许多文件编译软件都用这样的文件名保存副本

```shell
[root@single-k8s gitdemo]# touch aa.o
[root@single-k8s gitdemo]# git status
# 头指针分离于 v0.1
# 要提交的变更：
#   （使用 "git reset HEAD <file>..." 撤出暂存区）
#
#	新文件：    CONTRIBUTING.md
#	新文件：    b.txt
#	新文件：    c.txt
#
# 未跟踪的文件:
#   （使用 "git add <file>..." 以包含要提交的内容）
#
#	.gitignore
```

可能换需要忽略log，tmp或者pid目录，以及自动生成的文档等等，要养成一开始就设置好.gitignore文件的习惯，以免将来误提交这类无用的文件。

文件.gitignore的格式规范如下：

- 所有空行或者以#开头的行都被Git忽略
- 可以使用标准的glob模式匹配
- 匹配模式可以以(/)开头防止递归
- 匹配模式可以以(/)结尾指定目录
- 要忽略指定模式以外的文件或目录，可以在模式前加上惊叹号（！）取反

https://github.com/github/gitignore

### 列出修改区别

git status命令显示的状态太模糊，想知道具体修改了什么地方要用git diff命令

```shell
[root@single-k8s gitdemo]# git diff
diff --git a/a.txt b/a.txt
index ccc3e7b..be4e668 100644
--- a/a.txt
+++ b/a.txt
@@ -1 +1,2 @@
 aaaaa
```

查看已暂存的将要添加到下次提交里的内容，

```shell
[root@single-k8s gitdemo]# git diff --staged
diff --git a/a.txt b/a.txt
index ccc3e7b..be4e668 100644
--- a/a.txt
+++ b/a.txt
@@ -1 +1,2 @@
 aaaaa
+bbbbb
diff --git a/b.txt b/b.txt
new file mode 100644
index 0000000..9f47804
--- /dev/null
+++ b/b.txt
@@ -0,0 +1 @@
+bbbbbb
```

### 跳过暂存区存储

尽管使用暂存区域的方式可以精心准备要提交的细节，但有时候这么做略显繁琐。Git提供了一个跳过使用暂存区域的方式，只要在提交的时候，给git commit 加上-a选项，Git就会自动把所有已经跟踪过的文件暂存起来一并提交，从而跳过git add步骤。

```shell
[root@single-k8s gitdemo]# git commit -a -m "跳过暂存区的提交"
[master 67445ce] 跳过暂存区的提交
 2 files changed, 2 insertions(+)
 create mode 100644 b.txt
```

### 移除文件

从Git中移除某个文件，必须要从已跟踪文件清单（暂存区）中移除，然后提交

```shell
[root@single-k8s gitdemo]# git rm b.txt
rm 'b.txt'
```

从存储区移除文件

你想让文件保留在磁盘，但是并不想让Git继续跟踪。当你忘记添加.gitignore文件，不小心把一个很大的日志文件或一堆.a这样的编译生成文件添加到暂存区时，这一做法尤其有用。

```shell
[root@single-k8s gitdemo]# git rm --cached b.txt
rm 'b.txt'
```

### 移动文件或改名

Git并不显式跟踪文件移动操作，在Git中重命名了某个文件，仓库中存储的元数据并不会体现出这是一次改名操作。

```shell
[root@single-k8s gitdemo]# git mv README README.md
```

```shell
[root@single-k8s gitdemo]# git status -s
A  README.md
```

其实，运行git mv就相当于运行了下面三条命令：

```shell
[root@single-k8s gitdemo]# git rm README
[root@single-k8s gitdemo]# git add README.md
```

### 查询历史提交

在提交了若干更新，又或者克隆了某个项目之后，回看提交历史

```shell
[root@single-k8s gitdemo]# git log
commit 67445ce540825e2400494eb77792fcb12a7bc924
Author: 张策 <“2320943144@qq.com”>
Date:   Sat Oct 10 14:18:26 2020 +0800

    跳过暂存区的提交

commit c8672db272eb11f73606b5674bc0ee80addcfcf8
Author: 张策 <“2320943144@qq.com”>
Date:   Sat Oct 10 11:17:59 2020 +0800

    第一次提交
```

不传入任何参数的默认情况下，git log会按时间先后顺序列出所有的提交，最近的更新排在最上面。正如你所看到的，这个命令会列出每个提交的SHA-1校验和、作者的名字和电子邮件地址、提交时间以及提交说明。

### 显示每次提交的差异

显示最近的两次提交的差异。除了显示基本信息之外，换附带了每次commit的变化。当进行代码审查，或者快速浏览某个搭档提交的commit所带来的变化的时候，这个参数就非常有用了。

```shell
[root@single-k8s gitdemo]# git log -p -2
commit 67445ce540825e2400494eb77792fcb12a7bc924
Author: 张策 <“2320943144@qq.com”>
Date:   Sat Oct 10 14:18:26 2020 +0800

    跳过暂存区的提交

diff --git a/a.txt b/a.txt
index ccc3e7b..be4e668 100644
--- a/a.txt
+++ b/a.txt
@@ -1 +1,2 @@
 aaaaa
+bbbbb
diff --git a/b.txt b/b.txt
new file mode 100644
index 0000000..9f47804
--- /dev/null
+++ b/b.txt
@@ -0,0 +1 @@
+bbbbbb

commit c8672db272eb11f73606b5674bc0ee80addcfcf8
Author: 张策 <“2320943144@qq.com”>
Date:   Sat Oct 10 11:17:59 2020 +0800
```

### 每次提交的简略统计

在每次提交的下面列出所有被修改过的文件、有多少文件被修改了以及被修改过的文件的哪些行被移除或是添加了。在每次提交的最后还有一个总结。

```shell
[root@single-k8s gitdemo]# git log --stat
commit 67445ce540825e2400494eb77792fcb12a7bc924
Author: 张策 <“2320943144@qq.com”>
Date:   Sat Oct 10 14:18:26 2020 +0800

    跳过暂存区的提交

 a.txt | 1 +
 b.txt | 1 +
 2 files changed, 2 insertions(+)

commit c8672db272eb11f73606b5674bc0ee80addcfcf8
Author: 张策 <“2320943144@qq.com”>
Date:   Sat Oct 10 11:17:59 2020 +0800

    第一次提交

 a.txt | 1 +
 1 file changed, 1 insertion(+)
```

### 简洁的显示提交信息

除了oneline子选项，还有short，full和fuller选项，它们展示信息的格式基本一致，但是详尽程度不一

```shell
[root@single-k8s gitdemo]# git log --pretty=oneline
67445ce540825e2400494eb77792fcb12a7bc924 跳过暂存区的提交
c8672db272eb11f73606b5674bc0ee80addcfcf8 第一次提交
```

### 按照指定的格式显示

这样的输出对后期提取分析格外有用，因为输出的格式不会随着Git的更新而发生改变。

```shell
[root@single-k8s gitdemo]# git log --pretty=format:"%h - %an,%ar :%s"
67445ce - 张策,42 分钟之前 :跳过暂存区的提交
c8672db - 张策,4 小时之前 :第一次提交
```

git log --pretty=format 常用的选项

![image-20201010150101361](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010150101361.png)

### **限制输出长度**

该命令可用的格式十分丰富--可以是类似“2008-01-15”的具体的某一天，也可以是类似“2 years 1 day 3 minutes ago”的相对日期。

```shell
[root@single-k8s gitdemo]# git log --since=2.weeks
commit 67445ce540825e2400494eb77792fcb12a7bc924
Author: 张策 <“2320943144@qq.com”>
Date:   Sat Oct 10 14:18:26 2020 +0800

    跳过暂存区的提交

commit c8672db272eb11f73606b5674bc0ee80addcfcf8
Author: 张策 <“2320943144@qq.com”>
Date:   Sat Oct 10 11:17:59 2020 +0800

    第一次提交
```

![image-20201010150317934](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010150317934.png)

### 撤销操作

在任何一个阶段，你有可能想要撤销某些操作，有些撤销操作是不可逆的

**重新提交**

有时候我们提交完了才发现漏掉了几个文件没有添加，或者提交信息写错了。可以用下面命令重新提交。

```shell
[root@single-k8s gitdemo]# git commit --amend
```

这个命令会将暂存区中的文件提交，如果自上次提交以来你换未做任何修改（例如，在上次提交后马上执行了此命令），那么快照会保持不变，而你所修改的只是提交信息。

可以像下面这样操作：

```shell
[root@single-k8s gitdemo]# git commit -m "init commit"
[root@single-k8s gitdemo]# git add forgotten_file
[root@single-k8s gitdemo]# git commit --amend
```

```shell
[root@single-k8s gitdemo]# git log
commit 53a18e5823059080d5896b43f2bb0bfafa810b2c
Author: 张策 <“2320943144@qq.com”>
Date:   Sat Oct 10 14:18:26 2020 +0800

    真的忘记了跳过暂存区的提交

commit c8672db272eb11f73606b5674bc0ee80addcfcf8
Author: 张策 <“2320943144@qq.com”>
Date:   Sat Oct 10 11:17:59 2020 +0800

    第一次提交
```

最终你只会有一个提交，第二次提交将代替第一次提交的结果。

### 取消暂存文件

通过git add暂存文件

```shell
[root@single-k8s gitdemo]# vim CONTRIBUTING.md
[root@single-k8s gitdemo]# git add *
[root@single-k8s gitdemo]# git status
# 位于分支 master
# 要提交的变更：
#   （使用 "git reset HEAD <file>..." 撤出暂存区）
#
#	新文件：    CONTRIBUTING.md
#	新文件：    b.txt
#	新文件：    c.txt
#
# 未跟踪的文件:
#   （使用 "git add <file>..." 以包含要提交的内容）
#
#	.gitignore
[root@single-k8s gitdemo]# git reset HEAD CONTRIBUTING.md
[root@single-k8s gitdemo]# git status
# 位于分支 master
# 要提交的变更：
#   （使用 "git reset HEAD <file>..." 撤出暂存区）
#
#	新文件：    b.txt
#	新文件：    c.txt
#
# 未跟踪的文件:
#   （使用 "git add <file>..." 以包含要提交的内容）
#
#	.gitignore
#	CONTRIBUTING.md
```

### 撤销对文件的修改

插销对CONTRIBUTING.md文件的修改，还原成上次提交的样子

```shell
[root@single-k8s gitdemo]# git status
# 位于分支 master
# 要提交的变更：
#   （使用 "git reset HEAD <file>..." 撤出暂存区）
#
#	新文件：    CONTRIBUTING.md
#	新文件：    b.txt
#	新文件：    c.txt
#
# 尚未暂存以备提交的变更：
#   （使用 "git add <file>..." 更新要提交的内容）
#   （使用 "git checkout -- <file>..." 丢弃工作区的改动）
#
#	修改：      CONTRIBUTING.md
#
# 未跟踪的文件:
#   （使用 "git add <file>..." 以包含要提交的内容）
#
#	.gitignore
```

```shell
[root@single-k8s gitdemo]# git checkout -- CONTRIBUTING.md
```

```shell
[root@single-k8s gitdemo]# git status
# 位于分支 master
# 要提交的变更：
#   （使用 "git reset HEAD <file>..." 撤出暂存区）
#
#	新文件：    CONTRIBUTING.md
#	新文件：    b.txt
#	新文件：    c.txt
#
# 未跟踪的文件:
#   （使用 "git add <file>..." 以包含要提交的内容）
#
#	.gitignore
```

## 远程仓库的使用

### 查看远程仓库服务器

```shell
$ git remote
origin
```

git remote命令。它会列出你指定的每一个远程服务器的简写。如果你已经克隆了自己的仓库，那么至少应该能看到origin-这是git给你克隆的仓库服务器的默认名字。

```shell
C:\Users\23209> git clone https://github.com/schacon/ticgit
Cloning into 'ticgit'...
remote: Enumerating objects: 1857, done.
remote: Total 1857 (delta 0), reused 0 (delta 0), pack-reused 1857 eceiving objects:  99% (1839/1857), 324.01 KiB | 11.0Receiving obje
Receiving objects: 100% (1857/1857), 334.06 KiB | 15.00 KiB/s, done.
Resolving deltas: 100% (837/837), done.
C:\Users\23209>cd ticgit
C:\Users\23209\ticgit>git remote
origin
```

### 添加远程仓库	

运行git remote add添加一个新的远程Git仓库，同时指定一个你可以轻松引用的简写

```shell
[root@single-k8s gitdemo]# git remote add pb https://github.com/paulboone/ticgit
[root@single-k8s gitdemo]# git remote -v
pb	https://github.com/paulboone/ticgit (fetch)
pb	https://github.com/paulboone/ticgit (push)
```

### 抓取与拉取

从远程仓库中获得数据，可以执行：

```shell
[root@single-k8s gitdemo]# git fetch pb
warning: no common commits
remote: Enumerating objects: 634, done.
remote: Total 634 (delta 0), reused 0 (delta 0), pack-reused 634
接收对象中: 100% (634/634), 88.93 KiB | 43.00 KiB/s, done.
处理 delta 中: 100% (261/261), done.
来自 https://github.com/paulboone/ticgit
 * [新分支]          master     -> pb/master
 * [新分支]          ticgit     -> pb/ticgit
```

这个命令会去访问远程仓库，从中拉取所有你还没有的数据。执行完成后，你将会拥有那个远程仓库中所有分支的引用，可以随时合并或查看。

### 推送到远程仓库

命令很简单：git push  [remote] [branch-name]。当你想要将master分支推送到origin服务器时（克隆时通常会自动帮你设置好那两个名字），那么运行这个命令就可以将你所做的备份到服务器。

```shell
[root@single-k8s gitdemo]# git push pb master
```

只有当你有所克隆的服务器的写入权限，并且之前没有人推送过时，这条命令才能生效。当你和其他人在同一时间克隆，他们先推送到上游然后你再推送到上游，你的推送就会毫无疑问的被拒绝。你必须先将他们的工作拉取下来并将其合并到你的工作后才能推送。

### 查看某个远程仓库

可以使用git remote show  [remote-name]命令，查看某一个远程仓库的信息。

```shell
[root@single-k8s gitdemo]# git remote show pb
* 远程 pb
  获取地址：https://github.com/paulboone/ticgit
  推送地址：https://github.com/paulboone/ticgit
  HEAD分支：master
  远程分支：
    master 已跟踪
    ticgit 已跟踪
  为 'git push' 配置的本地引用：
    master 推送至 master (本地已过时)
```

### 远程仓库的移除与重命名

重命名引用的名字可以运行git remote  rename去修改一个远程仓库的简写名。例如，想要将pb重命名为paul，可以用git remote rename这样做：

```shell
[root@single-k8s gitdemo]# git remote rename pb paul
[root@single-k8s gitdemo]# git remote
paul
```

移除一个远程仓库-你已经从服务器上搬走了或不再想使用某一个特定的镜像了，又或者某一个贡献者不再贡献了-可以使用git remote rm：

```shell
[root@single-k8s gitdemo]# git remote rm paul
[root@single-k8s gitdemo]# git remote
```

### 打标签

Git可以给历史中的某一个提交打上标签，以示重要。比较有代表性的是人们会使用这个功能来标记发布节点（v1.0等等）

#### **列出标签**

```shell
[root@single-k8s gitdemo]# git tag		
```

#### 查找标签

Git自身的源代码仓库包含标签的数量超过500个。如果只对1.8.5系列感兴趣，可以运行：

```shell
[root@single-k8s gitdemo]# git tag -l 'v1.8.5'
```

#### **创建标签**

Git使用两种主要类型的标签：轻量标签（lightweight）与附注标签（annotated）。

##### 附注标签

附注标签是存储在Git数据库中的一个完整对象。它们是可以被校验；其中包含打标签者的名字、电子邮件地址、日期时间；换有一个标签信息；并且可以使用GPG签名与验证。通常建议创建附注标签，这样你可以拥有以上所有信息；但是你只是想用一个临时的标签，或者因为某些原因不想保存哪些信息，轻量标签也是可用的。

创建一个附注标签，运行tag命令时指定-a选项

```shell
[root@single-k8s gitdemo]# git tag -a v1.0 -m "my version 1.0"
[root@single-k8s gitdemo]# git tag
v1.0
```

查看标签信息

```shell
[root@single-k8s gitdemo]# git show v1.0
tag v1.0
Tagger: 张策 <“2320943144@qq.com”>
Date:   Sun Oct 11 17:15:34 2020 +0800

my version 1.0

commit ec754d9616b7e978a7bb6b731d418d56d3fc5f05
Author: 张策 <“2320943144@qq.com”>
Date:   Sat Oct 10 15:11:43 2020 +0800

    取消init commit

diff --git a/a.txt b/a.txt
index be4e668..db50baa 100644
--- a/a.txt
+++ b/a.txt
@@ -1,2 +1,3 @@
 aaaaa
 bbbbb
+cccc
```

输出显示了打标签者的信息、打标签的日期、附注信息，然后显示具体的提交信息。

##### 轻量标签

轻量标签本质上是将提交校验和存储到一个文件中-没有保存任何其他信息。创建轻量标签，不需要使用-a、-s或-m选项，只需要提供标签名字：

```shell
[root@single-k8s gitdemo]# git tag v1.0-lw
[root@single-k8s gitdemo]# git tag
v1.0
v1.0-lw
```

git show，不会看到额外的标签信息

```shell
[root@single-k8s gitdemo]# git show v1.0-lw
commit ec754d9616b7e978a7bb6b731d418d56d3fc5f05
Author: 张策 <“2320943144@qq.com”>
Date:   Sat Oct 10 15:11:43 2020 +0800

    取消init commit

diff --git a/a.txt b/a.txt
index be4e668..db50baa 100644
--- a/a.txt
+++ b/a.txt
@@ -1,2 +1,3 @@
 aaaaa
 bbbbb
+cccc
```

##### 后期打标签

对历史提交打标签

```shell
[root@single-k8s gitdemo]# git log --pretty=oneline
ec754d9616b7e978a7bb6b731d418d56d3fc5f05 取消init commit
53a18e5823059080d5896b43f2bb0bfafa810b2c 真的忘记了跳过暂存区的提交
c8672db272eb11f73606b5674bc0ee80addcfcf8 第一次提交
```

假设在v1.2时你忘记给项目打标签，也就是在“第一次提交”提交。你可以在之后补上标签。要在那个提交上打标签，你需要在命令的末尾指定提交的校验和（或部分校验和）：

```shell
[root@single-k8s gitdemo]# git tag v0.1 c8672db27
[root@single-k8s gitdemo]# git tag
v0.1
v1.0
v1.0-lw
```

##### 共享标签

推送标签到共享服务器上

```shell
git push pb v0.1
```

##### 删除标签

```shell
[root@single-k8s gitdemo]# git tag -d v0.1
已删除 tag 'v0.1'（曾为 c8672db）
```

##### 检出标签

```shell
[root@single-k8s gitdemo]# git checkout v0.1
A	CONTRIBUTING.md
A	b.txt
A	c.txt
Note: checking out 'v0.1'.

You are in 'detached HEAD' state. You can look around, make experimental
changes and commit them, and you can discard any commits you make in this
state without impacting any branches by performing another checkout.

If you want to create a new branch to retain commits you create, you may
do so (now or later) by using -b with the checkout command again. Example:

  git checkout -b new_branch_name

HEAD 目前位于 c8672db... 第一次提交
```



# Git鼓励使用分支

Git鼓励在工作流程中频繁地使用分支和合并，哪怕一天之内进行许多次

- git的必杀技特性-分支模型
- git分支处理非常的轻量级，瞬间完成新建分支
- 分支切换快捷方便
- 分支是git强大独特之处

要真正理解git分支需要理解git是如何保存数据的

## Git是如何保存数据的？

git保存的不是文件的变化或者差异，而是**一系列不同时刻的文件快照**

![image-20201010091042373](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010091042373.png)

git add暂存操作步骤

- 通过SHA-1哈希算法为每个文件计算校验和，校验和与文件名对应
- 使用blob对象将**当前版本文件快照**保存到git仓库中
- 将校验和信息加入到暂存区域等待提交

文件快照：其实就是指添加文件的那一时刻的文件内容。就像照相机摄影保留了生活中某一时刻的样子。

**Git commit操作步骤**

![image-20201010091441404](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010091441404.png)

- 安照提交的文件目录，计算每个目录的校验和
- 仓库中将上面目录结构、对应的校验和保存为树对象
- 创建一个提交对象，包含树对象指针、提交信息
- 为后面需要的时候重现本次保存的快照

![image-20201010091635771](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010091635771.png)

保存到git数据库中的内容都是用校验和哈希值来作为索引的，不是用文件名

```shell
[root@single-k8s gitdemo2]# git log
commit 92a9f97981a6911edd7fd2e02e59fd69f0db323f
Author: Jone Doe <“2320943144@qq.com”>
Date:   Sun Oct 11 19:41:45 2020 +0800

    项目的第二次提交

commit 821860285b854c90b1d4e264c2298da3629d859a
Author: Jone Doe <“2320943144@qq.com”>
Date:   Sun Oct 11 19:39:54 2020 +0800

    项目的初始化提交
[root@single-k8s gitdemo2]# git cat-file -p   92a9f97981a
tree 96d119d7ca973ca9bf761cdd0f3e2f48bd084999
parent 821860285b854c90b1d4e264c2298da3629d859a
author Jone Doe <“2320943144@qq.com”> 1602416505 +0800
committer Jone Doe <“2320943144@qq.com”> 1602416505 +0800

项目的第二次提交
[root@single-k8s gitdemo2]# git cat-file -p   96d119d7
100644 blob e69de29bb2d1d6434b8b29ae775ad8c2e48c5391	LICENSE
100644 blob 056c1157ea9decc134ade1c61680cdf36f21b78b	README
100644 blob e69de29bb2d1d6434b8b29ae775ad8c2e48c5391	test.rb
[root@single-k8s gitdemo2]# git cat-file -p   056c1157ea9
hhhh
adsfa
[root@single-k8s gitdemo2]# cat README 
hhhh
adsfa
[root@single-k8s gitdemo2]# git cat-file  -h
用法：git cat-file (-t|-s|-e|-p|<类型>|--textconv) <对象>
  或：git cat-file (--batch|--batch-check) < <对象列表>

<类型> 可以是其中之一：blob、tree、commit、tag
    -t                    显示对象类型
    -s                    显示对象大小
    -e                    当没有错误时退出并返回零
    -p                    美观地打印对象的内容
    --textconv            对于数据（blob）对象，对其内容执行 textconv
    --batch               显示从标准输入提供的对象的信息和内容
    --batch-check         显示从标准输入提供的对象的信息
```

**此时git仓库中的对象**

![image-20201010091910564](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010091910564.png)

三个blob对象，保存了文件快照

一个树对象，记录了目录结构和blob对象索引

一个提交对象，包含指向前面树对象指针和提交信息

**做出修改再次提交**

![image-20201010092123632](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010092123632.png)

修改后再提交，产生的提交对象会包含一个指向上次提交对象的指针，parent

## Git分支的本质

Git分支本质上是指向提交对象的可变指针，默认分支名字是master

master分支会在每次的提交操作中自动向前移动（头指针）

![image-20201010092344071](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010092344071.png)

master分支不是一个特殊分支，跟其它分支没有区别。git init命令默认创建master分支，名字可改。

## 创建分支

Git创建分支，只是创建了一个可以移动的新的指针

![image-20201010092532539](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010092532539.png)

![image-20201010092541974](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010092541974.png)

git branch命令只创建一个新分支，并不切换分支

git checkout -b命令创建并切换分支

## 相同提交历史的分支

怎么知道当前在哪个分支上？

通过名为HEAD的特殊指针，指向当前所在的本地分支

![image-20201010092734274](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010092734274.png)

![image-20201010092747897](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010092747897.png)

```shell
root@single-k8s gitdemo2]# git log --pretty=oneline --decorate
92a9f97981a6911edd7fd2e02e59fd69f0db323f (HEAD, testing, test, master) 项目的第二次提交
821860285b854c90b1d4e264c2298da3629d859a 项目的初始化提交		
```

## 分支切换

![image-20201010092818240](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010092818240.png)

## 查看分叉历史

通过git log命令查看分叉历史

![image-20201010092847238](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010092847238.png)

## git分支总结

git版本控制系统

- git分支只包含所指向对象校验和的文件，创建销毁非常高效

- 创建一个新分支就相当于往一个文件中写入41个字节

- 根据父对象寻找共同的祖先进行合并同样高效

- 鼓励开发人员频繁创建使用分支

  

其他大多数版本控制系统

- 创建分支将项目文件复制一遍，保存到特定的目录
- 项目越大，时间越长

## 使用分支开发

![image-20201010093157783](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010093157783.png)

## 使用分支修复紧急问题

![image-20201010093220744](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010093220744.png)

## 合并分支

非直接先祖合并

![image-20201010093243095](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010093243095.png)

一个合并提交

master分支所在的提交不是iss53分支所在提交的直接祖先

git用两个分支的末端快照（C4和C5）以及共同祖先（C2），进行合并

将三方合并结果做成新的快照并自动创建的新的提交（C6）

不需要iss53分支，则可删除

![image-20201010093507536](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010093507536.png)

## 冲突时的分支合并

在两个分支中对同一个文件同一个地方进行了修改，合并时冲突发生

Git会做合并，但不会自动创建一个新的合并提交

![image-20201010093654423](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010093654423.png)

![image-20201010093707433](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010093707433.png)

![image-20201010093714557](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010093714557.png)

**解决冲突**

- 打开包含冲突的文件然后手动解决冲突
- 可以保留一方修改；也可以合并两方修改
- 删除<<<<，=====和>>>>>>行
- 使用git add标记文件为冲突已解决

# 服务器上的Git

## 远程仓库与本地仓库分支开发

- 从远程仓库拉取代码

  ![image-20201010094003968](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010094003968.png)

- 远程和本地提交修改

  ![image-20201010094023772](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010094023772.png)

- 拉取远程差异

  ![image-20201010094039525](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010094039525.png)

  ![image-20201010094054186](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010094054186.png)

  ## 一个项目多个远程仓库

  添加新的远程仓库

  ![image-20201010094134014](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010094134014.png)

  从新的仓库拉取差异

  ![image-20201010094149115](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010094149115.png)

  ## 远程仓库其他的操作

  ![image-20201010094209727](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010094209727.png)

# 协同开发

## 长期分支工作模式

渐进稳定分支的流水线模式

master，保留完全稳定的代码

develop，用来做后续开发或者测试稳定性，一旦达到稳定状态可以合并master

topic，完成某个特性的分支

![image-20201010094354374](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010094354374.png)

随着提交指针不断的右移，稳定分支的指针会落后一大截，前沿分支的指针比较靠前。

![image-20201010094532698](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010094532698.png)

用这种方法可以维护不同层次的稳定性

每个分支具有不同级别的稳定性

当它们稳定后，再合并入具有更高稳定性的分支中

在庞大复杂的项目中，这种模式很有帮助

## 特性分支工作模式

特性分支是一种短期分支，用来实现单一特性或其相关工作

![image-20201010094755954](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010094755954.png)

特性分支对任何规模的项目都适用

## git flow工作流

一个外国人写过一篇《一个成功的Git分支模型》的博文，GitFlow就这样出来了

![image-20201010094906366](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010094906366.png)

gitflow工作流程是围绕项目发布定义的严格分支模型，比特性分支模式复杂，为大规模项目提供一个框架

- master

  存放正式发布的版本，可以作为项目历史版本记录分支，不直接提交代码。仅用于保持一个对应线上运行代码的code base

- develop

  分支为主开发分支，一般不直接提交代码

- release

  基于最新的develop分支创建，当新功能足够发布一个新版本（或者接近新版本发布的截止日期），从develop分支创建一个release分支作为新版本的起点，用于测试，所有的测试bug也在这个分支。测试完成后合并到master并打上版本号，同时也合并到develop，更新最新开发分支。（一旦打了release分支之后不要从develop分支上合并新的改动到release分支），同一时间只有1个，生命周期很短，只为了发布。

- hotfix

  分支基于master分支创建，对线上版本的bug进行修复，完成后直接合并到master分支和develop分支，如果当前换有新功能release分支，也同步到release分支上。同一时间只有1个，生命周期较短

- feature

  分支为新功能分支，feature分支都是基于develop创建的，开发完成后会合并到develop分支上，同时存在多个。

  ![image-20201010095733908](D:\学习\动脑\课堂笔记\架构筑基\git\git操作及其原理\img\image-20201010095733908.png)