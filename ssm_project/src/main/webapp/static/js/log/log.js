
let vm = new Vue({
    el: '.page-content',
    data: {
            pageInfo: {
                pageNum: 1,
                pageSize: 5
            },
            params: {
                type: '',
                description:'',
            },
        // log:{},

    },
    methods: {
        selectPage: function () {
            axios({
                url: `manager/syslog/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
                method: 'post',
                data: this.params
            }).then(response => {
                console.log(response.data.obj);
                this.pageInfo = response.data.obj;
            }).catch(error => {
                console.log(error);
            })
        },
        _selectPage: function (pageNum, pageSize) {
            this.pageInfo.pageNum = pageNum;
            this.pageInfo.pageSize = pageSize;
            this.selectPage();
        },
        selectAll: function () {
            this.params = {
                status: '',
                startDate:'',
                endDate:'',
                officeId:''
            }
            this._selectPage(1, this.pageInfo.pageSize);
        },
        toDetail: function (id) {
            //借助当前窗口的layer对象传递值到子窗口
            layer.obj = id;
            // console.log(window);
            // console.log(layer.obj);
            /*弹出一个 iframe窗*/
            layer.open({
                type: 2,        //加载一个页面
                title: false,
                area: ['70%', '80%'],//设置宽高   px  或比例  占据父窗口
                content: 'manager/admin/detail',

            });
        },

    },

    created: function () {
        this.selectPage();
    }
});



