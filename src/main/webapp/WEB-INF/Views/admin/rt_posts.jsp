<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">随手拍管理</strong> /
            <small>随手拍列表</small>
        </div>
    </div>
    <form action="${path}/rt/posts" method="get" class="am-form am-form-horizontal">
        <div class="am-form-group">
            <c:if test="${sessionScope.user.role eq 1}">
            <div class="am-u-sm-3">
                <select name="project">
                    <option value="0" <c:if test="${project eq 0}">selected</c:if>> -- 全部 -- </option>
                    <c:forEach items="${projects}" var="p">
                        <option value="${p.id}" <c:if test="${project eq p.id}">selected</c:if>>${p.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="am-u-sm-7">
                <input type="text" name="key" value="${key}" placeholder="输入标题搜索">
            </div>
            </c:if>
            <c:if test="${sessionScope.user.role ne 1}">
            <div class="am-u-sm-10">
                <input type="text" name="key" value="" placeholder="输入标题搜索">
            </div>
            </c:if>
            <button class="am-btn am-btn-primary am-u-sm-2">搜索</button>
        </div>
    </form>
    <table class="am-table am-table-striped am-table-hover">
        <thead>
        <tr>
            <td>ID</td>
            <td>标题</td>
            <td>作者</td>
            <td>日期</td>
            <td>评论/点赞/投诉数</td>
            <td>状态</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${posts}" var="i">
            <tr id="r_${i.id}">
                <td>${i.id}</td>
                <td>${st:summary(i.title, 12)}</td>
                <td>${i.uname}</td>
                <td>${i.uptime}</td>
                <td>${i.cnmu}/${i.praisenum}/${i.reportnum==null?0:i.reportnum}</td>
                <td>
                    <c:if test="${i.status eq 0}">待审核</c:if>
                    <c:if test="${i.status eq 1}">已通过</c:if>
                    <c:if test="${i.status eq 2}">未通过</c:if>
                </td>
                <td>
                    [<a href="${path}/rt/post?id=${i.id}">详细</a>]
                    [<a href="${path}/rt/post/comment?id=${i.id}">评论</a>]
                    <c:if test="${sessionScope.user.hasdelpost eq 1}">
                        [<a class="do-delete" data-id="${i.id}" data-name="${i.title}" data-action="${path}/rt/posts/${i.id}" href="javascript:void(0);">删除</a>]
                    </c:if>
                    [<a href="${path}/rt/post/reportpost?id=${i.id}">投诉管理</a>]
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<c:if test="${posts.size() > 0}">
    <div>
        <ul data-am-widget="pagination" class="am-pagination am-pagination-default am-text-center">
            ${pager}
        </ul>
    </div>
</c:if>
<jsp:include page="../footer.jsp"/>