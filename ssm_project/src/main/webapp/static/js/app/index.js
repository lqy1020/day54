let vm = new Vue({
    el: '.page-content',
    data: {
        pageInfo: {
            pageNum: 1,
            pageSize: 5
        },
        active:true,
        app:{}
    },
    methods: {
        selectPage: function () {
            axios({
                url: `manager/app/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`
            }).then((response) => {
                this.pageInfo = response.data.obj;
                console.log(response.data);
            }).catch((error) => {
                console.log(error);
            })
        },
        _selectPage: function (pageNum, pageSize) {
            this.pageInfo.pageNum = pageNum;
            this.pageInfo.pageSize = pageSize;
            this.selectPage();
        },

        toUpdate: function (app) {
            //借助当前窗口的layer对象传递值到子窗口
            layer.obj = app;
            // console.log(window);
            // console.log(layer.obj);
            /*弹出一个 iframe窗*/
            layer.open({
                type: 2,        //加载一个页面
                title: false,
                area: ['50%', '60%'],//设置宽高   px  或比例  占据父窗口
                content: 'manager/app/toUpdate',
                end: () => {
                    if (layer.success == undefined || !layer.success) {
                        this.selectPage();
                    }
                }
            });
        },
        doDelete: function (id) {
            layer.msg("确认删除吗?", {
                time: 0 //不自动关闭
                , btn: ['是', '否']   //设置按钮项内容
                , yes: (index) => {//点击第一个成功按钮的回调函数  index是窗口的唯一标记 自动传入
                    layer.close(index);    //关闭当前窗口
                    let app={id:id,delFlag:1};
                    axios({
                        url:'manager/app/doUpdate',
                        method:'put',
                        data:app
                    }).then((response)=>{
                        console.log(response.data);
                        if (response.data.success){
                            this.selectPage();
                        }
                    }).catch(function (error) {
                        console.log(error);
                    })


                }
            })
        },
        changeActive:function () {
            this.active=!this.active;
        },

        save:function () {
            axios({
                url:'manager/app/doInsert',
                method: 'post',
                data:this.app
            }).then((response)=>{
                if (response.data.success){
                    layer.msg(response.data.msg);
                    this.app={};
                    this.changeActive();
                    this.selectPage();

                }else {
                    layer.msg("保存失败");
                }
            });
        }
    },

    created: function () {
        // this.selectAll()
        this.selectPage();
    }
});


