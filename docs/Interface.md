# 接口文档

# 1 为用户添加访问权限

## 1.1 请求地址 - Url

[POST] `/admin/addUser`

## 1.2 请求头 - Request Header

| 字段名       | 说明            | 类型     | 必须 | 示例                                                                                   |
|-----------|---------------|--------|----|--------------------------------------------------------------------------------------|
| User-Info | 用户信息的Base64编码 | String | 是  | eyJ1c2VySWQiOiAxMjM0NTYsICJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwgInJvbGUiOiAiYWRtaW4ifQ== |

## 1.3 请求体 - Request Body

格式：application/json

| 字段名      | 说明   | 类型       | 必须 | 示例     |
|----------|------|----------|----|--------|
| uerId    | 用户Id | int      | 是  | 123456 |
| endpoint | 资源列表 | string[] | 是  |        |

示例：

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

## 1.4 返回体 - Response Body

### 1.4.1 正常场景

格式：application/json

| 字段名     | 说明   | 类型      |
|---------|------|---------|
| value   | 操作结果 | boolean |
| message | 操作信息 | string  |

示例：

```json
{
  "value": true,
  "message": "成功为用户“123,456”配置3条资源。"
}
```

### 1.4.2 异常场景

格式：application/json

| 字段名        | 说明   | 类型     |
|------------|------|--------|
| message    | 异常描述 | string |
| detail     | 异常详情 | string |
| suggestion | 修复建议 | string |

示例：

```json
{
  "message": "入参校验失败。",
  "detail": "入参“userResource.userId”不合法。",
  "suggestion": "请检查入参后重试。"
}
```

## 1.5 请求示例

### 1.5.1 200 OK

#### 请求 - Request

```http request
POST /admin/addUser HTTP/1.1
Host: localhost:8080
User-Info: eyJ1c2VySWQiOiAxMjM0NTYsICJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwgInJvbGUiOiAiYWRtaW4ifQ==

{
    "userId": 123456,
    "endpoint": [
        "resource A",
        "resource B",
        "resource C"
    ]
}
```

#### 返回 - Response

```json
{
  "value": true,
  "message": "成功为用户“123,456”配置3条资源。"
}
```

### 1.5.2 400 Bad Request

#### 请求 - Request

```http request
POST /admin/addUser HTTP/1.1
Host: localhost:8080
User-Info: eyJ1c2VySWQiOiAxMjM0NTYsICJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwgInJvbGUiOiAiYWRtaW4ifQ==

{
    "userId": null,
    "endpoint": [
        "resource A",
        "resource B",
        "resource C"
    ]
}
```

#### 返回 - Response

```json
{
  "message": "入参校验失败。",
  "detail": "入参“userResource.userId”不合法。",
  "suggestion": "请检查入参后重试。"
}
```

### 1.5.3 403 Forbidden

#### 请求 - Request

```http request
POST /admin/addUser HTTP/1.1
Host: localhost:8080
User-Info: eyJ1c2VySWQiOiAxMjM0NTYsICJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwgInJvbGUiOiAidXNlciJ9

{
    "userId": 123456,
    "endpoint": [
        "resource A",
        "resource B",
        "resource C"
    ]
}
```

#### 返回 - Response

```json
{
  "message": "权限校验失败。",
  "detail": "用户“123,456”权限不足。",
  "suggestion": "请联系系统管理员排查解决。"
}
```

### 1.5.4 500 Internal Server Error

#### 请求 - Request

```http request
POST /admin/addUser HTTP/1.1
Host: localhost:8080
User-Info: e====

{
    "userId": 123456,
    "endpoint": [
        "resource A",
        "resource B",
        "resource C"
    ]
}
```

#### 返回 - Response

```json
{
  "message": "用户信息转换失败。",
  "detail": "用户信息转换失败。",
  "suggestion": "请检查请求信息后重试。"
}
```

# 2 查询资源权限

## 2.1 请求地址 - Url

[GET] `/user/{resource}`

## 2.2 请求头 - Header

| 字段名       | 说明            | 类型     | 必须 | 示例                                                                                   |
|-----------|---------------|--------|----|--------------------------------------------------------------------------------------|
| User-Info | 用户信息的Base64编码 | String | 是  | eyJ1c2VySWQiOiAxMjM0NTYsICJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwgInJvbGUiOiAiYWRtaW4ifQ== |

## 2.3 请求参数 - Param

| 字段名      | 说明   | 类型     | 必须 | 示例           |
|----------|------|--------|----|--------------|
| resource | 资源Id | string | 是  | resource%20A |

## 2.4 返回体 - Response Body

### 2.4.1 正常场景

格式：application/json

| 字段名     | 说明   | 类型      |
|---------|------|---------|
| value   | 操作结果 | boolean |
| message | 操作信息 | string  |

示例：

```json
{
  "value": true,
  "message": "当前用户“123,456”有权访问资源“resource A”。"
}
```

### 2.4.2 异常场景

格式：application/json

| 字段名        | 说明   | 类型     |
|------------|------|--------|
| message    | 异常描述 | string |
| detail     | 异常详情 | string |
| suggestion | 修复建议 | string |

示例：

```json
{
  "message": "权限校验失败。",
  "detail": "无法从请求头中获取用户信息。",
  "suggestion": "请检查请求信息后重试。"
}
```

## 2.5 请求示例

### 2.5.1 200 OK

#### 请求 - Request

```http request
GET /user/resource%20A HTTP/1.1
Host: localhost:8080
User-Info: eyJ1c2VySWQiOiAxMjM0NTYsICJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwgInJvbGUiOiAiYWRtaW4ifQ==
```

#### 返回 - Response

```json
{
  "value": true,
  "message": "当前用户“123,456”有权访问资源“resource A”。"
}
```

### 2.5.2 200 OK Failed

#### 请求 - Request

```http request
GET /user/resource%20D HTTP/1.1
Host: localhost:8080
User-Info: eyJ1c2VySWQiOiAxMjM0NTYsICJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwgInJvbGUiOiAiYWRtaW4ifQ==
```

#### 返回 - Response

```json
{
  "value": false,
  "message": "当前用户“123,456”无权访问资源“resource D”。"
}
```

### 2.5.3 403 Forbidden

#### 请求 - Request

```http request
GET /user/resource%20D HTTP/1.1
Host: localhost:8080
```

#### 返回 - Response

```json
{
  "message": "权限校验失败。",
  "detail": "无法从请求头中获取用户信息。",
  "suggestion": "请检查请求信息后重试。"
}
```

### 2.5.4 500 Internal Server Error

#### 请求 - Request

```http request
GET /user/resource%20D HTTP/1.1
Host: localhost:8080
User-Info: e===
```

#### 返回 - Response

```json
{
  "message": "用户信息转换失败。",
  "detail": "用户信息转换失败。",
  "suggestion": "请检查请求信息后重试。"
}
```
