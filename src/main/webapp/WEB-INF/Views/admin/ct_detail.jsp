<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">随手拍管理</strong> /
            <strong class="am-text-primary am-text-lg">圈子列表</strong> /
            <small>${ct.groupname}</small>
        </div>
    </div>
    <div class="am-g">
        <div class="am-u-md-12 am-u-lg-8">
            <form id="form"  action="${path}/ct/detail" method="post" class="am-form am-form-horizontal">

                <input type="hidden" id="id" name="id" value="${ct.id}">

                <div class="am-form-group">
                    <label for="groupname" class="am-u-sm-3 am-form-label">圈子名称</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="groupname" name="groupname" value="${ct.groupname}" maxlength="6">
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="uname" class="am-u-sm-3 am-form-label">创建人名字</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="uname" name="uname" value="${ct.uname}" maxlength="6" readonly="readonly">
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="telephone" class="am-u-sm-3 am-form-label">电话</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="telephone" name="telephone" value="${ct.telephone}" maxlength="11">
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
                                <option value="${p.id}" <c:if test="${ct.project eq p.id}">selected</c:if>>${p.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                
                
                <div class="am-form-group">
                    <label for="grouptype" class="am-u-sm-3 am-form-label">圈子类型</label>
                    <div class="am-u-sm-9">
                        <select id="grouptype" name="grouptype">
                            <c:forEach items="${tags}" var="tag">
                                <option value="${tag.id}" <c:if test="${ct.grouptype eq tag.id}">selected</c:if>>${tag.typename}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="groupdescribe" class="am-u-sm-3 am-form-label">圈子描述</label>
                    <div class="am-u-sm-9">
                        <textarea name="groupdescribe" rows="5" id="groupdescribe"  maxlength="166">${ct.groupdescribe}</textarea>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="status" class="am-u-sm-3 am-form-label">状态</label>
                    <div class="am-u-sm-9">
                        <select id="status" name="status">
                            <option value="0" <c:if test="${ct.status == 0}">selected</c:if>>待审核</option>
                            <option value="1" <c:if test="${ct.status == 1}">selected</c:if>>审核通过</option>
                            <option value="2" <c:if test="${ct.status == 2}">selected</c:if>>审核未通过</option>
                            <option value="4" <c:if test="${ct.status == 4}">selected</c:if>>关闭中</option>
                        </select>
                    </div>
                </div>

                <div class="am-form-group">
                    <label for="ismemberaudit" class="am-u-sm-3 am-form-label">新成员审核</label>
                    <div class="am-u-sm-9">
                        <select id="ismemberaudit" name="ismemberaudit">
                            <option value="0" <c:if test="${ct.ismemberaudit == 0}">selected</c:if>>不需要审核</option>
                            <option value="1" <c:if test="${ct.ismemberaudit == 1}">selected</c:if>>需要审核</option>
                        </select>
                    </div>
                </div>

                <p class="am-text-center">
                    <button type="submit" class="am-btn am-btn-primary">提交</button>
                    <a href="javascript:history.back();" class="am-btn am-btn-secondary">返回</a>
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
            <c:forEach items="${ct.grouppic.split(';')}" var="url">
                <img id="iconImage" src="${path}/${url}" style="max-width: 240px; max-height: 320px;">
            </c:forEach>
        </div>
    </div>
</div>
<jsp:include page="../footer.jsp"/>