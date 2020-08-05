

let vm = new Vue({
    el: '.page-content',
    data: function () {
        return {
            pageInfo: {
                pageNum: 1,
                pageSize: 5
            },
            nodes:[],
            name:'',//查询框的查询名
            officeName:'全部',//显示的公司名
            params: {
                dataScope: '',
                oid:'',
                name:''
            },
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
        selectPage: function () {
            axios({
                url: `manager/role/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
                method: 'post',
                data: this.params
            }).then((response) => {
                this.pageInfo = response.data.obj;
            }).catch((error) => {
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
        toUsers:function(id,name){
            layer.roleId = id;
            layer.roleName=name;
            axios({
                url:'manager/sysuser/selectByRid',
                method: 'post',
                params:{rid:layer.roleId}
            }).then(response=>{
                layer.users=response.data.obj;
                layer.open({
                    type:2,
                    title: false,
                    area: ['90%','90%'],
                    content: `manager/role/toUser`,
                    // end:()=>{
                    // }
                });
            })

        },
        toUpdate:function(role) {
            layer.obj = role;
            layer.open({
                type:2,
                title:false,
                area:['80%','80%'],
                content:`manager/role/toUpdate`,
                end:()=>{
                    if (layer.success == undefined || !layer.success) {
                        this.selectPage();
                    }
                },
            })
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

        initTree:function () {
            axios({
                url:'manager/office/select',
            }).then(response=>{
                this.nodes=response.data.obj;
                //动态添加一个父节点
                this.nodes[this.nodes.length]={id:0,name:'全部机构'};

                /*init(obj,setting,nodes)初始化树的api
                 * obj是需要挂载树的jq节点对象
                 * setting是树配置对象
                 * nodes：树的显示节点信息数组
                 * */
                //必须保证响应返回对nodes赋值后，再初始化树
                let zTreeObj=$.fn.zTree.init($('#pullDownTreeone'),this.setting,this.nodes)
            }).catch(error=>{
                layer.msg(error.message);
            });
        },
        onClick:function(event,treeId,treeNode){//点击事件触发的函数
            console.log(event);//事件对象
            console.log(treeId);//树id ： pullDownTreeone
            console.log(treeNode);//当前点击的节点对象
            this.officeName=treeNode.name;
            if(treeNode.id!=0){//选中全部就不需要给params.officeId赋值
                this.params.oid=treeNode.id;
            }else {
                this.params.oid='';
            }
        },
        stop:function(event){//解决事件冒泡问题
            console.log(event.target);
            if(event.target.id==='pullDownTreeone_1_switch'){
                event.stopPropagation();
            }
        },
        search:function () {
            // console.log(111)
            //根据条件查询所有模糊匹配的节点名
            let zTreeObj = $.fn.zTree.getZTreeObj("pullDownTreeone");  //根据树id获取树对象
            //getNodesByParamFuzzy(key,value,parentNode) 根据父节点parentNode和指定属性名key查找模糊匹配
            //属性值value的节点
            let fuzzyNodes = zTreeObj.getNodesByParamFuzzy("name",this.name,null);
            //获取所有树节点，多维结构
            let nodes = zTreeObj.getNodes();
            //转换成一维结构
            nodes = zTreeObj.transformToArray(nodes);

            //还原所有高亮属性为false
            for(let i in nodes){
                nodes[i].highLight=false;
                zTreeObj.updateNode(nodes[i]);
            }

            //从树节点中查找对应节点，设置高亮显示属性为true
            for (let i in fuzzyNodes) {
                /*for(let j in nodes){
                        if(fuzzyNodes[i].id===nodes[j].id){
                        nodes[j].highLight=true;//设置高亮
                        zTreeObj.updateNode(nodes[j]);//更新节点对应的dom 会重新调用fontCss
                        break;
                    }
                }*/
                fuzzyNodes[i].highLight=true;
                zTreeObj.updateNode(fuzzyNodes[i]);
            }



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

        this.selectPage();
    }
});





