<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">圈子管理</strong> /
            <strong class="am-text-primary am-text-lg">${c.groupname}</strong> /
            <small>举报列表</small>
        </div>
    </div>
    <table class="am-table am-table-striped am-table-hover">
        <thead>
        <tr>
            <td>ID</td>
            <td>举报者</td>
            <td>内容</td>
            <td>时间</td>
            <td>状态</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${comments}" var="i">
            <tr id="r_${i.id}">
                <td>${i.id}</td>
                <td>${i.uname}</td>
                <td title="${i.content}">${st:summary(i.content, 15)}</td>
                <td>${i.uptime}</td>
                <td>
                    <select data-id="${i.id}" data-action="${path}/ct/reportpost/${i.id}" class="do-status">
                        <option value="0" <c:if test="${i.status eq 0}">selected</c:if>>新举报</option>
                        <option value="1" <c:if test="${i.status eq 1}">selected</c:if>>已审核</option>
                        <option value="2" <c:if test="${i.status eq 2}">selected</c:if>>未受理</option>
                    </select>
                </td>
                <td>
                    [<a class="do-delete" data-id="${i.id}" data-action="${path}/ct/reportpost/${i.id}" href="javascript:void(0);">删除</a>]
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="../footer.jsp"/>