let vm = new Vue({
    el: '.page-content',
    data: {
        area: {}
    },
    methods: {
        doUpdate: function () {
            axios({
                url: 'manager/area/doUpdate',
                method: 'put',
                data: this.area
            }).then((response) => {
                let index = parent.layer.getFrameIndex(window.name);
                parent.layer.success = response.data.success;
                if (parent.layer.success) {
                    parent.layer.close(index);
                    parent.layer.msg("更新成功")
                } else {
                    parent.layer.msg("更新失败");
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
    created: function () {
        this.area = parent.layer.obj;
        this.area.oldParentIds=this.area.parentIds;
    }
})