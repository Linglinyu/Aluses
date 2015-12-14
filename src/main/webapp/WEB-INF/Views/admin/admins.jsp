<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">管理员管理</strong> /
            <small>管理员列表</small>
        </div>
        <div class="am-align-right">
            <a id="additem" href="javascript: void(0);">添加管理员</a>
        </div>
    </div>
    <table class="am-table am-table-striped am-table-hover">
        <thead>
        <tr>
            <td>ID</td>
            <td>帐号</td>
            <td>片区</td>
            <td>项目</td>
            <td>姓名</td>
            <td>职位</td>
            <td>身份</td>
            <td>电话</td>
            <td>状态</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="u">
            <tr id="r_${u.id}">
                <td>${u.id}</td>
                <td>${st:summary(u.username,6)}</td>
                <td>${u.region}</td>
                <td data-project="${u.project}">${u.projectname}</td>
                <td>${st:summary(u.truename,6)}</td>
                <td>${st:summary(u.position,6)}</td>
                <td data-role="${u.role}">${u.roleName}</td>
                <td>${u.telephone}</td>
                <td data-status="${u.status}">${u.statusName}</td>
                <td>
                    <c:if test="${u.role eq 1}">
                        不可操作
                    </c:if>
                    <c:if test="${u.role ne 1}">
                        [<a class="do-update" data-id="${u.id}" data-action="${path}/admin/${u.id}" href="javascript:void(0);">修改</a>]
                        [<a class="do-delete" data-id="${u.id}" data-name="${i.username}" data-action="${path}/admin/${u.id}" href="javascript:void(0);">删除</a>]
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div style="display: none;">
        <form id="hideForm" action="${path}/admin" method="post" class="am-form am-form-horizontal">
            <fieldset>
                <legend>管理员操作</legend>

                <input type="hidden" id="id" name="id">

                <div class="am-form-group am-form-warning">
                    <label for="username" class="am-u-sm-3 am-form-label">账号</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="username" name="username" value="" maxlength="18">
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="userpwd" class="am-u-sm-3 am-form-label">密码</label>
                    <div class="am-u-sm-9">
                        <input type="password" id="userpwd" name="userpwd" value="" maxlength="20">
                    </div>
                </div>

<!--                 <div class="am-form-group am-form-warning"> -->
<!--                     <label for="region" class="am-u-sm-3 am-form-label">片区</label> -->
<!--                     <div class="am-u-sm-9"> -->
<!--                         <input type="number" id="region" name="region" value="" max="10000"> -->
<!--                     </div> -->
<!--                 </div> -->


                <div class="am-form-group">
                    <label for="project" class="am-u-sm-3 am-form-label">项目</label>
                    <div class="am-u-sm-9">
                        <select id="project" name="project">
                            <c:forEach items="${projects}" var="p">
                                <option value="${p.id}">${p.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="truename" class="am-u-sm-3 am-form-label">真实姓名</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="truename" name="truename" value="" maxlength="6">
                    </div>
                </div>

                <div class="am-form-group am-form-warning">
                    <label for="position" class="am-u-sm-3 am-form-label">职务</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="position" name="position" value="" maxlength="10">
                    </div>
                </div>

                <div class="am-form-group am-form-warning">
                    <label class="am-u-sm-3 am-form-label">角色</label>
                    <div class="am-u-sm-9">
                        <input type="radio" name="role" id="role_2" value="2">
                        <label for="role_2">总部管理员</label>
<!--                         <input type="radio" name="role" id="role_3" value="3"> -->
<!--                         <label for="role_3">片区管理员</label> -->
                        <input type="radio" name="role" id="role_4" value="4" checked>
                        <label for="role_4">项目管理员</label>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="telephone" class="am-u-sm-3 am-form-label">电话</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="telephone" name="telephone" value="" maxlength="11">
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="hasdelpost" class="am-u-sm-3 am-form-label">删除随手拍</label>
                    <div class="am-u-sm-9">
                        <select id="hasdelpost" name="hasdelpost">
                            <option value="0">不允许</option>
                            <option value="1">允许</option>
                        </select>
                    </div>
                </div>

                <div class="am-form-group am-form-warning">
                    <label class="am-u-sm-3 am-form-label">状态</label>
                    <div class="am-u-sm-9">
                        <input type="radio" name="status" id="status_0" value="0">
                        <label for="status_0">未审核</label>
                        <input type="radio" name="status" id="status_1" value="1" checked>
                        <label for="status_1">已审核</label>
                        <input type="radio" name="status" id="status_2" value="2">
                        <label for="status_2">禁用</label>
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
<c:if test="${users.size() > 0}">
    <div>
        <ul data-am-widget="pagination" class="am-pagination am-pagination-default am-text-center">
        ${pager}
        </ul>
    </div>
</c:if>

<jsp:include page="../footer.jsp"/>
