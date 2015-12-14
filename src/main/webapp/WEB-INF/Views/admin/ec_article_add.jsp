<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">活动管理</strong> /
            <strong class="am-text-primary am-text-lg">文章列表</strong> /
            <small>新建活动</small>
        </div>
    </div>
    <div class="am-g">
        <form id="form" action="${path}/ec/article" method="post" class="am-form am-form-horizontal" enctype="multipart/form-data">
            <div class="am-form-group">
                <label for="eid" class="am-u-sm-3 am-form-label">主题</label>
                <div class="am-u-sm-9">
                    <select id="eid" name="eid">
                        <c:forEach items="${themes}" var="t">
                            <option value="${t.id}">${t.typename}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="am-form-group">
                <label for="title" class="am-u-sm-3 am-form-label">标题</label>
                <div class="am-u-sm-9">
                    <input type="text" id="title" name="title" maxlength="42">
                </div>
            </div>

<!--             <div class="am-form-group"> -->
<!--                 <label for="uid" class="am-u-sm-3 am-form-label">作者ID</label> -->
<!--                 <div class="am-u-sm-9"> -->
<%--                     <input type="number" id="uid" name="uid" value="${sessionScope.user.id}" maxlength="7"> --%>
<!--                 </div> -->
<!--             </div> -->

            <div class="am-form-group">
                <label for="uname" class="am-u-sm-3 am-form-label">作者</label>
                <div class="am-u-sm-9">
                    <input type="text" id="uname" name="uname" value="${sessionScope.user.username}" maxlength="6">
                </div>
            </div>

            <div class="am-form-group">
                <label for="telephone" class="am-u-sm-3 am-form-label">作者电话</label>
                <div class="am-u-sm-9">
                    <input type="text" id="telephone" name="telephone" value="${sessionScope.user.telephone}" maxlength="11">
                </div>
            </div>

            <c:if test="${sessionScope.user.role eq 1}">
                <div class="am-form-group">
                    <label for="project" class="am-u-sm-3 am-form-label">项目</label>
                    <div class="am-u-sm-9">
                        <select name="project" id="project">
                            <c:forEach items="${projects}" var="t">
                                <option value="${t.id}">${t.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </c:if>
            <c:if test="${sessionScope.user.role ne 1}">
                <input type="hidden" name="project" value="${sessionScope.user.project}">
            </c:if>

<!--             <div class="am-form-group"> -->
<!--                 <label for="region" class="am-u-sm-3 am-form-label">区域</label> -->
<!--                 <div class="am-u-sm-9"> -->
<%--                     <input type="number" id="region" name="region" value="${sessionScope.user.region}" maxlength="6"> --%>
<!--                 </div> -->
<!--             </div> -->

            <div class="am-form-group am-form-warning">
                <label for="begintime" class="am-u-sm-3 am-form-label">开始时间</label>
                <div class="am-u-sm-9">
                    <div class="am-input-group date" id="begin_date">
                        <input size="10" id="begintime" name="begintime" type="text" class="am-form-field">
                        <span class="am-input-group-label add-on"><i class="icon-th am-icon-calendar"></i></span>
                    </div>
                </div>
            </div>

            <div class="am-form-group am-form-warning">
                <label for="endtime" class="am-u-sm-3 am-form-label">结束时间</label>
                <div class="am-u-sm-9">
                    <div class="am-input-group date" id="end_date">
                        <input size="10" id="endtime" name="endtime" type="text" class="am-form-field">
                        <span class="am-input-group-label add-on"><i class="icon-th am-icon-calendar"></i></span>
                    </div>
                </div>
            </div>

            <div class="am-form-group">
                <label for="address" class="am-u-sm-3 am-form-label">地址</label>
                <div class="am-u-sm-9">
                    <input type="text" id="address" name="address" maxlength="33">
                </div>
            </div>

            <div class="am-form-group am-form-warning">
                <label for="enrollendtime" class="am-u-sm-3 am-form-label">报名截止</label>
                <div class="am-u-sm-9">
                    <div class="am-input-group date" id="last_date">
                        <input size="10" id="enrollendtime" name="enrollendtime" type="text" class="am-form-field">
                        <span class="am-input-group-label add-on"><i class="icon-th am-icon-calendar"></i></span>
                    </div>
                </div>
            </div>

            <div class="am-form-group">
                <label for="upperlimit" class="am-u-sm-3 am-form-label">人数限制</label>
                <div class="am-u-sm-9">
                    <input type="number" maxlength="4" id="upperlimit" name="upperlimit">
                </div>
            </div>

            <div class="am-form-group">
                <label for="phone" class="am-u-sm-3 am-form-label">联系电话</label>
                <div class="am-u-sm-9">
                    <input type="text" id="phone" name="phone" maxlength="11">
                </div>
            </div>

            <div class="am-form-group">
                <label for="content" class="am-u-sm-3 am-form-label">内容</label>
                <div class="am-u-sm-9">
                    <textarea name="content" rows="5" id="content" maxlength="500"></textarea>
                </div>
            </div>

            <%--<div class="am-form-group">
                <label for="isonlinepay" class="am-u-sm-3 am-form-label">是否收费</label>
                <div class="am-u-sm-9">
                    <select id="isonlinepay" name="isonlinepay">
                        <option value="0">不收费</option>
                        <option value="1" selected>收费</option>
                    </select>
                </div>
            </div>--%>

            <div class="am-form-group">
                <label for="eid" class="am-u-sm-3 am-form-label">状态</label>
                <div class="am-u-sm-9">
                    <select id="status" name="status">
                        <option value="0">待审核</option>
                        <option value="1" selected>已通过</option>
                        <option value="2">未通过</option>
                    </select>
                </div>
            </div>
            
           <%--<div class="am-form-group" id="divMoney">
                <label for="money" class="am-u-sm-3 am-form-label">费用</label>
                <div class="am-u-sm-9">
                    <input type="text" id="money" name="money">
                </div>
            </div>--%>

           <div class="am-form-group">
                <label for="img" class="am-u-sm-3 am-form-label">添加图片</label>
                <div class="am-u-sm-9">
                    <input type="file" name="img">
                </div>
            </div>
            
            <p class="am-text-center">
                <button type="submit" class="am-btn am-btn-primary">提交</button>
                <a href="javascript:history.back();" class="am-btn am-btn-secondary">返回</a>
            </p>
        </form>
    </div>
</div>
<jsp:include page="../footer.jsp"/>