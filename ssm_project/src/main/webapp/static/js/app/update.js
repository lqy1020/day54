

let vm=new Vue({
    el:'.page-content',
    data:{
        app:{}
    },
    methods:{
        doUpdate:function () {
            axios({
                url:'manager/app/doUpdate',
                method:'put',
                data:this.app
            }).then((response)=>{
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
            })
        },
        doCancel:function () {
            let index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }

    },
    created:function (){
        this.app=parent.layer.obj;
    }
})





















// let vm = new Vue({
//     el:'.page-content',
//     data:{
//         app:{}
//     },
//     methods:{
//         doUpdate:function () {
//             axios({
//                 url:`manager/app/doUpdate`,   //基于base的绝对路径请求
//                 method:'put',
//                 data:this.app
//             }).then((response)=>{//剪头函数  自动传递上下文的this
//                 // console.log(response.data);
//                 //成功后关闭当前子窗口  提示
//                 //注意：parent 是 JS 自带的全局对象，可用于操作父页面
//                 let index = parent.layer.getFrameIndex(window.name); //获取窗口索引
//                 parent.layer.success = response.data.success;
//                 if(response.data.success){//成功更新，关闭当前子窗口，并且通过父窗口提示
//                     parent.layer.close(index);
//                     parent.layer.msg(response.data.msg);
//
//                 }else{
//                     layer.msg(response.data.msg);//子窗口中提示
//                 }
//
//                 //失败后提示
//             }).catch(error=>{
//                 // console.log(error);
//                 layer.msg(error.message);
//             })
//         }
//     },
//     created:function () {  //data初始化后调用
//         //获取父窗口parent的layer对象
//         // console.log(parent);
//         this.app=parent.layer.obj;
//     }
// });