let vm = new Vue({
    el: '.page-content',
    data: {
        area: {
            parentName:'',
            parentId:'',
            name:'',
            code:'',
            type:'',
            icon:'',
            remarks:'',
            parentIds:'',

        }
    },
    methods: {
        doInsert: function () {
            axios({
                url: 'manager/area/doInsert',
                method: 'post',
                data: this.area
            }).then((response) => {
                let index = parent.layer.getFrameIndex(window.name);
                parent.layer.success = response.data.success;
                if (parent.layer.success) {
                    parent.layer.close(index);
                    parent.layer.msg("插入成功")
                } else {
                    parent.layer.msg("插入失败");
                }

            }).catch(error => {
                console.log(error.message);
            })
        },
        doCancel: function () {
            let index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        toModules: function () {
            layer.open({
                type: 2,
                tit: false,
                area: ['100%', '100%'],
                content: `manager/area/toModules`,
                end: () => {
                    if (layer.icon != undefined && layer.icon != '') {
                        this.area.icon = layer.icon;
                    }
                }
            });
        },
        toSelect: function () {
            layer.open({
                type: 2,
                title: false,
                area: ['100%', '100%'],
                content: `manager/area/toSelect`,
                end: () => {
                    if (layer.parentName != undefined && layer.parentName != '') {
                        this.area.parentName=layer.parentName;
                        this.area.parentId=layer.parentId;
                        this.area.parentIds=layer.parentIds;
                        console.log(this.area.parentIds);
                    }
                }

            })
        },
        doCancel:function () {
            let index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }

    },

})