<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">项目管理</strong> /
            <small>项目列表</small>
        </div>
        <div class="am-align-right">
            <a id="additem" href="javascript: void(0);">添加项目</a>
        </div>
    </div>
    <table class="am-table am-table-striped am-table-hover">
        <thead>
        <tr>
            <td>ID</td>
            <td>名称</td>
            <td>联系方式</td>
            <td>描述</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pjs}" var="i">
            <tr id="r_${i.id}">
                <td>${i.id}</td>
                <td>${st:summary(i.name, 10)}</td>
                <td>${i.contactway}</td>
                <td>${st:summary(i.remark, 15)}</td>
                <td>
                    [<a class="do-update" data-id="${i.id}" data-action="${path}/pj/${i.id}" href="javascript:void(0);">修改</a>]
                    [<a class="do-delete" data-id="${i.id}" data-name="${i.name}" data-action="${path}/pj/${i.id}" href="javascript:void(0);">删除</a>]
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div style="display: none;">
        <form id="hideForm" method="post" class="am-form am-form-horizontal">
            <fieldset>
                <legend>项目操作</legend>

                <input type="hidden" id="id" name="id">

                <div class="am-form-group am-form-warning">
                    <label for="name" class="am-u-sm-3 am-form-label">名称</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="name" name="name" value="" maxlength="20">
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="contactway" class="am-u-sm-3 am-form-label">联系方式</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="contactway" name="contactway" value="" maxlength="11">
                    </div>
                </div>

                <div class="am-form-group">
                    <label class="am-u-sm-3 am-form-label" for="remark">描述</label>
                    <div class="am-u-sm-9">
                        <textarea name="remark" rows="5" id="remark" maxlength="100"></textarea>
                    </div>
                </div>

                <p class="am-text-center">
                    <button type="submit" class="am-btn am-btn-primary">提交</button>
                    <input type="reset" class="am-btn am-btn-secondary" value="取消">
                </p>
            </fieldset>
        </form>
    </div>
</div>
<c:if test="${pjs.size() > 0}">
    <div>
        <ul data-am-widget="pagination" class="am-pagination am-pagination-default am-text-center">
        ${pager}
        </ul>
    </div>
</c:if>
<jsp:include page="../footer.jsp"/>