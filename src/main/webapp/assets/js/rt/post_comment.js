define(['jquery'], function ($) {
    'use strict';

    $('.do-delete').click(function () {
        var id = $(this).attr('data-id');
        var url = $(this).attr('data-action');
        if (confirm("确定要删除这条评论吗？")) {
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
        $.ajax({
            'type': 'POST',
            'url': url,
            'data': {'status': status},
            'success': function (result) {
                if(result.resCode === 100)
                    alert('审核操作完成！');
            },
            'error': function (resp) {
                if (resp.status === 400) {
                    alert(resp.responseJSON.resMsg);
                } else {
                    alert('处理失败！');
                }
            }
        });
    });
});