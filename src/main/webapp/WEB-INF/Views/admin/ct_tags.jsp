<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">圈子管理</strong> /
            <small>类别列表</small>
        </div>
        <div class="am-align-right">
            <a id="additem" href="javascript: void(0);">添加标签</a>
        </div>
    </div>
    <table class="am-table am-table-striped am-table-hover">
        <thead>
        <tr>
            <td>ID</td>
            <td>名称</td>
            <td>描述</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tags}" var="i">
            <tr id="r_${i.id}">
                <td>${i.id}</td>
                <td>${i.typename}</td>
                <td>${st:summary(i.describe, 12)}</td>
                <td>
                    [<a class="do-update" data-id="${i.id}" data-action="${path}/ct/tags/${i.id}" href="javascript:void(0);">修改</a>]
                    [<a class="do-delete" data-id="${i.id}" data-name="${i.typename}" data-action="${path}/ct/tags/${i.id}" href="javascript:void(0);">删除</a>]
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div style="display: none;">
        <form id="hideForm" action="${path}/ct/tags" method="post" class="am-form am-form-horizontal">
            <fieldset>
                <legend>类别操作</legend>

                <input type="hidden" id="id" name="id">

                <div class="am-form-group am-form-warning">
                    <label for="typename" class="am-u-sm-3 am-form-label">名称</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="typename" name="typename" value="" maxlength="6">
                    </div>
                </div>


                <div class="am-form-group">
                    <label class="am-u-sm-3 am-form-label" for="describe">描述</label>
                    <div class="am-u-sm-9">
                        <textarea name="describe" rows="5" id="describe" maxlength="100"></textarea>
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
<script type="text/javascript" src="${path}/assets/vendor/jscolor/jscolor.js"></script>
<jsp:include page="../footer.jsp"/>