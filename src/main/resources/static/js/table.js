/**
 * Created by zxk175 on 16/12/2.
 */

var $table = $('#users');

//bootstrapTable使用中文
$.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

//防止表头与表格不对齐
$(window).resize(function () {
    $table.bootstrapTable('resetView');
});

$(function () {
    //使用严格模式
    "use strict";

    tableInit();
    $('#users').bootstrapTable('hideLoading');
})

//初始化Table
function tableInit() {
    //先销毁表格
    $table.bootstrapTable('destroy');

    $table.bootstrapTable({
        //请求地址
        url: '/user/showUser',
        //请求方式
        method: 'post',
        //请求内容类型
        contentType: "application/x-www-form-urlencoded",
        //数据类型
        dataType: "json",
        //table高度，
        //如果没有设置height属性，
        //表格自动根据记录条数觉得表格高度
        height: '657',
        //是否显示行间隔色
        striped: true,
        //是否启用排序
        sortable: false,
        //排序方式
        sortOrder: "asc",
        //是否使用缓存
        cache: false,
        //指定工具栏
        toolbar: "#toolbar",
        //显示隐藏列
        showColumns: true,
        //显示刷新按钮
        showRefresh: true,
        //切换显示样式
        showToggle: false,
        //搜索框
        search: true,
        //导出数据
        showExport: true,
        //显示Table脚部
        showFooter: false,
        //是否显示详细视图
        cardView: false,
        //是否显示父子表
        detailView: false,
        //detail格式化
        detailFormatter: genderDetail,
        //是否显示分页
        pagination: true,
        //是否显示分页按钮
        showPaginationSwitch: true,
        //是否启用点击选中行
        clickToSelect: false,
        //最少要显示的列数
        minimumCountColumns: 2,
        //cell没有值时显示
        undefinedText: '-',
        //分页方式：client客户端分页，server服务端分页
        sidePagination: "server",
        //每页的记录行数
        pageSize: 10,
        //初始化加载第1页，默认第1页
        pageNumber: 1,
        uniqueId: 'userId',
        //可供选择的每页的行数
        pageList: "[10, 25, 50, 80, 100]",
        paginationFirstText: "首页",
        paginationPreText: "上一页",
        paginationNextText: "下一页",
        paginationLastText: "末页",
        buttonsClass: 'default',
        iconsPrefix: 'glyphicon',
        queryParams: queryParams,
        icons: {
            paginationSwitchDown: 'glyphicon-collapse-down icon-chevron-down',
            paginationSwitchUp: 'glyphicon-collapse-up icon-chevron-up',
            refresh: 'glyphicon-refresh icon-refresh',
            toggle: 'glyphicon-list-alt icon-list-alt',
            columns: 'glyphicon-th icon-th',
            detailOpen: 'glyphicon-plus icon-plus',
            detailClose: 'glyphicon-minus icon-minus',
            export: 'glyphicon glyphicon-export'
        }, columns: [{
            field: 'state',
            checkbox: true,
            align: 'center',
            valign: 'middle',
            height: '52'
        }, {
            title: '序号',
            field: 'userId',
            align: 'center',
            valign: 'middle',
            height: '52',
            formatter: genderIndex
        }, {
            title: '用户名',
            field: 'loginName',
            align: 'center',
            valign: 'middle',
            height: '52'
        },
            {
                title: '姓名',
                field: 'realName',
                align: 'center',
                valign: 'middle',
                height: '52'
            },
            {
                title: '创建时间',
                field: 'createTime',
                formatter: genderTime,
                align: 'center',
                valign: 'middle',
                height: '52'
            },
            {
                title: '邮箱地址',
                field: 'email',
                align: 'center',
                valign: 'middle',
                height: '52'
            },

            {
                field: 'telPhone',
                title: '手机号',
                align: 'center',
                height: '52'
            },
            {
                field: 'status',
                title: '状态',
                editable: true,
                events: operateEvents,
                formatter: statusFormatter,
                align: 'center',
                height: '52'
            },
            {
                title: '操作',
                field: 'operate',
                align: 'center',
                events: operateEvents,
                formatter: genderOpt,
                height: '52'
             }],
        responseHandler: function (res) {
            if (res.status == 0) {
                var obj = {
                    "total": res.total,
                    "rows": res.records,
                };
            } else {
                var obj = {
                    "total": 0,
                    "rows": [],
                }
            }
            return obj;
        }, onLoadSuccess: function () {
            //加载成功时执行
            console.log("加载成功!");
        }, onLoadError: function () {
            //加载失败时执行
            layer.msg("加载失败!", {icon: 2, time: 2000});
        }, formatLoadingMessage: function () {
            //正在加载
            return "请稍等，正在加载中...";
        }, formatNoMatches: function () {
            //没有匹配的结果
            return '无符合条件的记录';
        }
    })
}
//时间戳转换
function genderTime(value, row, index) {
    if (null == value || value == "") {
        return "-";
    } else {
        var date = new Date(value * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        var D = date.getDate() + ' ';
        var h = date.getHours() + ':';
        var m = date.getMinutes() + ':';
        var s = date.getSeconds();
        return Y+M+D+h+m+s;
    }

}

//生成详细视图
function genderDetail(index, row) {
    var html = [];
    $.each(row, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}

//生成序号
function genderIndex(value, row, index) {
    return index +1;
    //return row.userId;
}

//替换sex数据为文字
function genderSex(value, row, index) {
    if (value == null || value == undefined) {
        return "-";
    } else if (value == 1) {
        return "男";
    } else if (value == 0) {
        return "女";
    }
}

//替换状态数据为文字
/*function statusFormatter(value, row, index) {
    if (value == null || value == undefined) {
        return "-";
    } else if (value == 1) {
        return "冻结";
    } else if (value == 0) {
        return "正常";
    }
}*/
function statusFormatter(value, row, index) {
    var classBtn = '',
        classDropup = '',
        pageSize = 10;
    if (value == null || value == undefined) {
        return "-";
    }
    if (row.status == 1) {
        value = "冻结";
    } else if (row.status == 0) {
        value = "正常";
    }
    if (value === '冻结') classBtn = 'btn-danger';
    if (value === '正常') classBtn = 'btn-success';

    if (index >= pageSize / 2) {
        classDropup = 'dropup';
    }
    return [
        '<div class="dropdown dropdown-status ' + classDropup + ' ">' + '' +
        '<button class="btn ' + classBtn +
        ' dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">' +
    value +
        '</button>' +
        '<div class="dropdown-menu">' +
        '<a id="btn_dj" class="dropdown-item" href="javascript:void(0)">冻结</a>' +
        '<a id="btn_zc" class="dropdown-item" href="javascript:void(0)">正常</a>' +
        '</div></div>']
}

//自定义列内容

function genderOpt(value, row, index) {
    return [
        '<a id="update" class="like" href="javascript:void(0)" title="修改">',
        '<i class="glyphicon glyphicon-edit"></i>',
        '</a>  ',
        '<a id="role" class="like" href="#" title="设置">',
        '<i class="glyphicon glyphicon-cog"></i>',
        '</a>  ',
        '<a id="remove" class="remove" href="javascript:void(0)" title="删除">',
        '<i class="glyphicon glyphicon-remove"></i>',
        '</a>'
    ].join('');
}

//自定义列内容事件
window.operateEvents = {
    'click #update': function (e, value, row, index) {
        //editData(row);
        $("#new").modal("show");
    },
    'click #btn_dj': function (e, value, row, index) {
        if (row.status == 0) {
            editStatus(row.userId,row.status);
        } else {
            layer.msg("无效操作", {icon: 2, time: 1500});
        }
    },
    'click #btn_zc': function (e, value, row, index) {
        if (row.status == 1) {
            editStatus(row.userId,row.status);
        } else {
            layer.msg("无效操作", {icon: 2, time: 1500});
        }
    },
    'click #remove': function (e, value, row, index) {
        delData(row.userId);
    }
};

//查询条件与分页数据
function queryParams(params) {
    //排序方式
    params.order = "modify_time desc";
    //第几页
    params.nowPage = this.pageNumber;
    search:params.search
    return params;
}
