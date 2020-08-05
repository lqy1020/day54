
let vm = new Vue({
    el: '.page-content',
    data: function () {
        return {
            pageInfo: {
                pageNum: 1,
                pageSize: 5
            },
            nodes:[],
            name:'',
            pId:'',
            // params: {
            //     status: '',
            //     startDate:'',
            //     endDate:'',
            //     officeId:''
            // },
            setting: {//树配置对象  用于设置树结构全局配置
                data:{
                    simpleData:{//简单数据格式设置
                        enable:true,//开启简单数据格式   自动将一维数组组装成父子结构
                        pIdKey:'parentId' //默认的父id属性名为pId
                    }
                },
                callback:{//回调函数设置，用于设置一些事件回调函数
                    onClick:this.onClick,
                    beforeEditName:this.beforeEditName,
                    beforeRemove:this.beforeRemove
                },
                edit:{
                    enable:true,
                    renameTitle:'修改',
                    removeTitle:'删除',
                },
                view:{
                    addHoverDom:this.addHoverDom, //鼠标移动到树节点上触发
                    removeHoverDom:this.removeHoverDom //鼠标离开节点触发
                },
            }
            ,
        }
    },
    methods: {
        selectPage: function () {

            if (this.pId!=''){
                axios({
                    url: `manager/area/selectByPid/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
                    params:{id:this.pId}
                }).then((response) => {
                    this.pageInfo = response.data.obj;
                }).catch((error) => {
                    console.log(error);
                })
            } else {
                axios({
                    url: `manager/area/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
                    params:{name:this.name}
                }).then((response) => {
                    this.pageInfo = response.data.obj;
                }).catch((error) => {
                    console.log(error);
                })
            }


        },
        _selectPage: function (pageNum, pageSize) {
            this.pageInfo.pageNum = pageNum;
            this.pageInfo.pageSize = pageSize;
            this.selectPage();
        },
        selectAll: function () {
            this.name='',
            this.pId='',
            this._selectPage(1, this.pageInfo.pageSize);
        },
        toUpdate:function(area){
            layer.obj=area;
            layer.open({
                type:2,
                tit: false,
                area: ['60%','80%'],
                content: `manager/area/toUpdate`,
                end:()=>{
                    if (layer.success == undefined || !layer.success) {
                        this.selectPage();
                    }
                }
            });
        },
        download:function(){
            location.href='manager/area/download'
        },
        upload:function(event){
            let formDate=new FormData();
            formDate.append("file",event.target.files[0]);
           axios({
              url:`manager/area/upload`,
              method:'post',
              header:{'Content-Type':'multipart/form-data'},
              data:formDate
           }).then(response=>{
               layer.msg(response.data.msg)
               this.selectPage();
           }).catch(error=>{
               layer.msg(error.message)
           })
        },
        doDelete:function(id){
            layer.msg("确认删除吗",{
                time:0,
                btn:['是','否'],
                yes:(index)=>{
                    layer.close(index);
                    let area={id:id,delFlag:1};
                    axios({
                        url:'manager/area/doDelete',
                        method: 'put',
                        data:area
                    }).then(response=>{
                        layer.msg(response.data.msg);
                        this.selectPage();
                        this.initTree();

                    })
                }
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
                url:'manager/area/select',
            }).then(response=>{
                this.nodes=response.data.obj;
                //动态添加一个父节点
                this.nodes[this.nodes.length]={id:0,name:'区域列表',open:true};

                /*init(obj,setting,nodes)初始化树的api
                 * obj是需要挂载树的jq节点对象
                 * setting是树配置对象
                 * nodes：树的显示节点信息数组
                 * */
                //必须保证响应返回对nodes赋值后，再初始化树
                let zTreeObj=$.fn.zTree.init($('#treeMenu'),this.setting,this.nodes)
            }).catch(error=>{
                layer.msg(error.message);
            });
        },
        onClick:function(event,treeId,treeNode){//点击事件触发的函数
            this.pId=treeNode.id;
            this.selectPage();
        },
        beforeEditName:function () {
            //阻止默认编辑节点状态  弹出更新窗口
            layer.open({
                type:2,
                tit: false,
                area: ['60%','80%'],
                content: `manager/area/toUpdate`,
                end:()=>{

                }
            });
            return false;
        },
        beforeRemove:function (treeId,treeNode) {
            let id=treeNode.id;
            layer.msg("确认删除吗",{
                time:0,
                btn:['是','否'],
                yes:(index)=>{
                    layer.close(index);
                    let area={id:id,delFlag:1};
                    axios({
                        url:'manager/area/doDelete',
                        method: 'put',
                        data:area
                    }).then(response=>{
                        layer.msg(response.data.msg);
                        this.selectPage();
                        this.initTree();

                    })
                }
            });
        },
        addHoverDom:function (treeId,treeNode) {

            let tId = treeNode.tId;
            //1.获取span节点
            let aObj = $(`#${tId}_span`);
            //2.获取tId值，组装  新增的span标签
            let span = `<span class="button add" id="${tId}_add" title="添加"  ></span>`
            let spanObj = $(`#${tId}_add`);
            if(spanObj.length>0){return};//已存在，阻止创建
            //3.插入到显示节点名的dom后面
            aObj.after(span);
            //4.获取新增节点，绑定点击事件
            // console.log(this)
            $(`#${tId}_add`).on('click',function () {
                console.log("------------------");
                layer.open({
                    type: 2,        //加载一个页面
                    title: false,
                    area: ['80%', '90%'],//设置宽高   px  或比例  占据父窗口
                    content: 'manager/area/toInsert',
                   end:()=>{ //关闭子窗口后的回调函数  会把this替换掉
                       if (layer.success == undefined || !layer.success) {
                           this.selectPage();
                           this.initTree();
                       }
                    }
                })
            });

            // console.log(treeNode)
        },
        removeHoverDom: function (treeId,treeNode) {
            console.log(treeNode+"移出")
            //解除节点的绑定事件  和删除节点
            $(`#${treeNode.tId}_add`).unbind().remove();
        }

    },

    mounted:function () {//当vue的代理el节点挂载上去后自动执行
        this.initTree();
    },
    created: function () {

        this.selectPage();
    }
});




