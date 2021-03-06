<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">投诉管理</strong> /
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
            <td>类别</td>
            <td>描述</td>
            <td>状态</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tags}" var="i">
            <tr id="r_${i.id}">
                <td>${i.id}</td>
                <td>${i.typename}</td>
                <td>${i.category == 1 ? "举报帖子" : "举报圈子"}</td>
                <td>${st:summary(i.describe, 12)}</td>
                <td>${i.status == 1 ? "使用中" : "禁用"}</td>
                <td>
                    [<a class="do-update" data-id="${i.id}" data-action="${path}/rp/tags/${i.id}" href="javascript:void(0);">修改</a>]
                    [<a class="do-delete" data-id="${i.id}" data-name="${i.typename}" data-action="${path}/rp/tags/${i.id}" href="javascript:void(0);">删除</a>]
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div style="display: none;">
        <form id="hideForm" action="${path}/rp/tags" method="post" class="am-form am-form-horizontal">
            <fieldset>
                <legend>类别操作</legend>

                <input type="hidden" id="id" name="id">

                <div class="am-form-group am-form-warning">
                    <label for="typename" class="am-u-sm-3 am-form-label">名称</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="typename" name="typename" value="" maxlength="6">
                    </div>
                </div>

<!--                 <div class="am-form-group"> -->
<!--                     <label for="color" class="am-u-sm-3 am-form-label">类别</label> -->
<!--                     <div class="am-u-sm-9"> -->
<!--                         <input type="text" id="category" name="category" value=""> -->
<!--                     </div> -->
<!--                 </div> -->
                
                <div class="am-form-group">
                    <label for="status" class="am-u-sm-3 am-form-label">类别</label>
                    <div class="am-u-sm-9">
                        <select id="category" name="category">
                            <option value="1">举报帖子</option>
                            <option value="2">举报圈子</option>
                        </select>
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
                            <option value="3">禁用</option>
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
<script type="text/javascript" src="${path}/assets/vendor/jscolor/jscolor.js"></script>
<jsp:include page="../footer.jsp"/>