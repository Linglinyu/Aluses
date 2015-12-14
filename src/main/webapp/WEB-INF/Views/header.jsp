<%--这个path为逗比们的特殊配置，以解决图片访问，取不到根目录的问题，直接写死就是屌--%>
<%--测试环境请改为相应的根目录+端口，请勿将该path的修改提交到生产--%>
<%--<c:set var="path" value="http://www.88888881.com:8885/Aluses" scope="request"/>
<c:set var="path_root" value="/Aluses" scope="request"/>--%>
<c:set var="path" value="http://localhost:8082" scope="request"/>
<c:set var="path_root" value="" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="Author" content="DHC">
    <meta name="Keywords" content="<spring:message code="website.keywords" /><c:if test="${!empty param.keywords}">,${param.keywords}</c:if>">
    <meta name="Description" content="<spring:message code="website.description" /><c:if test="${!empty param.description}">,${param.description}</c:if>">
    <title><spring:message code="website.name"/><c:if test="${!empty param.title}"> | ${param.title}</c:if></title>
    <link rel="stylesheet" href="${path}/assets/vendor/amazeui/css/amazeui.min.css">
    <link rel="stylesheet" href="${path}/assets/vendor/amazeui/css/admin.css">
    <link rel="stylesheet" href="${path}/assets/css/common.css">
    <c:if test="${!empty param.styles}">
        <c:forEach items="${param.styles.split(',')}" var="css">
            <link rel="stylesheet" href="${path}/assets/${css}.css">
        </c:forEach>
    </c:if>
    <script type="text/javascript">
        var BASE_PATH = '${path}';
        var BASE_PATH_ROOT = '${path_root}';
    </script>
</head>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a> 以获得更好的体验！</p>
<![endif]-->

<header class="am-topbar admin-header">
    <div class="am-topbar-brand">
        <strong><spring:message code="website.name" /></strong> <small>管理界面</small>
    </div>

    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>

    <div class="am-collapse am-topbar-collapse" id="topbar-collapse">

        <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
            <li style="line-height: 50px;"><span class="am-icon-clock-o"></span> ${st:now("yyyy年MM月dd日")} </li>
            <li class="am-dropdown" data-am-dropdown>
                <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:void(0);">
                    <span class="am-icon-users"></span> ${sessionScope.user.truename} <span class="am-icon-caret-down"></span>
                </a>
                <ul class="am-dropdown-content">
                    <li><a href="javascript:void(0);" id="repwd"><span class="am-icon-cog"></span> 修改密码</a></li>
                    <li><a href="${path}/"><span class="am-icon-power-off"></span> 退出</a></li>
                </ul>
            </li>
        </ul>
    </div>
</header>

<div class="am-cf admin-main">
    <form id="pwdForm" action="${path}/repwd" method="post" class="am-form am-form-horizontal" style="display: none;">
        <fieldset>
            <legend>修改密码</legend>

            <div class="am-form-group am-form-warning">
                <label for="opwd" class="am-u-sm-3 am-form-label">原密码</label>
                <div class="am-u-sm-9">
                    <input type="password" id="opwd" name="opwd" value="">
                </div>
            </div>

            <div class="am-form-group am-form-warning">
                <label for="npwd" class="am-u-sm-3 am-form-label">新密码</label>
                <div class="am-u-sm-9">
                    <input type="password" id="npwd" name="npwd" value="">
                </div>
            </div>

            <div class="am-form-group am-form-warning">
                <label for="rnpwd" class="am-u-sm-3 am-form-label">密码确认</label>
                <div class="am-u-sm-9">
                    <input type="password" id="rnpwd" name="rnpwd" value="">
                </div>
            </div>

            <p class="am-text-center">
                <button type="submit" class="am-btn am-btn-primary">提交</button>
                <input type="reset" class="am-btn am-btn-secondary" value="取消">
            </p>
        </fieldset>
    </form>
    <!-- sidebar start -->
    <div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
        <div class="am-offcanvas-bar admin-offcanvas-bar">
            <ul class="am-list admin-sidebar-list">
                <c:if test="${sessionScope.user.role eq 1}">
                    <li><a href="${path}/pj"><span class="am-icon-bars"></span> 项目管理</a></li>
                    <li><a href="${path}/admin"><span class="am-icon-user"></span> 管理员管理</a></li>
                </c:if>
                <li class="admin-parent">
                    <a class="am-cf" data-am-collapse="{target: '#nav-readily'}">
                        随手拍管理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
                    </a>
                    <ul class="am-list am-collapse admin-sidebar-sub am-in" id="nav-readily">
                        <c:if test="${sessionScope.user.role eq 1}">
                            <li><a href="${path}/rt/tags"><span class="am-icon-bar-chart"></span> 标签管理</a></li>
                        </c:if>
                        <li><a href="${path}/rt/posts"><span class="am-icon-bitbucket"></span> 帖子和评论</a></li>
                    </ul>
                </li>
<%--                 <li><a href="${path}/ct"><span class="am-icon-user"></span> 圈子管理</a></li> --%>
                <li class="admin-parent">
                    <a class="am-cf" data-am-collapse="{target: '#nav-community'}">
                        圈子管理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
                    </a>
                    <ul class="am-list am-collapse admin-sidebar-sub am-in" id="nav-community">
                        <c:if test="${sessionScope.user.role eq 1}">
                            <li><a href="${path}/ct/tags"><span class="am-icon-bar-chart"></span> 圈子类别</a></li>
                        </c:if>
                        <li><a href="${path}/ct"><span class="am-icon-bitbucket"></span> 圈子管理</a></li>
                    </ul>
                </li>
                <li class="admin-parent">
                    <a class="am-cf" data-am-collapse="{target: '#nav-event'}">
                        活动管理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
                    </a>
                    <ul class="am-list am-collapse admin-sidebar-sub am-in" id="nav-event">
                        <c:if test="${sessionScope.user.role eq 1}">
                            <li><a href="${path}/ec/themes"><span class="am-icon-bar-chart"></span> 主题管理</a></li>
                        </c:if>
                        <li><a href="${path}/ec"><span class="am-icon-bitbucket"></span> 活动召集文章</a></li>
                    </ul>
                </li>
                <li class="admin-parent">
                    <a class="am-cf" data-am-collapse="{target: '#nav-report'}">
                        投诉管理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
                    </a>
                    <ul class="am-list am-collapse admin-sidebar-sub am-in" id="nav-report">
                        <c:if test="${sessionScope.user.role eq 1}">
                            <li><a href="${path}/rp/tags"><span class="am-icon-bar-chart"></span> 投诉类别</a></li>
                        </c:if>
<%--                         <li><a href="${path}/rp/posts"><span class="am-icon-bitbucket"></span> 投诉管理</a></li> --%>
                    </ul>
                </li>
                <c:if test="${sessionScope.user.role eq 1}">
                <li><a href="${path}/setting"><span class="am-icon-server"></span> 参数设置</a></li>
                </c:if>
            </ul>

            <div class="am-panel am-panel-default admin-sidebar-panel">
                <div class="am-panel-bd">
                    <p><span class="am-icon-bookmark"></span> 公告</p>
                    <p><spring:message code="website.name" />v1.0发布。</p>
                </div>
            </div>

            <div class="am-panel am-panel-default admin-sidebar-panel">
                <div class="am-panel-bd">
                    <p><span class="am-icon-tag"></span> 标签</p>
                    <p>更多功能将陆续添加中……</p>
                </div>
            </div>
        </div>
    </div>
    <!-- sidebar end -->

    <!-- content start -->
    <div class="admin-content">