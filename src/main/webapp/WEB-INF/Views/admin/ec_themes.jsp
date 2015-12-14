<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">活动管理</strong> /
            <small>主题列表</small>
        </div>
        <div class="am-align-right">
            <a id="additem" href="javascript: void(0);">添加主题</a>
        </div>
    </div>
    <table class="am-table am-table-striped am-table-hover">
        <thead>
        <tr>
            <td>ID</td>
            <td>名称</td>
            <td>描述</td>
            <td>状态</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${themes}" var="i">
            <tr id="r_${i.id}">
                <td>${i.id}</td>
                <td>${i.typename}</td>
                <td title="${i.describe}">${st:summary(i.describe, 12)}</td>
                <td>
                    <select data-id="${i.id}" data-action="${path}/ec/themes/${i.id}/status" class="do-status">
                        <option value="1" <c:if test="${i.status eq 1}">selected</c:if>>使用中</option>
                        <option value="3" <c:if test="${i.status eq 3}">selected</c:if>>不可见</option>
                    </select>
                </td>
                <td>
                    [<a class="do-update" data-id="${i.id}" data-action="${path}/ec/themes/${i.id}" href="javascript:void(0);">修改</a>]
                    [<a class="do-delete" data-id="${i.id}" data-name="${i.typename}" data-action="${path}/ec/themes/${i.id}/status" href="javascript:void(0);">删除</a>]
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div style="display: none;">
        <form id="hideForm" action="${path}/ec/themes" method="post" class="am-form am-form-horizontal">
            <fieldset>
                <legend>主题操作</legend>

                <input type="hidden" id="id" name="id">

                <div class="am-form-group am-form-warning">
                    <label for="typename" class="am-u-sm-3 am-form-label">名称</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="typename" name="typename" value="" maxlength="10">
                    </div>
                </div>

                <div class="am-form-group">
                    <label class="am-u-sm-3 am-form-label" for="describe">描述</label>
                    <div class="am-u-sm-9">
                        <textarea name="describe" rows="5" id="describe" maxlength="100"></textarea>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="status" class="am-u-sm-3 am-form-label">状态</label>
                    <div class="am-u-sm-9">
                        <select id="status" name="status">
                            <option value="1">使用中</option>
                            <option value="3">不可见</option>
                        </select>
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
<jsp:include page="../footer.jsp"/>