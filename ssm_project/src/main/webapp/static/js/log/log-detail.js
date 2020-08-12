

let vm = new Vue({
    el: '.page-content',
    data: {
        log: {}
    },
    methods: {
    },
    created: function () {
        this.log = parent.layer.obj;
        // this.selectDetail(parent.layer.obj);

    }
})