

let vm=new Vue({
    el: '.page-content',
   data:function () {
        return{
            name:'',
            nodes:[],
            setting:{
                data:{
                    simpleData:{
                        enable:true,
                        pIdKey:'parentId'
                    }
                },
                callback:{
                    onClick:this.onClick
                }
            }
        }
   },
    methods:{
        initTree:function () {
            axios({
                url:`manager/office/select`
            }).then(response=>{
                this.nodes=response.data.obj;
                this.nodes[this.nodes.length]={id:0,name:'机构列表',open:true};
                let zTreeObj=$.fn.zTree.init($('#select-tree'),this.setting,this.nodes);
            }).catch(error=>{
                layer.msg(error.message);
            });
        },
        onClick:function (event,treeId,treeNode) {
            if (treeNode.id!=0){
                this.name=treeNode.name
                console.log(this.name);
                parent.layer.parentName=this.name;
                parent.layer.parentId=treeNode.id;
                let index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            } 

        },
        choose:function () {
            
        }
    },
    mounted:function () {//当vue的代理el节点挂载上去后自动执行
        this.initTree();
    }
})