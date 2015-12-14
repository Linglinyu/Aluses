define(['jquery','jquery.form'], function($){
    $('form').submit(function() {
        var account = $('#account').val();
        var password = $('#password').val();
        if (!account) {
            return alert('用户帐号不能为空！');
        }
        if (!password) {
            return alert('登录密码不能为空！');
        }
        $(this).ajaxSubmit({
            'success': function (result){
                if(result.resCode === 100) {
                    location.href = BASE_PATH + '/rt/posts';
                }
            },
            'error': function(resp) {
                alert(resp.responseJSON.resMsg);
            }
        });
        return false;
    });
});