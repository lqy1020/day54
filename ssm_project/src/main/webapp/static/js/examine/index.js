let vm = new Vue({
    el: '.page-content',
    data: function () {
        return {
            pageInfo: {
                pageNum: 1,
                pageSize: 5
            },
            officeName:'全部',
            nodes:[],
            name:'',
            params: {
                type: '',
                name:'',
                officeId:''
            },
            setting: {
                data:{
                    simpleData:{
                        enable:true,
                        pIdKey:'parentId'
                    }
                },
                callback:{
                    onClick:this.onClick
                },
                view:{
                    fontCss:this.fontCss
                }
            },
        }
    },
    methods: {
        selectPage: function () {
            axios({
                url: `manager/examine/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
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
                type: ''
            }
            this._selectPage(1, this.pageInfo.pageSize);
        },

        initTree:function () {
            axios({
                url:'manager/office/select',
            }).then(response=>{
                console.log(response.data);
                this.nodes=response.data.obj;
                this.nodes[this.nodes.length]={id:0,name:'全部机构'};

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
                this.params.officeId=treeNode.id;
            }
        },
        search:function () {
            let zTreeObj=$.fn.zTree.getZTreeObj("pullDownTreeone");
            let fuzzyNodes=zTreeObj.getNodesByParamFuzzy("name",this.name,null);

            let nodes=zTreeObj.getNodes();
            nodes=zTreeObj.transformToArray(nodes);
            for (let i in nodes){
                nodes[i].highLight=false;
                zTreeObj.updateNode(nodes[i]);
            }

            for(let i in fuzzyNodes){
                fuzzyNodes[i].highLight=true;
                zTreeObj.updateNode(fuzzyNodes[i]);
            }
        },
        fontCss: function (treeId,treeNode) {
            //返回json格式   对字体进行设置样式
            // return treeNode.id===106?{color:'red'}:{color:'black'}
            //高亮属性的节点显示红色
            return treeNode.highLight?{color:'red'}:{color:'black'}
        },
        stop:function (event) {
            if ( event.target.id==="pullDownTreeone_1_switch") {
                event.stopPropagation();
            }

        }

    },

    mounted:function () {//当vue的代理el节点挂载上去后自动执行
        this.initTree();
    },
    created: function () {
        // this.selectAll()
        this.selectPage();
    }
});




