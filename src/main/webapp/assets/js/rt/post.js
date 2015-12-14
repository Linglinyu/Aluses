define(['jquery', 'jquery.form'], function ($) {
    'use strict';

    $("#form").submit(function(){
        $("button[type='submit']").html('数据提交中……').prop('disabled', true);
        $(this).ajaxSubmit({
            success: function (data) {
                if (data.resCode === 100) {
                    alert('修改完成！');
                    window.location.href="posts";
                }
                $("button[type='submit']").html('提交').prop('disabled', false);
            },
            error: function (resp) {
                if (resp.status === 400) {
                    alert(resp.responseJSON.resMsg);
                } else {
                    alert('处理失败！');
                }
                $("button[type='submit']").html('提交').prop('disabled', false);
            }
        });
        return false;
    });
});