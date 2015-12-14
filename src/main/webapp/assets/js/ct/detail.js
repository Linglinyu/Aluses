define(['jquery', 'jquery.form', 'amazeui.datetimepicker'], function ($) {
    'use strict';

    var date = new Date();

    var date_option = {
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        language: 'zh',
        pickerPosition: 'bottom-left',
        startDate: date
    };
    $('#begin_date').datetimepicker(date_option);
    $('#end_date').datetimepicker(date_option);
    $('#last_date').datetimepicker(date_option);

    $("#form").submit(function(){
        $("button[type='submit']").html('数据提交中……').prop('disabled', true);
        $(this).ajaxSubmit({
            success: function (data) {
                if (data.resCode === 100) {
                    alert('操作完成！');
                    if($('#id').length === 0) {
                        location.href = $("#form").attr('action') + '?id=' + data['data'].id;
                    }
                }
                $("button[type='submit']").html('提交').prop('disabled', false);
            },
            error: function (resp) {
                if (resp.responseJSON) {
                    alert(resp.responseJSON.resMsg);
                } else {
                    alert('处理失败！');
                }
                $("button[type='submit']").html('提交').prop('disabled', false);
            }
        });
        return false;
    });

    $('#isonlinepay').change(function(){
        var pay = $(this).val();
        if(pay === '1') {
            $('#divMoney').show();
        } else {
            $('#divMoney').hide();
        }
    });
});