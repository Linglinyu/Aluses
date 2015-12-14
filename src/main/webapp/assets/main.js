(function(){
    //var path = location.pathname.replace(BASE_PATH, '');
    var path = location.pathname.replace(BASE_PATH_ROOT, '');

    var page = null;

    var router = {
        '/' : '/home/index',
        '/admin': '/admin/admins',
        '/rt/post/comment': '/rt/post_comment',
        '/rt/post/reportpost': '/rt/post_reportpost',
        '/pj': '/admin/pj',
        '/ct': '/ct/index',
        '/ec': '/ec/index',
        '/rp': '/rp/index',
        '/setting': '/admin/setting'
    };

    var pages = [
        '/rt/tags',
        '/rt/posts',
        '/rt/post',
        '/ct/members',
        '/ct/chats',
        '/ct/reportpost',
        '/ct/tags',
        '/ct/detail',
        '/ec/themes',
        '/ec/article',
//        '/ec/result',
        '/ec/ttake',
        '/rp/tags',
        '/rp/posts'
    ];

    if(router[path]) {
        page = 'js' + router[path];
    } else if(pages.indexOf(path) > -1){
        page = 'js' + path;
    }

    require.config({
        deps: ['jquery', 'common', 'amazeui', page],
        shim: {
            common: {
                deps: ['jquery']
            },
            dialog: {
                deps: [
                    'jquery',
                    'css!vendor/artDialog/ui-dialog'
                ]
            },
            amazeui: {
                deps: ['jquery']
            },
            'amazeui.datetimepicker': {
                deps: ['jquery','css!vendor/amazeui/css/amazeui.datetimepicker'],
                init: function(jq) {
                    jq.fn.datetimepicker.dates['zh'] = {
                        days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
                        daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
                        daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
                        months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                        monthsShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
                        today: "今天",
                        suffix: [],
                        meridiem: ["上午", "下午"]
                    };
                    return jq;
                }
            },
            'dialog-plus': {
                deps: ['dialog']
            }
        },
        paths: {
            'common': 'js/common',
            'css': 'vendor/css.min',
            'jquery': 'jquery-2.1.1.min',
            'jquery.form': 'vendor/jquery.form.min',
            'jscolor': 'vendor/jscolor/jscolor',
            'amazeui': 'vendor/amazeui/js/amazeui.min',
            'amazeui.datetimepicker': 'vendor/amazeui/js/amazeui.datetimepicker.min',
            'dialog': 'vendor/artDialog/dialog-min',
            'dialog-plus': 'vendor/artDialog/dialog-plus-min'
        }
    });
})();