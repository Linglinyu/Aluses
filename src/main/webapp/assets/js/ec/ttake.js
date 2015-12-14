define(['jquery'], function ($) {
    'use strict';

    $('.do-audit').click(function () {
        var url = $(this).attr('data-action');
        var name = $(this).attr('data-name');
        if (confirm("确定要通过该审核吗？")) {
            $.ajax({
                'type': 'POST',
                'url': url,
                'success': function () {
                    alert('审核完成！');
                    location.reload();
                },
                'error': function () {
                    alert('操作失败！');
                }
            });
        }
    });
    
    $('.do-delete').click(function () {
        var id = $(this).attr('data-id');
        var name = $(this).attr('data-name');
        var url = $(this).attr('data-action');
        if (confirm("确定要删除该报名者吗？")) {
            $.ajax({
                'type': 'post',
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
    
    $('.do-export').click(function () {
    	var id = $(this).attr('data-id');
        var url = $(this).attr('data-action');
        if (confirm("确定要删除该报名者吗？")) {
            $.ajax({
                'type': 'post',
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
});