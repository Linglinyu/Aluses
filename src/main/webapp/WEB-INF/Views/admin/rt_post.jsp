<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">随手拍管理</strong> /
            <strong class="am-text-primary am-text-lg">随手拍列表</strong> /
            <small>${post.title}</small>
        </div>
    </div>
    <div class="am-g">
        <div class="am-u-md-12 am-u-lg-8">
            <form id="form" action="${path}/rt/post/${post.id}" method="post" class="am-form am-form-horizontal">

                <div class="am-form-group">
                    <label for="id" class="am-u-sm-3 am-form-label">编号</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="id" name="id" value="${post.id}" readonly>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="uname" class="am-u-sm-3 am-form-label">发表人</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="uname" name="uname" value="${post.uname}" maxlength="6" readonly>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="telephone" class="am-u-sm-3 am-form-label">电话</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="telephone" name="telephone" value="${post.telephone}" maxlength="11">
                    </div>
                </div>

<!--                 <div class="am-form-group"> -->
<!--                     <label for="region" class="am-u-sm-3 am-form-label">区域</label> -->
<!--                     <div class="am-u-sm-9"> -->
<%--                         <input type="number" id="region" name="region" value="${post.region}"> --%>
<!--                     </div> -->
<!--                 </div> -->


                <div class="am-form-group">
                    <label for="project" class="am-u-sm-3 am-form-label">项目</label>
                    <div class="am-u-sm-9">
                        <select id="project" name="project" <c:if test="${sessionScope.user.role ne 1}">disabled</c:if>>
                            <c:forEach items="${projects}" var="p">
                                <option value="${p.id}" <c:if test="${post.project eq p.id}">selected</c:if>>${p.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="title" class="am-u-sm-3 am-form-label">标题</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="title" name="title" value="${post.title}" maxlength="42">
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="rid" class="am-u-sm-3 am-form-label">标签</label>
                    <div class="am-u-sm-9">
                        <select id="rid" name="rid">
                            <c:forEach items="${ttakes}" var="tag">
                                <option value="${tag.id}" <c:if test="${post.rid eq tag.id}">selected</c:if>>${tag.typename}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="content" class="am-u-sm-3 am-form-label">内容</label>
                    <div class="am-u-sm-9">
                        <textarea name="content" rows="5" id="content" maxlength="500">${post.content}</textarea>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="status" class="am-u-sm-3 am-form-label">状态</label>
                    <div class="am-u-sm-9">
                        <select id="status" name="status">
                            <option value="0" <c:if test="${post.status eq 0}">selected</c:if>>待审核</option>
                            <option value="1" <c:if test="${post.status eq 1}">selected</c:if>>审核通过</option>
                            <option value="2" <c:if test="${post.status eq 2}">selected</c:if>>审核不通过</option>
                        </select>
                    </div>
                </div>

                <p class="am-text-center">
                    <button type="submit" class="am-btn am-btn-primary">提交</button>
                    <a href="${path}/rt/post/comment?id=${post.id}" class="am-btn am-btn-secondary">查看评论</a>
                </p>

            </form>
        </div>
        <div class="am-u-md-12 am-u-lg-4 am-text-center">
            <h3>图片列表</h3><hr>
            <c:forEach items="${post.smallpic.split(';')}" var="url">
                <img id="iconImage" src="${path}/${url}" style="max-width: 240px; max-height: 320px;"><br><br>
            </c:forEach>
        </div>
    </div>
</div>
<jsp:include page="../footer.jsp"/>