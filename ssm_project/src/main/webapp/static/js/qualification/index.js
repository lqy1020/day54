let vm = new Vue({
    el: '.page-content',
    data: {
        pageInfo: {
            pageNum: 1,
            pageSize: 5
        },
        condition: {
            type: "",
            check: ""

        }
    },
    methods: {
        selectPage: function () {
            axios({
                url: `manager/qualification/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
                method: 'post',
                data: this.condition
            }).then(response => {
                console.log(response.data);
                this.pageInfo = response.data.obj;
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        _selectPage: function (pageNum, pageSize) {
            this.pageInfo.pageNum = pageNum;
            this.pageInfo.pageSize = pageSize;
            this.selectPage();
        },
        selectAll: function () {
            this.condition = {
                type: "",
                check: ""
            }
            this._selectPage(1, this.pageInfo.pageSize);
        },
        toUpdate: function (qualification) {
            axios({
                url: `manager/qualification/getPath/${qualification.uploadUserId}`,
            }).then(response => {
                qualification.address = response.data.obj + "/" + qualification.address;

                //借助当前窗口的layer对象传递值到子窗口
                layer.obj = qualification;
                // console.log(window);
                // console.log(layer.obj);
                /*弹出一个 iframe窗*/
                layer.open({
                    type: 2,        //加载一个页面
                    title: false,
                    area: ['60%', '80%'],//设置宽高   px  或比例  占据父窗口
                    content: 'manager/qualification/toUpdate',
                    end: () => {
                        this.selectPage();
                    }
                });
            })
        }
    },
    created: function () {
        this.selectPage();
    }
})

