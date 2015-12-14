<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">活动管理</strong> /
            <small>活动列表</small>
        </div>
        <div class="am-fr">
            <a href="${path}/ec/article">添加活动</a>
        </div>
    </div>
    <form action="${path}/ec/" method="get" class="am-form am-form-horizontal">
        <div class="am-form-group">
            <c:if test="${sessionScope.user.role eq 1}">
                <div class="am-u-sm-3">
                    <select name="project">
                        <option value="0" <c:if test="${project eq 0}">selected</c:if>> -- 全部 --</option>
                        <c:forEach items="${projects}" var="p">
                            <option value="${p.id}" <c:if test="${project eq p.id}">selected</c:if>>${p.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="am-u-sm-7">
                    <input type="text" name="key" value="${key}" maxlength="18" placeholder="输入活动名称搜索">
                </div>
            </c:if>
            <c:if test="${sessionScope.user.role ne 1}">
                <div class="am-u-sm-10">
                    <input type="text" name="key" value="" maxlength="18" placeholder="输入活动名称搜索">
                </div>
            </c:if>
            <button class="am-btn am-btn-primary am-u-sm-2">搜索</button>
        </div>
    </form>
    <table class="am-table am-table-striped am-table-hover">
        <thead>
        <tr>
        	<td>项目</td>
            <td>标题</td>
            <td>作者</td>
            <td>报名截止</td>
            <td>人数上限</td>
            <%--<td>费用</td>--%>
            <td>状态</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${ats}" var="i">
            <tr id="r_${i.id}">
                <td result="${i.result}">${st:summary(i.result, 10)}</td>
                <td title="${i.title}">${st:summary(i.title, 10)}</td>
                <td>${i.uname}</td>
                <td>${i.enrollendtime}</td>
                <td>${i.upperlimit}</td>
                <%--<td>${i.isonlinepay eq 1 ? i.money : "不需要"}</td>--%>
                <td>
                    <select data-id="${i.id}" data-action="${path}/ec/article/${i.id}" class="do-status">
                        <option value="0" <c:if test="${i.status eq 0}">selected</c:if>>待审核</option>
                        <option value="1" <c:if test="${i.status eq 1}">selected</c:if>>已通过</option>
                        <option value="2" <c:if test="${i.status eq 2}">selected</c:if>>未通过</option>
                    </select>
                </td>
                <td>
                    [<a href="${path}/ec/article?id=${i.id}">修改</a>]
                    [<a href="${path}/ec/ttake?eid=${i.id}">报名情况</a>]
                    [<a class="do-delete" data-id="${i.id}" data-name="${i.title}" data-action="${path}/ec/article/${i.id}"
                        href="javascript:void(0);">删除</a>]
                    [<a href="${path}/ec/result?id=${i.id}">总结</a>]
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<c:if test="${ats.size() > 0}">
    <div>
        <ul data-am-widget="pagination" class="am-pagination am-pagination-default am-text-center">
                ${pager}
        </ul>
    </div>
</c:if>
<jsp:include page="../footer.jsp"/>