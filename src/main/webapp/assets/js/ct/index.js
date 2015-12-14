define(['jquery', 'jquery.form', 'dialog'], function ($) {
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
        form.attr('action', url);
        $.get(url, function (user) {
            initForm(user.data);
        });
    });

    function initForm(obj) {
        if (typeof obj === 'object') {
            for (var i in obj) {
                if (obj.hasOwnProperty(i)) { //filter,只输出man的私有属性
                    $('[name="' + i + '"]').val(obj[i]);
                }
                $('#grouppic').empty().append('<img src="' + obj['grouppic'] + '" style="max-width: 200px; max-height: 150px;">');
            }
        }
        dlg = dialog({
            'width': 720,
            'content': $('#hideDiv').get(0)
        }).showModal();
    }

    $('.do-delete').click(function () {
        var id = $(this).attr('data-id');
        var name = $(this).attr('data-name');
        var url = $(this).attr('data-action');
        if (confirm("确定要删除该圈子吗？")) {
            $.ajax({
                'type': 'POST',
                'url': url,
                'data': {'status': 3},
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

    $('.do-status').change(function () {
        var id = $(this).attr('data-id');
        var url = $(this).attr('data-action');
        var status = $(this).val();
        var word = $(this).find(':selected').html();
        $.ajax({
            'type': 'POST',
            'url': url,
            'data': {'status': status},
            'success': function () {
                alert('审核状态修改成功：' + word);
            },
            'error': function () {
                alert('修改失败！');
            }
        });
    });

    $('.do-audit').change(function () {
        var id = $(this).attr('data-id');
        var url = $(this).attr('data-action');
        var status = $(this).val();
        var word = $(this).find(':selected').html();
        $.ajax({
            'type': 'POST',
            'url': url,
            'data': {'audit': status},
            'success': function () {
                alert('新成员申请审核状态修改成功：' + word + ' 审核');
            },
            'error': function () {
                alert('修改失败！');
            }
        });
    });
});