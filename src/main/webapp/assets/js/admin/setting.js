define(['jquery'], function ($) {
    'use strict';

    $('select').change(function(){
        var url = $(this).attr("data-action");
        var name = $(this).attr("data-name");
        var value = $(this).val();

        $.ajax({
            'url': url,
            'type': 'post',
            'data': {'value': value},
            'success': function(){
                alert("成功修改【" + name + "】的状态为：" + (value === '1' ? '需要审核' : '不需要审核'));
            },
            'error': function(){
                alert('修改失败！');
                location.reload();
            }
        });
    });
});