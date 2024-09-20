## 教程

该仓库是我整理好的快速构建文档页面的项目，基于[vuepress-theme-vdoing](https://github.com/xugaoyi/vuepress-theme-vdoing)

## 初始化

### 安装

`npm install` 或者 `yarn install`

### 调试

`npm run dev` 或者 `yarn dev`

### 打包

`npm run build`

## 使用

### 作为官网使用

1.将该项目下载到本地

2.根据你的需求，修改`docs/.vuepress/config.js`中的`base`字段

3.使用上面的`初始化`步骤，即可在docs/.vuepress/dist下生成静态页面，将这些上传到你的服务器即可

### 作为github.io项目文档使用

注意：请提前设置ssh秘钥

1.在github上创建一个项目，名称为`你的用户名.github.io`，例如`daenmax.github.io`

2.在你的项目仓库中创建`pages`文件夹

3.将项目下载到`pages`文件夹

4.将`docs/.vuepress/config.js`中的`base`字段设置为你仓库的名字，注意，必须是仓库名字，大小写也必须完全一样，例如`/MyAdmin/`

5.提前`install`好

6.修改`deploy.sh`中的`用户名`和`项目仓库`

```
git push -f git@github.com:用户名/项目仓库.git master:gh-pages
//例如修改为如下
git push -f git@github.com:daenmax/MyAdmin.git master:gh-pages
```

6.使用`Git Bash Here`，输入`sh deploy.sh`（或者直接双击`deploy.sh`，选择使用`Git Bash`运行）

7.这样就会自动创建一个`gh-pages`分支，并自动部署到github.io上，直接访问`你的用户名.github.io/二级路径`
即可，例如`https://daenmax.github.io/MyAdmin/`

## 其他

### 百度统计

修改`docs/.vuepress/config/baiduCode.js`里即可
