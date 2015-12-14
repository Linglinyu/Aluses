define(['jquery', 'jquery.form', 'dialog'], function () {
    'use strict';

    var dlg;

    var form = $('#hideForm').submit(function () {
        $(this).ajaxSubmit({
            success: function (data) {
                if (data.resCode === 100) {
                    alert('操作完成！');
                    location.reload();
                }
            },
            error: function (resp) {
                if (resp.status === 400) {
                    alert(resp.responseJSON.resMsg);
                } else {
                    alert('处理失败！');
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

    $('#additem').click(function () {
        $('#id').val('');
        initForm();
    });

    $('.do-update').click(function () {
        var url = $(this).attr('data-action');
        $.get(url, function (user) {
            initForm(user.data);
        });
    });
    
    $('.do-delete').click(function () {
        var id = $(this).attr('data-id');
        var url = $(this).attr('data-action');
        var name = $(this).attr('data-name');
        if (confirm("确定要删除该项目吗？")) {
            $.ajax({
                'type': 'POST',
                'url': url,
                'success': function () {
                    $('#r_' + id).remove();
                    alert('删除完成！');
                },
                'error': function () {
                    alert('删除失败！');
                }
            });
        }
    });

    function initForm(obj) {
        if (typeof obj === 'object') {
            for (var i in obj) {
                if (obj.hasOwnProperty(i)) { //filter,只输出man的私有属性
                    $('[name="' + i + '"]').val(obj[i]);
                }
            }
        }
        dlg = dialog({
            'width': 500,
            'content': form.get(0)
        }).showModal();
    }
});