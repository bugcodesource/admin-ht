/**
 * Created by zxk175 on 16/12/3.
 */

//刷新页面
function refresh() {
    $table.bootstrapTable('refresh');
}
//修改账号状态
function editStatus(userId, status) {
        $.ajax({
            url: '/user/updateStatus/'+userId+'/'+status,
            method: 'post',
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            //阻止深度序列化，向后台传送数组
            traditional: true,
            success: function (msg) {
                if (msg.success) {
                    layer.msg(msg.msg, {icon: 1, time: 1500});
                } else {
                    layer.msg(msg.msg, {icon: 2, time: 1500});
                }
                refresh();
            }
        })
}
function delData(userId) {
    $.ajax({
        url: '/user/delUser/'+userId,
        method: 'post',
        contentType: "application/json;charset=utf-8",
        dataType: 'json',
        //阻止深度序列化，向后台传送数组
        traditional: true,
        success: function (msg) {
            if (msg.success) {
                layer.msg(msg.msg, {icon: 1, time: 1500});
            } else {
                layer.msg(msg.msg, {icon: 2, time: 1500});
            }
            refresh();
        }
    })
}