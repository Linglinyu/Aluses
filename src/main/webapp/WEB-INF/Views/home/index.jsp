<%--这个path为逗比们的特殊配置，以解决图片访问，取不到根目录的问题，直接写死就是屌--%>
<%--测试环境请改为相应的根目录+端口，请勿将该path的修改提交到生产--%>
<%--<c:set var="path" value="http://www.88888881.com:8885/Aluses" scope="request"/>
<c:set var="path_root" value="/Aluses" scope="request"/>--%>
<c:set var="path" value="http://localhost:8082" scope="request"/>
<c:set var="path_root" value="" scope="request"/>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="UTF-8">
    <title><spring:message code="website.name"/></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="${path}/assets/vendor/amazeui/css/amazeui.min.css">
    <style>
        .header {
            text-align: center;
        }

        .header h1 {
            font-size: 200%;
            color: #333;
            margin-top: 30px;
        }

        .header p {
            font-size: 14px;
        }
    </style>
    <script type="text/javascript">
        var BASE_PATH = '${path}';
        var BASE_PATH_ROOT = '${path_root}';
    </script>
</head>
<body>
<div class="header">
    <div class="am-g">
        <h1>管理登陆</h1>
    </div>
    <hr/>
</div>
<div class="am-g">
    <div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
        <h2>登录</h2>
        <hr>

        <form action="${path}/login" method="post" class="am-form">
            <label for="account">账号:</label>
            <input type="text" id="account" name="account" value="">
            <br>
            <label for="password">密码:</label>
            <input type="password" id="password" name="password" value="">
            <br>
            <label for="remember-me">
                <input id="remember-me" type="checkbox">
                记住密码
            </label>
            <br/>

            <div class="am-cf">
                <input type="submit" id="submit" value="登 录" class="am-btn am-btn-primary am-btn-sm am-fl">
            </div>
        </form>
    </div>
</div>

<script data-main="${path}/assets/main" src="${path}/assets/require.js"></script>
</body>
</html>