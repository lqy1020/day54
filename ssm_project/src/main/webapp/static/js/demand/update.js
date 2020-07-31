
let vm=new Vue({
    el:'.page-content',
    data:{
        demand:{
        }
    },
    methods:{
        doUpdate:function (demand) {
            axios({
                url:`manager/demand/doUpdate`,
                method:'put',
                data:this.demand
            }).then(response=>{
                let index=parent.layer.getFrameIndex(window.name);
                if (response.data.success){
                    parent.layer.close(index);
                    parent.layer.msg("更新成功");
                } else {
                    parent.layer.msg("更新失败");
                }
            })
        },
        doCancel:function () {
            let index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    },
    created:function () {
        this.demand=parent.layer.obj;
    }
});


