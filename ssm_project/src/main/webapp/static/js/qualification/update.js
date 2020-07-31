let vm = new Vue({
    el: '.page-content',
    data: {
        qualification: {}
    },
    methods: {
        doUpdate: function (check) {
            this.qualification.check = check;
            this.qualification.address = null;
            axios({
                url:'manager/qualification/doUpdate',
                method:'put',
                data:this.qualification
            }).then(response=>{
                let index=parent.layer.getFrameIndex(window.name);
                parent.layer.success=response.data.success;
                if (parent.layer.success){
                    parent.layer.close(index);
                    parent.layer.msg("更新成功")
                }else {
                    parent.layer.msg("更新失败");
                }
            }).catch(error=>{
                console.log(error.message);
            });
        }

    },
    created:function () {
        this.qualification=parent.layer.obj;
    }
})