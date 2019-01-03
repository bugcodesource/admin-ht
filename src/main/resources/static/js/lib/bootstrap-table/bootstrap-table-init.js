$(document).ready(function(){

    /* ==========================================================================
        Tables
        ========================================================================== */

    var $table = $('#table'),
        $remove = $('#remove'),
        selections = [];

    function totalTextFormatter(data) {
        return 'total';
    }

    function totalNameFormatter(data) {
        return data.length;
    }

    function totalPriceFormatter(data) {
        var total = 0;
        $.each(data, function (i, row) {
            total += +(row.price.substring(1));
        });
        return '$' + total;
    }

    function statusFormatter(data, rowData, index) {
        var classBtn = '',
            classDropup = '',
            pageSize = 10;

        if (data === '冻结') classBtn = 'btn-danger';
        if (data === '正常') classBtn = 'btn-success';

        if (index >= pageSize / 2) {
            classDropup = 'dropup';
        }

        return	'<div class="dropdown dropdown-status ' +
            classDropup +
            ' ">' +
            '<button class="btn ' +
            classBtn +
            ' dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
            data +
            '</button>' +
            '<div class="dropdown-menu">' +
            '<a class="dropdown-item" href="#">冻结</a>' +
            '<a class="dropdown-item" href="#">正常</a>' +
            '</div></div>';
    }

    window.operateEvents = {
        'click #update': function (e, value, row, index) {
            alert("11111");
        },
        'click #role': function (e, value, row, index) {

        },
        'click #remove': function (e, value, row, index) {
            $table.bootstrapTable('remove', {
                field: 'id',
                values: [row.id]
            });
        }
    };

    function operateFormatter(value, row, index) {
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

    function getIdSelections() {
        return $.map($table.bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
    /*$table.bootstrapTable({
        url: '/userInfo/userList',
        uniqueId : "userId",
        method: 'get',
        contentType : "application/x-www-form-urlencoded",

        iconsPrefix: 'font-icon',
        icons: {
            paginationSwitchDown:'font-icon-arrow-square-down',
            paginationSwitchUp: 'font-icon-arrow-square-down up',
            refresh: 'font-icon-refresh',
            toggle: 'font-icon-list-square',
            columns: 'font-icon-list-rotate',
            export: 'font-icon-download',
            detailOpen: 'font-icon-plus',
            detailClose: 'font-icon-minus-1'
        },
        paginationPreText: '<i class="font-icon font-icon-arrow-left"></i>',
        paginationNextText: '<i class="font-icon font-icon-arrow-right"></i>',
        columns: [
            [
                {
                    field: 'state',
                    checkbox: true,
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: 'ID',
                    field: 'createTime',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    footerFormatter: totalTextFormatter
                },
                {
                    title: '用户名',
                    field: 'loginName',
                    align: 'center',
                    valign: 'middle',
                    footerFormatter: totalTextFormatter
                },
                {
                    title: '姓名',
                    field: 'realName',
                    align: 'center',
                    valign: 'middle',
                    footerFormatter: totalTextFormatter
                },
                {
                    title: '创建时间',
                    field: 'createTime',
                    align: 'center',
                    valign: 'middle',
                    footerFormatter: totalTextFormatter
                },
                {
                    title: '邮箱地址',
                    field: 'email',
                    align: 'center',
                    valign: 'middle',
                    footerFormatter: totalTextFormatter
                },

                {
                    field: 'telPhone',
                    title: '手机号',
                    align: 'center',
                    footerFormatter: totalPriceFormatter
                },
                {
                field: 'name',
                title: 'Status',
                editable: true,
                formatter: statusFormatter,
                footerFormatter: totalNameFormatter,
                align: 'center'
            },
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: operateEvents,
                    formatter: operateFormatter
                }
            ]
        ]
    });*/


    $table.bootstrapTable({
        url: '/userInfo/userList',
        uniqueId : "userId",
        responseHandler:function(res){
            return res;
        },
        queryParams: function (params) {
            return {
                offset: params.offset,
                limit: params.limit,
                name: $('#queryNameText').val(),
                age: $('#queryAgeText').val()
            }
        },
        columns: [{
            field: 'state',
            checkbox: true,
            align: 'center',
            valign: 'middle'
        },
            {
                title: 'ID',
                field: 'createTime',
                align: 'center',
                valign: 'middle',
                sortable: true,
                footerFormatter: totalTextFormatter
            },
            {
                title: '用户名',
                field: 'loginName',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '姓名',
                field: 'realName',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '创建时间',
                field: 'createTime',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '邮箱地址',
                field: 'email',
                align: 'center',
                valign: 'middle'
            },

            {
                field: 'telPhone',
                title: '手机号',
                align: 'center'
            },
            {
                field: 'status',
                title: 'Status',
                editable: true,
                formatter: statusFormatter,
                footerFormatter: totalNameFormatter,
                align: 'center'
            }, {
            formatter: function (value, rows, index) {
                return [
                    '<a href="javascript:modifyPer(' + rows.id + ",'" + rows.name + "'," + rows.age + ",'" + rows.address + "'" + ')">' +
                    '<i class="glyphicon glyphicon-pencil"></i>修改' +
                    '</a>',
                    '<a href="javascript:delPer(' + rows.id + ')">' +
                    '<i class="glyphicon glyphicon-remove"></i>删除' +
                    '</a>'
                ].join('');
            },
            title: '操作'
        }],
        striped: true,
        pagination: true,
        pageSize: 10,
        pageList: [5, 10, 25, 50, 100],
        rowStyle: function (row, index) {
            var ageClass = '';
            if (row.age < 18) {
                ageClass = 'text-danger';
            }
            return {classes: ageClass}
        },
    });


    $table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $remove.prop('disabled', !$table.bootstrapTable('getSelections').length);
        // save your data, here just save the current page
        selections = getIdSelections();
        // push or splice the selections if you want to save all data selections
    });

    $remove.click(function () {
        var ids = getIdSelections();
        $table.bootstrapTable('remove', {
            field: 'id',
            values: ids
        });
        $remove.prop('disabled', true);
    });

    $('#toolbar').find('select').change(function () {
        $table.bootstrapTable('refreshOptions', {
            exportDataType: $(this).val()
        });
    });

    /* ========================================================================== */
});
