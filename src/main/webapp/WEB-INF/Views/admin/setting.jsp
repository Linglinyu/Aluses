<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">参数设置</strong>
        </div>
    </div>
    <div  style="padding-left: 30px;">
        <c:forEach items="${pms}" var="p">
            <p><label for="p_${p.id}">${p.parname}</label>：
                <select data-action="${path}/setting/${p.id}" data-name="${p.parname}">
                    <option value="0" <c:if test="${p.parvalue == 0}">selected</c:if>>不需要审核</option>
                    <option value="1" <c:if test="${p.parvalue == 1}">selected</c:if>>需要审核</option>
                </select>
            </p>
        </c:forEach>
    </div>
</div>
<jsp:include page="../footer.jsp"/>