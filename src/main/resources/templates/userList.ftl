<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <title>User List</title>
</head>
<body>
<div class="container">
    <h1>User List</h1>
    <table>
        <thead>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Phone Number</th>
            </tr>
        </thead>
        <tbody>
            <#if users?? && users?size > 0>
                <#list users as user>
                    <tr>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.email}</td>
                        <td>${user.phoneNumber}</td>
                    </tr>
                </#list>
            <#else>
                <tr>
                    <td colspan="4">No users found.</td>
                </tr>
            </#if>
        </tbody>
    </table>
</div>
</body>
</html>