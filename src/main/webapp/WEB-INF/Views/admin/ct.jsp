<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">圈子管理</strong> /
            <small>圈子列表</small>
        </div>
<!--         <div class="am-align-right"> -->
<!--             <a id="additem"  href="javascript: void(0);">添加圈子</a> -->
<!--         </div> -->
    </div>
    <form action="${path}/ct" method="get" class="am-form am-form-horizontal">
        <div class="am-form-group">
            <c:if test="${sessionScope.user.role eq 1}">
                <div class="am-u-sm-3">
                    <select name="project">
                        <option value="0" <c:if test="${project eq 0}">selected</c:if>> -- 全部 -- </option>
                        <c:forEach items="${projects}" var="p">
                            <option value="${p.id}" <c:if test="${project eq p.id}">selected</c:if>>${p.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="am-u-sm-7">
                    <input type="text" name="key" value="${key}" placeholder="输入圈子名称搜索">
                </div>
            </c:if>
            <c:if test="${sessionScope.user.role ne 1}">
                <div class="am-u-sm-10">
                    <input type="text" name="key" value="" placeholder="输入圈子名称搜索">
                </div>
            </c:if>
            <button class="am-btn am-btn-primary am-u-sm-2">搜索</button>
        </div>
    </form>
    <table class="am-table am-table-striped am-table-hover">
        <thead>
        <tr>
            <td>ID</td>
            <td>名称</td>
            <td>创建者</td>
        <%--    <td>区域</td>
            <td>创建者角色</td>--%>
            <td>状态</td>
            <td>新成员审核</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cts}" var="i">
            <tr id="r_${i.id}">
                <td>${i.id}</td>
                <td>${i.groupname}</td>
                <td>${i.uname}</td>
<%--                <td>${i.region}</td>
                <td>${i.ownerrole == 1 ? "项目管理员" : "业主"}</td>--%>
                <td>
                    <select data-id="${i.id}" data-action="${path}/ct/${i.id}/status" class="do-status">
                        <option value="0" <c:if test="${i.status eq 0}">selected</c:if>>待审核</option>
                        <option value="1" <c:if test="${i.status eq 1}">selected</c:if>>已通过</option>
                        <option value="2" <c:if test="${i.status eq 2}">selected</c:if>>未通过</option>
                        <option value="4" <c:if test="${i.status eq 4}">selected</c:if>>关闭中</option>
                    </select>
                </td>
                <td>
                    <select data-id="${i.id}" data-action="${path}/ct/${i.id}/audit" class="do-audit">
                        <option value="0" <c:if test="${i.ismemberaudit eq 0}">selected</c:if>>不需要</option>
                        <option value="1" <c:if test="${i.ismemberaudit eq 1}">selected</c:if>>需要</option>
                    </select>
                </td>
                <td>
                    [<a href="${path}/ct/detail?id=${i.id}">修改</a>]
                    [<a href="${path}/ct/members?id=${i.id}">成员</a>]
<%--                     [<a href="${path}/ct/chats?id=${i.id}">聊天</a>] --%>
                    [<a class="do-delete" data-id="${i.id}" data-name="${i.groupname}" data-action="${path}/ct/${i.id}/status" href="javascript:void(0);">删除</a>]
                    [<a href="${path}/ct/reportpost?id=${i.id}">投诉管理</a>]
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="am-g" id="hideDiv" style="display: none;">
        <div class="am-u-md-12 am-u-lg-8">
            <form id="hideForm"  action="${path}/ct" method="post" class="am-form am-form-horizontal">

                <input type="hidden" id="id" name="id" value="">

                <div class="am-form-group">
                    <label for="groupname" class="am-u-sm-3 am-form-label">圈子名称</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="groupname" name="groupname" value="" maxlength="6">
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="uname" class="am-u-sm-3 am-form-label">创建人名字</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="uname" name="uname" value="" maxlength="6">
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="telephone" class="am-u-sm-3 am-form-label">电话</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="telephone" name="telephone" value="" maxlength="11">
                    </div>
                </div>

<!--                 <div class="am-form-group"> -->
<!--                     <label for="region" class="am-u-sm-3 am-form-label">区域</label> -->
<!--                     <div class="am-u-sm-9"> -->
<!--                         <input type="number" id="region" name="region" value="" maxlength="3"> -->
<!--                     </div> -->
<!--                 </div> -->

                <div class="am-form-group">
                    <label for="project" class="am-u-sm-3 am-form-label">项目</label>
                    <div class="am-u-sm-9">
                        <select id="project" name="project" <c:if test="${sessionScope.user.role ne 1}">disabled</c:if>>
                            <c:forEach items="${projects}" var="p">
                                <option value="${p.id}">${p.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                
                
                <div class="am-form-group">
                    <label for="grouptype" class="am-u-sm-3 am-form-label">圈子类型</label>
                    <div class="am-u-sm-9">
                        <select id="grouptype" name="grouptype">
                            <c:forEach items="${tags}" var="tag">
                                <option value="${tag.id}">${tag.typename}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="groupdescribe" class="am-u-sm-3 am-form-label">圈子描述</label>
                    <div class="am-u-sm-9">
                        <textarea name="groupdescribe" rows="5" id="groupdescribe" maxlength="166"></textarea>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="status" class="am-u-sm-3 am-form-label">状态</label>
                    <div class="am-u-sm-9">
                        <select id="status" name="status">
                            <option value="0">待审核</option>
                            <option value="1">审核通过</option>
                            <option value="2">审核未通过</option>
                            <option value="4">关闭中</option>
                        </select>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="ismemberaudit" class="am-u-sm-3 am-form-label">新成员审核</label>
                    <div class="am-u-sm-9">
                        <select id="ismemberaudit" name="ismemberaudit">
                            <option value="0">不需要审核</option>
                            <option value="1">需要审核</option>
                        </select>
                    </div>
                </div>

                <p class="am-text-center">
                    <button type="submit" class="am-btn am-btn-primary">提交</button>
                    <input type="reset" class="am-btn am-btn-secondary" value="取消">
                </p>
            </form>
        </div>
<!--         <div class="am-u-md-12 am-u-lg-4 am-text-center"> -->
<!--             <h3>图片</h3><hr> -->
<%--             <div id="../${grouppic}"></div> --%>
<!--             <img id="iconImage" src="grouppic" style="max-width: 240px; max-height: 320px;"> --%> -->
<!--         </div> -->
       <div class="am-u-md-12 am-u-lg-4 am-text-center">
            <h3>图片列表</h3><hr>
            <c:forEach items="${i.grouppic.split(';')}" var="url">
                <img id="iconImage" src="${path}/${url}" style="max-width: 240px; max-height: 320px;">
            </c:forEach>
        </div>
    </div>
</div>
<c:if test="${cts.size() > 0}">
    <div>
        <ul data-am-widget="pagination" class="am-pagination am-pagination-default am-text-center">
        ${pager}
        </ul>
    </div>
</c:if>
<jsp:include page="../footer.jsp"/>