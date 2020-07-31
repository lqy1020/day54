//
// function formatTime(number,format) {
//     var formateArr  = ['Y','M','D','h','m','s'];
//     var returnArr   = [];
//
//     var date = new Date(number);
//     returnArr.push(date.getFullYear())//在字符数组的末尾插入一个元素
//     returnArr.push(formatNumber(date.getMonth() + 1));
//     returnArr.push(formatNumber(date.getDate()));
//
//     returnArr.push(formatNumber(date.getHours()));
//     returnArr.push(formatNumber(date.getMinutes()));
//     returnArr.push(formatNumber(date.getSeconds()));
//
//     for (var i in returnArr)
//     {
//         format = format.replace(formateArr[i], returnArr[i]);
//     }
//     return format;
// }

//数据转化 动态添加0
function formatNumber(n) {
    n = n.toString();
    return n[1] ? n : '0' + n
}



let vm = new Vue({
    el: '.page-content',
    data: {
        workOrder: {}
    },
    methods: {
        selectDetail: function (id) {
            axios({
                url: 'manager/admin/work/selectById',
                method: 'put',
                params: {id: id}
            }).then((response) => {
                this.workOrder = response.data.obj;
                console.log(response.data.obj);
                // console.log(this.workOrder.updateDate);
                // console.log(new Date(this.workOrder.updateDate));
                // let date = new Date(this.workOrder.updateDate)
                // console.log(date.toLocaleDateString());
                // // this.workOrder.transfers.updateDate = date.toLocaleDateString();
                // console.log(formatTime(this.workOrder.updateDate, "Y-M-D"));


            }).catch(error => {
                console.log(error.message);
            })
        }
    },
    created: function () {
        this.selectDetail(parent.layer.obj);
    }
    //声明局部过滤器
    // 1.filters属性中声明过滤器函数
    // 2.在对应插值表达式或 v-bind表达式中通过 |过滤器函数(参数1，参数2...)
    // 3.需要过滤处理的函数不用指定传参  {{ msg | filter(参数1)}}  ->  function(msg,参数1)
    // filters:{
    //     formatTime: function (number,format) {
    //         var formateArr  = ['Y','M','D','h','m','s'];
    //         var returnArr   = [];
    //
    //         var date = new Date(number);
    //         returnArr.push(date.getFullYear())//在字符数组的末尾插入一个元素
    //         returnArr.push(formatNumber(date.getMonth() + 1));
    //         returnArr.push(formatNumber(date.getDate()));
    //
    //         returnArr.push(formatNumber(date.getHours()));
    //         returnArr.push(formatNumber(date.getMinutes()));
    //         returnArr.push(formatNumber(date.getSeconds()));
    //
    //         for (var i in returnArr)
    //         {
    //             format = format.replace(formateArr[i], returnArr[i]);
    //         }
    //         return format;
    //     }
    // }
})

