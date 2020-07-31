
let vm=new Vue({
    el:'.page-content',
    data:{
        pageInfo:{
            pageNum:1,
            pageSize:5
        }
    },
    methods:{
     selectPage:function () {
         axios({
             url:`manager/demand/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
         }).then((response)=>{
             console.log(response.data);
             this.pageInfo=response.data.obj;
         })
     },
        _selectPage(pageNum,pageSize){
         this.pageInfo.pageNum=pageNum;
         this.pageInfo.pageSize=pageSize;
         this.selectPage();
        },
        toUpdate:function (demand) {
            layer.obj=demand;
            layer.open({
                type: 2,        //加载一个页面
                title: false,
                area: ['50%', '60%'],//设置宽高   px  或比例  占据父窗口
                content: 'manager/demand/toUpdate',
                end: () => {
                    if (layer.success == undefined || !layer.success) {
                        this.selectPage();
                    }
                }
            });
        },
        toDetail:function (demand) {
            layer.obj=demand;
            layer.open({
                type: 2,        //加载一个页面
                title: false,
                area: ['50%', '60%'],//设置宽高   px  或比例  占据父窗口
                content: 'manager/demand/toDetail',
                // end: () => {
                //     if (layer.success == undefined || !layer.success) {
                //         this.selectPage();
                //     }
                // }
            });
        }

    },
    created:function () {
        this.selectPage();
    }
})

