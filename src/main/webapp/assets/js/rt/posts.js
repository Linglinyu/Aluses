define(['jquery'], function ($) {
    'use strict';

    $('.do-delete').click(function () {
        var id = $(this).attr('data-id');
        var url = $(this).attr('data-action');
        if (confirm("确定要删除该帖子吗？")) {
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
});