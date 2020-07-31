
let vm = new Vue({
    el: '.page-content',
    data:{
        pageInfo:{
            pageNum:1,
            pageSize:5
        },
       type:'',
        statute:{},
        active:true,
        ueditorConfig:{
            UEDITOR_HOME_URL:'static/ueditor/',
            serverUrl:'doUeditor',
            maximumWords:100000
        }
    },
    methods: {
        selectPage: function () {
            axios({
                url: `manager/statute/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
                method: 'put',
                params: {type: this.type}
            }).then(response => {
                this.pageInfo = response.data.obj;
                console.log(response.data);
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        _selectPage: function (pageNum, pageSize) {

            this.pageInfo.pageNum = pageNum;
            this.pageInfo.pageSize = pageSize;
            this.selectPage();
        },
        selectAll: function () {
            this.type = '';
            this._selectPage(1, this.pageInfo.pageSize);
        },
        toUpdate: function (statute) {
            layer.obj = statute;
            layer.open({
                type: 2,        //加载一个页面
                title: false,
                area: ['70%', '80%'],//设置宽高   px  或比例  占据父窗口
                content: 'manager/statute/toUpdate',
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
                    let statute = {id: id, delFlag: 1};
                    axios({
                        url: `manager/statute/doUpdate`,
                        method: 'put',
                        data: statute
                    }).then(response => {
                        console.log(response.data);
                        if (response.data.success) {
                            this.selectPage();
                        }
                    })
                }
            })
        },
        changeActive: function () {
            this.active = !this.active;
        },
        save: function () {
            axios({
                url: `manager/statute/doInsert`,
                method: 'post',
                data: this.statute
            }).then(response => {
                console.log(response.data);
                if (response.data.success) {
                    layer.msg(response.data.msg);
                    this.statute = {};
                    this.selectPage();
                    this.changeActive();
                } else {
                    layer.msg(response.data.msg);

                }
            }).catch(error => {
                console.log(error.message);
            })

        },
    },
    created:function () {
        this.selectPage();
    },
    mounted:function () {
        jeDate({
            dateCell: '#indate',   //设置日期插件存放的dom节点
            format: 'YYYY-MM-DD',       //设置日期显示格式
            zIndex: 999999999,           //设置图层
            choosefun:(val)=> {//选中日期后回调
                //动态获取jeDate赋值后的日期，给vue的statute对象的pubDate赋值
                // console.log(val)
                this.statute.pubDate=val;
            }
        });
    },
    components:{
        VueUeditorWrap
    }
})


