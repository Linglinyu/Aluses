<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">活动管理</strong> /
            <strong class="am-text-primary am-text-lg">${article.title}</strong> /
            <small>报名列表</small>
        </div>
        <div class="am-fr">
        	<a href="${path}/ec/ttake/${article.id}/export">导出数据</a>
        </div>
    </div>
    <table class="am-table am-table-striped am-table-hover">
        <thead>
        <tr>
            <td>ID</td>
            <td>申请者</td>
            <td>电话</td>
            <td>申请时间</td>
            <td>审核时间</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${takes}" var="i">
            <tr id="r_${i.id}">
                <td>${i.id}</td>
                <td>${i.uname}</td>
                <td>${i.telephone}</td>
                <td><fmt:formatDate value="${i.ptime}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td>
                    <c:if test="${i.audittime == null}">
                        <a href="javascript:void(0);" data-name="${i.uname}" data-action="${path}/ec/ttake/${i.id}" class="do-audit">审核通过</a>
                    </c:if>
                    <c:if test="${i.audittime != null}">
                        <fmt:formatDate value="${i.audittime}" pattern="yyyy-MM-dd HH:mm"/>
                    </c:if>
                </td>
                <td>
                [<a class="do-delete" data-id="${i.id}" data-name="${i.uname}" data-action="${path}/ec/ttake/${i.id}/delete"
                        href="javascript:void(0);">删除</a>]
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<c:if test="${takes.size() > 0}">
    <div>
        <ul data-am-widget="pagination" class="am-pagination am-pagination-default am-text-center">
                ${pager}
        </ul>
    </div>
</c:if>
<jsp:include page="../footer.jsp"/>