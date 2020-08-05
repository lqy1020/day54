
let vm = new Vue({
    el: '.page-content',
    data: function () {
        return {
            nodes:[],
            oid:'',
            yxUsers:{},
            yxIds:[],
            dxUser:{},
            dxIds:[],
            showDelete:false,
            showInsert:false,
            role:{},
            setting: {//树配置对象  用于设置树结构全局配置
                data:{
                    simpleData:{//简单数据格式设置
                        enable:true,//开启简单数据格式   自动将一维数组组装成父子结构
                        pIdKey:'parentId' //默认的父id属性名为pId
                    }
                },
                callback:{//回调函数设置，用于设置一些事件回调函数
                    onClick:this.onClick
                },
                view:{//显示回调，当节点显示的时候，会触发回调
                    fontCss:this.fontCss
                }
            },
        }
    },
    methods: {
        initTree:function () {
            axios({
                url:'manager/office/select',
            }).then(response=>{
                this.nodes=response.data.obj;
                //动态添加一个父节点
                // this.nodes[this.nodes.length]={id:0,name:'全部机构'};

                /*init(obj,setting,nodes)初始化树的api
                 * obj是需要挂载树的jq节点对象
                 * setting是树配置对象
                 * nodes：树的显示节点信息数组
                 * */
                //必须保证响应返回对nodes赋值后，再初始化树
                let zTreeObj=$.fn.zTree.init($('#treeOffice'),this.setting,this.nodes)
            }).catch(error=>{
                layer.msg(error.message);
            });
        },

        checkYxUsers:function (id) {
            for (let i in this.yxUsers) {
                if (this.yxUsers[i].id==id){
                    this.yxUsers[i].show=!this.yxUsers[i].show;
                    if (this.yxUsers[i].show) {
                        this.showDelete=true;
                        this.yxIds.push(this.yxUsers[i].id);
                    }else {
                        for (let j in this.yxIds){
                            if (this.yxIds[j]==id){
                                this.yxIds.splice(j,1);
                            }
                        }
                    }
                }
            }
            if (this.yxIds.length<=0){
                this.showDelete=false;
            }
        },

        selectRole:function(){
            axios({
                url:'manager/sysuser/selectByRid',
                method: 'post',
                params:{rid:this.role.id}
            }).then(response=>{
                this.yxUsers=response.data.obj;

            })
        },
        deleteBatch:function () {
            axios({
                url:'manager/role/deleteBatch',
                method:'post',
                params:{rid:this.role.id,ids:this.yxIds+''}
            }).then(response=>{
                if (response.data.success){
                    this.selectRole();
                    this.showDelete=false;
                    this.selectNoRole();
                    this.showInsert=false;
                }
            }).catch(error=>{
                layer.msg(error.message);
            })
        },
        selectNoRole:function(){
            axios({
                url:`manager/sysuser/selectNoRole`,
                method:'post',
                params:{oid:this.oid,rid:this.role.id}
            }).then(response=>{
                this.dxUser=response.data.obj;
                for (let i in this.dxUser){
                    this.dxUser[i].show=false;
                } 
            })

        },
        checkDxUsers:function (id) {
            for (let i in this.dxUser) {
                if (this.dxUser[i].id==id){
                    this.dxUser[i].show=!this.dxUser[i].show;
                    if (this.dxUser[i].show) {
                        this.showInsert=true;
                        this.dxIds.push(this.dxUser[i].id);
                    }else {
                        for (let j in this.dxIds){
                            if (this.dxIds[j]==id){
                                this.dxIds.splice(j,1);
                            }
                        }
                    }
                }
            }
            if (this.dxIds.length<=0){
                this.showInsert=false;
            }
        },
        insertBatch:function(){
            axios({
                url:`manager/role/insertBatch`,
                method:'post',
                params:{rid:this.role.id,cids:this.dxIds+''}
            }).then(response=>{
                layer.msg(response.data.msg);
                this.selectNoRole();
                this.showInsert=false;
                this.selectRole();
                this.showDelete=false;
            })
        },
        onClick:function(event,treeId,treeNode){//点击事件触发的函数
            // console.log(event);//事件对象
            // console.log(treeId);//树id ： treeOffice
            // console.log(treeNode);//当前点击的节点对象
            // this.officeName=treeNode.name;
            let zTreeObj = $.fn.zTree.getZTreeObj("treeOffice");  //根据树id获取树对象
            //获取所有树节点，多维结构
            let nodes = zTreeObj.getNodes();
            //转换成一维结构
            nodes = zTreeObj.transformToArray(nodes);

            //还原所有高亮属性为false
            for(let i in nodes){
                nodes[i].highLight=false;
                zTreeObj.updateNode(nodes[i]);
            }
            treeNode.highLight=true;//设置高亮
            zTreeObj.updateNode(treeNode);//更新节点
            this.oid=treeNode.id;
            this.selectNoRole();//查询出待选人员
        },
        fontCss: function (treeId,treeNode) {
            //返回json格式   对字体进行设置样式
            // return treeNode.id===106?{color:'red'}:{color:'black'}
            //高亮属性的节点显示红色
            return treeNode.highLight?{color:'red'}:{color:'black'}
        }


    },

    mounted:function () {//当vue的代理el节点挂载上去后自动执行
        this.initTree();
    },
    created: function () {
        this.role.id=parent.layer.roleId;
        this.role.name=parent.layer.roleName;
        this.yxUsers=parent.layer.users;
    }
});





