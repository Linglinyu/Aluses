<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">圈子管理</strong> /
            <strong class="am-text-primary am-text-lg">${c.groupname}</strong> /
            <small>成员列表</small>
        </div>
    </div>
    <table class="am-table am-table-striped am-table-hover">
        <thead>
        <tr>
            <td>UID</td>
            <td>姓名</td>
            <td>电话</td>
            <td>角色</td>
            <td>状态</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${mbs}" var="u">
            <tr id="r_${u.id}/${u.groupid}">
                <td>${u.uid}</td>
                <td>${u.uname}</td>
                <td>${u.telephone}</td>
                <td>${u.role == 2 ? "圈主" : "普通成员"}</td>
                <td>
                    <select data-id="${u.id}" data-action="${path}/ct/members/${u.id}/${u.groupid}/${u.uname}/status" class="do-status">
                        <option value="0" <c:if test="${u.status eq 0}">selected</c:if>>待审核</option>
                        <option value="1" <c:if test="${u.status eq 1}">selected</c:if>>已通过</option>
                        <option value="2" <c:if test="${u.status eq 2}">selected</c:if>>未通过</option>
                        <option value="4" <c:if test="${u.status eq 3}">selected</c:if>>禁用</option>
                    </select>
                </td>
                <td>
                    [<a class="do-delete" data-id="${u.uid}" data-action="${path}/ct/members/${u.id}/${u.groupid}/${u.uname}/status" href="javascript:void(0);">删除</a>]
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<c:if test="${mbs.size() > 0}">
    <div>
        <ul data-am-widget="pagination" class="am-pagination am-pagination-default am-text-center">
        ${pager}
        </ul>
    </div>
</c:if>
<jsp:include page="../footer.jsp"/>
