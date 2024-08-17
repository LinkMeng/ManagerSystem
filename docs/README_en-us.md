# Home code

[中文文档](./READM.md)

## Manager System

Please design a Spring/SpringBoot system that would do the following:

Basic functionality

1.  There are 2 kinds of roles in this system: `admin` & `user`. Use a header encoded in Base64 to pass the role info into all urls of this system. Please decode this header in all requests to check who is accessing the endpoint.

    Decoded header could be:

    ```json
    {
        "userId": 123456,
        "accountName": "XXXXXXX",
        "role": "admin"
    }
    ```

2.  Design a POST endpoint `/admin/addUser` for admins which could add accesses for users. Non-admin accounts will get an error message as they have no access to this endpoint. POST body should contain:

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

    This means user 123456 is granted to resource A,B,C.

    Please keep the access info in a file(instead of a database) for persistence.

3.  Design another endpoint `/user/{resource}` for users. `{resource}` could be added by admins in Requirement 2.

    a. Returns success info for users who have this access.

    b. Returns failure for users who do not have this access.

Requirements:

1.  Please submit a runnable code in java/scala
2.  Include unit tests
3.  Consider any kind of error which could happen in this system. Always return a readable error message when an error occurs.
