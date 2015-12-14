/**
 * Created by dhc on 15/7/18.
 */
define(['jquery'], function ($) {
    'use strict';

    $('.do-delete').click(function () {
        var id = $(this).attr('data-id');
        var url = $(this).attr('data-action');
        if (confirm("确定要删除该圈子成员吗？")) {
            $.ajax({
                'type': 'POST',
                'url': url,
                'data': {'status': 4},
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
});