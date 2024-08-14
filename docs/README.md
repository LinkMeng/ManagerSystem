# Home code

## 管理系统

请设计一个Spring/SpringBoot系统，它将执行以下操作：

基本功能

1.  该系统中有两种角色：`admin`（管理员）和`user`（用户）。使用Base64编码的Header将角色信息传入此系统的所有url中。请在所有请求中解码此Header以检查是谁在访问结构。

    解码后的Header可能如下：

    ```json
    {
        "userId": 123456,
        "accountName": "XXXXXXX",
        "role": "admin"
    }
    ```

2.  为管理员设计一个POST接口`/admin/addUser`，可以为用户添加访问权限。非管理员用户在访问时将收到错误信息，因为他们没有权限访问此接口。POST报文体应该包含以下内容：

    ```json
    {
        "userId": 123456,
        "endpoint": [
            "resource A",
            "resource B",
            "resource C"
        ]
    }
    ```

    上述内容表明用户“123456”被授予资源A、B和C的权限。

    请将访问信息保存在文件（而不是数据库）中以持久化。

3.  为用户设计另一个接口`/user/{resource}`。`{resource}`可以有管理员在需求2中添加。

    a. 为具有此资源访问权限的用户返回成功信息。

    b. 为没有此资源访问权限的用户返回失败信息。

要求：

1.  请提交一个使用Java/Scala编写的可运行的代码
2.  包含单元测试
3.  考虑这个系统中可能发生的任何类型的错误。当发生错误时，始终返回可读的错误信息。
