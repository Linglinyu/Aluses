<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">圈子管理</strong> /
            <strong class="am-text-primary am-text-lg">${c.groupname}</strong> /
            <small>聊天内容</small>
        </div>
    </div>
    <c:forEach items="${chats}" var="i">
        <div id="r_${i.id}">
            <p style="margin-bottom: 3px;">
                <span style="color: blue;">【${i.uname}】</span> <span style="color: green">${i.senddate}</span>
                <select class="do-status" data-action="${path}/ct/chats/${i.id}">
                    <option value="1">正常显示</option>
                    <option value="3">禁止显示</option>
                </select>
                <a href="javascript:void(0);" class="do-delete" data-id="${i.id}" data-action="${path}/ct/chats/${i.id}">删除</a>
            </p>
            <div style="padding: 5px 20px; background: #EEEEEE; border-radius: 5px;">${i.content}</div>
        </div>
    </c:forEach>
    <ul data-am-widget="pagination" class="am-pagination am-pagination-default am-text-center">
        ${pager}
    </ul>
</div>

<jsp:include page="../footer.jsp"/>
