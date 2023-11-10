# Local Pip 本地pip源

基于whl文件的本地pip源

使用StringBoot

## 1. 准备

### 配置pip目录

`config/app.yaml`

```yaml
pip:
  dir: C:/pip # whl 文件目录
  buffer : 4k # 缓冲区大小
```

### 配置pip源

```ini
[global]
index-url = http://127.0.0.9:9999/simple # 服务器地址
# 推荐添加镜像源，下载本地没有的或不支持的包
extra-index-url =
    https://pypi.tuna.tsinghua.edu.cn/simple
    https://mirrors.aliyun.com/pypi/simple
```

## 2. 启动

待补充...

## 相关链接

- [PEP 503 – Simple Repository API](https://peps.python.org/pep-0503/)