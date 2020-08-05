let vm = new Vue({
    el: '.page-content',
    data: function () {
        return {
            nodes: [],
            treeObj: {},
            resources: [],//存放当前角色的权限数组
            officeNodes: [],//公司节点数组
            officeTreeObj: '',//公司树对象
            offices: [],//存放当前角色的授权公司数组
            role: {
                resources: null,//角色权限
                oldResources: null,//原角色权限
                offices: null,//角色已授权公司
                oldOffices: null //角色原已授权公司
            },
            setting: {//树配置对象  用于设置树结构全局配置
                data: {
                    simpleData: {//简单数据格式设置
                        enable: true,//开启简单数据格式   自动将一维数组组装成父子结构
                        pIdKey: 'parentId' //默认的父id属性名为pId
                    }
                },
                check: {//选项框设置
                    enable: true,  //开启选项框
                    chkboxType: {'Y': 'ps', 'N': 'ps'}  //Y控制选中   N控制取消选中   p:父项关联  s子项关联
                },
                callback: {//回调函数设置，用于设置一些事件回调函数
                    onClick: this.onClick
                },
                view: {//显示回调，当节点显示的时候，会触发回调
                    fontCss: this.fontCss
                }
            },
        }
    },
    methods: {
        selectByRid: function () {
            axios({
                url: 'manager/menu/selectByRid',
                params: {rid: this.role.id}
            }).then(response => {
                this.resources = response.data.obj;
                for (let i in this.nodes) {
                    for (let j in this.resources) {
                        if (this.nodes[i].id == this.resources[j].id) {
                            this.nodes[i].checked = true;
                            break;
                        }
                    }
                }
                this.treeObj = $.fn.zTree.init($('#select-treetreeSelectResEdit'), this.setting, this.nodes);
            })
        },


        initTree: function () {
            axios({
                url: 'manager/menu/list',
            }).then(response => {
                this.nodes = response.data.obj;
                //动态添加一个父节点
                this.nodes[this.nodes.length] = {id: 0, name: '全部权限', checked: true};
                /*init(obj,setting,nodes)初始化树的api
                 * obj是需要挂载树的jq节点对象
                 * setting是树配置对象
                 * nodes：树的显示节点信息数组
                 * */
                //必须保证响应返回对nodes赋值后，再初始化树
                this.selectByRid();
            }).catch(error => {
                layer.msg(error.message);
            });
        },

        selectOfficeByRid: function () {
            axios({
                url: 'manager/office/selectOfficeByRid',
                params: {rid: this.role.id}
            }).then(response => {
                this.offices = response.data.obj;
                for (let i in this.officeNodes) {
                    for (let j in this.offices) {
                        if (this.officeNodes[i].id == this.offices[j].id) {
                            this.officeNodes[i].checked = true;
                            break;
                        }
                    }
                }
                this.officeTreeObj = $.fn.zTree.init($('#select-treetreeSelectOfficeEdit'), this.setting, this.officeNodes);
                $('#treeSelectOfficeEdit').css('display', 'inline-block');
            }).catch(error => {
                layer.msg(error.message);
            });
        },

        initOfficeTree: function () {
            axios({
                url: 'manager/office/select',
            }).then(response => {
                this.officeNodes = response.data.obj;
                //动态添加一个父节点
                this.officeNodes[this.officeNodes.length] = {id: 0, name: '全部机构', checked: true};
                /*init(obj,setting,nodes)初始化树的api
                 * obj是需要挂载树的jq节点对象
                 * setting是树配置对象
                 * nodes：树的显示节点信息数组
                 * */
                //必须保证响应返回对nodes赋值后，再初始化树
                this.selectOfficeByRid();
            }).catch(error => {
                layer.msg(error.message);
            });
        },

        changedataScope: function () {
            if (this.role.dataScope == 9) {
                if (this.officeTreeObj != undefined && this.officeTreeObj != '') {
                    $('#treeSelectOfficeEdit').css('display', 'inline-block');
                } else {
                    this.initOfficeTree();
                }
            } else {
                $('#treeSelectOfficeEdit').css('display', 'none');
            }
        },
        doUpdate: function () {
            this.role.oldResources = this.resources;
            this.role.oldOffices = this.officeNodes;
            this.role.resources = this.treeObj.getCheckedNodes(true);
            if (this.role.resources.length > 0) {
                if (this.role.resources != undefined && this.role.resources[0].id == 0) {
                    this.role.resources.splice(0, 1);
                }
            }
            if (this.role.dataScope == 9) {
                this.role.offices = this.officeTreeObj.getCheckedNodes(true);
                if (this.role.offices.length > 0) {
                    if (this.role.offices != undefined && this.role.offices[0].id == 0 && this.role.offices != null) {
                        this.role.offices.splice(0, 1);
                    }
                }
            }
            axios({
                url: 'manager/role/doUpdate',
                method: 'put',
                data: this.role
            }).then(response => {
                let index = parent.layer.getFrameIndex(window.name);
                parent.layer.success = response.data.success;
                if (response.data.success) {
                    parent.layer.close(index);
                    parent.layer.msg("更新成功")
                } else {
                    parent.layer.msg("更新失败");
                }
            }).catch(error => {
                console.log(error.message);
            })
        },
        toSelect:function () {
            layer.open({
                type:2,
                title:false,
                area:['80%','80%'],
                content: 'manager/role/toSelect',
                end:()=>{
                    if (layer.parentName!=undefined&&layer.parentName!=''){
                        this.role.officeName=layer.parentName;
                        this.role.officeId=layer.parentId;
                    }
                }
            })
        }
    },

    mounted: function () {//当vue的代理el节点挂载上去后自动执行
        this.initTree();
    },
    created: function () {
        this.role = parent.layer.obj;
        if (this.role.dataScope == 9) {
            this.initOfficeTree();
        }
    }
});





