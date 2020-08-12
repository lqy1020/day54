


let vm=new Vue({
    el:'.page-content',
    data:{
        statute:{},
        ueditorConfig:{
            UEDITOR_HOME_URL:'static/ueditor/',
            serverUrl:'doUeditor',
            maximumWords:100000
        }
    },
    methods:{
        doUpdate:function () {
            axios({
                url:`manager/statute/doUpdate`,
                method:'put',
                data:this.statute
            }).then(response=>{
                let index=parent.layer.getFrameIndex(window.name);
                parent.layer.success = response.data.success;
                if (parent.layer.success){
                    parent.layer.close(index);
                    parent.layer.msg("修改成功");
                } else {
                    layer.msg("修改失败");
                }
            }).catch(error=>{
                console.log(error.message);
            })
        },
        cancel:function () {
            let index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }

    },
    created:function () {
        this.statute=parent.layer.obj;
    },
    mounted:function () {
        jeDate({
            dateCell: '#modifydate',   //设置日期插件存放的dom节点
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

