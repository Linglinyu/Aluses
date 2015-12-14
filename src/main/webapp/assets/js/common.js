define(['jquery', 'jquery.form', 'dialog'], function($){
    var sh = $('.admin-sidebar').height();
    var ch = $('.admin-content').height();
    if(ch < sh){
        $('.admin-content').css('minHeight', sh + 'px');
    }

    var dlg;
    var form = $('#pwdForm').submit(function () {
        $(this).ajaxSubmit({
            success: function (data) {
                if (data.resCode === 100) {
                    alert('密码修改完成！');
                    if (dlg) {
                        dlg.close().remove();
                    }
                }
            },
            error: function (resp) {
                if (resp.status === 400) {
                    alert(resp.responseJSON.resMsg);
                } else {
                    alert('修改失败！');
                }
            }
        });
        return false;
    });

    form.find('[type="reset"]').click(function () {
        if (dlg) {
            dlg.close().remove();
        }
    });

    $('#repwd').click(function(){
        dlg = dialog({
            'width': 500,
            'content': form.get(0),
            'fixed': true
        }).showModal();
    });
});