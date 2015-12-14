<jsp:include page="../header.jsp"/>
<div class="am-cf am-padding am-text-center">
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">活动管理</strong> /
            <strong class="am-text-primary am-text-lg">活动总结</strong> /
            <small>${a.title}</small>
        </div>
    </div> 
    <form id="form" action="${path}/ec/result" method="post" class="am-form am-form-horizontal" enctype="multipart/form-data">

                <input type="hidden" id="id" name="id" value="${a.id}" readonly>
                <div type="hidden" >
                        <div class="am-input-group date" id="end_date">
                            <input size="10" id="endtime" name="endtime" type="text" value="<fmt:formatDate value="${a.endtime}" pattern="yyyy-MM-dd HH:mm"/>" class="am-form-field">
                            <span class="am-input-group-label add-on"><i class="icon-th am-icon-calendar"></i></span>
                        </div>
                </div>
                <!-- 加载编辑器的容器 -->
                 <script id="result" name="result" type="text/plain">
                  ${a.result}
                 </script>
                 <!-- 配置文件 -->
                 <script type="text/javascript" src="${path}/ueditor/ueditor.config.js"></script>
                 <!-- 编辑器源码文件 -->
                 <script type="text/javascript" src="${path}/ueditor/ueditor.all.js"></script>
                 <!-- 实例化编辑器 -->
                 <script type="text/javascript">
                 var editor = new UE.ui.Editor({ initialFrameHeight:380,initialFrameWidth:910 });
                 editor.render("result");
                 </script>
                <p class="am-text-center">
                    <button type="submit" class="am-btn am-btn-primary">提交</button>
                    <a href="${path}/ec" class="am-btn am-btn-secondary">返回</a>
                </p>

     </form>
</div>
<jsp:include page="../footer.jsp"/>