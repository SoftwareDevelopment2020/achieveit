(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-46b37c05"],{"333d":function(e,t,n){"use strict";var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"pagination-container",class:{hidden:e.hidden}},[e.total>0?n("el-pagination",e._b({attrs:{background:e.background,"current-page":e.currentPage,"page-size":e.pageSize,layout:e.layout,"page-sizes":e.pageSizes,total:e.total},on:{"update:currentPage":function(t){e.currentPage=t},"update:current-page":function(t){e.currentPage=t},"update:pageSize":function(t){e.pageSize=t},"update:page-size":function(t){e.pageSize=t},"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}},"el-pagination",e.$attrs,!1)):e._e()],1)},r=[];n("c5f6");Math.easeInOutQuad=function(e,t,n,a){return e/=a/2,e<1?n/2*e*e+t:(e--,-n/2*(e*(e-2)-1)+t)};var o=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(e){window.setTimeout(e,1e3/60)}}();function i(e){document.documentElement.scrollTop=e,document.body.parentNode.scrollTop=e,document.body.scrollTop=e}function c(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function l(e,t,n){var a=c(),r=e-a,l=20,s=0;t="undefined"===typeof t?500:t;var u=function e(){s+=l;var c=Math.easeInOutQuad(s,a,r,t);i(c),s<t?o(e):n&&"function"===typeof n&&n()};u()}var s={name:"Pagination",props:{total:{required:!0,type:Number},page:{type:Number,default:1},limit:{type:Number,default:20},pageSizes:{type:Array,default:function(){return[10,20,30,50]}},layout:{type:String,default:"total, sizes, prev, pager, next, jumper"},background:{type:Boolean,default:!0},autoScroll:{type:Boolean,default:!0},hidden:{type:Boolean,default:!1}},computed:{currentPage:{get:function(){return this.page},set:function(e){this.$emit("update:page",e)}},pageSize:{get:function(){return this.limit},set:function(e){this.$emit("update:limit",e)}}},methods:{handleSizeChange:function(e){this.$emit("pagination",{page:this.currentPage,limit:e}),this.autoScroll&&l(0,800)},handleCurrentChange:function(e){this.$emit("pagination",{page:e,limit:this.pageSize}),this.autoScroll&&l(0,800)}}},u=s,d=(n("aa51"),n("2877")),p=Object(d["a"])(u,a,r,!1,null,"25014efc",null);t["a"]=p.exports},"41dd":function(e,t,n){"use strict";var a=n("57a0"),r=n.n(a);r.a},"57a0":function(e,t,n){},"5afe":function(e,t,n){},"707c":function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticStyle:{margin:"30px 10px"}},[e.searchPage?n("Search",{on:{changePage:e.changePage,selectProject:e.selectProject}}):n("Add",{on:{changePage:e.changePage}})],1)},r=[],o=(n("a481"),function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("div",[n("el-input",{staticStyle:{width:"15%","min-width":"200px"},attrs:{placeholder:"项目ID",name:"projectIdInput",clearable:""},model:{value:e.searchValue.projectId,callback:function(t){e.$set(e.searchValue,"projectId",t)},expression:"searchValue.projectId"}}),e._v(" "),n("el-input",{staticStyle:{width:"20%","min-width":"200px"},attrs:{placeholder:"项目名称",name:"projectNameInput",clearable:""},model:{value:e.searchValue.name,callback:function(t){e.$set(e.searchValue,"name",t)},expression:"searchValue.name"}}),e._v(" "),n("el-select",{staticStyle:{width:"15%","min-width":"120px"},attrs:{placeholder:"项目状态",name:"projectStatusInput",clearable:""},model:{value:e.searchValue.statusId,callback:function(t){e.$set(e.searchValue,"statusId",t)},expression:"searchValue.statusId"}},e._l(e.statusOptions,(function(e,t){return n("el-option",{key:t,attrs:{label:e.label,value:t}})})),1),e._v(" "),n("el-button",{staticStyle:{"margin-left":"10px"},attrs:{type:"primary",name:"searchButton"},on:{click:e.search,keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.search(t)}}},[n("i",{staticClass:"el-icon-search"}),e._v(" "),n("span",[e._v("搜索")])]),e._v(" "),n("el-button",{directives:[{name:"permission",rawName:"v-permission",value:["ROLE_GLOBAL_PM"],expression:"['ROLE_GLOBAL_PM']"}],attrs:{type:"primary",name:"newProjectButton"},on:{click:e.addProject}},[n("i",{staticClass:"el-icon-plus"}),e._v(" "),n("span",[e._v("新建项目")])])],1),e._v(" "),n("div",[n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticStyle:{width:"100%","margin-top":"30px"},attrs:{data:e.table.data,"empty-text":"未参与项目"}},[n("el-table-column",{attrs:{fixed:"left",type:"index",index:e.indexMethod,align:"center"}}),e._v(" "),n("el-table-column",{attrs:{fixed:"left",prop:"projectId",label:"项目ID",align:"center","min-width":"120"}}),e._v(" "),n("el-table-column",{attrs:{fixed:"left",prop:"name",label:"项目名称",align:"center"}}),e._v(" "),n("el-table-column",{attrs:{prop:"projectManagerName",label:"创建人",width:"150",align:"center"}}),e._v(" "),n("el-table-column",{attrs:{prop:"scheduledDate",label:"开始时间",width:"150",align:"center"}}),e._v(" "),n("el-table-column",{attrs:{prop:"statusId",label:"项目状态",width:"150",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[null!==a.statusId?n("span",[n("svg-icon",{attrs:{"icon-class":e.statusOptions[e.getStatusId(a.statusId)].svg}}),e._v(" "),n("span",[e._v(e._s(e.statusOptions[e.getStatusId(a.statusId)].label))])],1):e._e()]}}])}),e._v(" "),n("el-table-column",{attrs:{"min-width":"200"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("el-button",{attrs:{type:"text",size:"mini"},on:{click:function(t){return e.selectProject(a)}}},[e._v("进入项目")])]}}])})],1),e._v(" "),n("pagination",{attrs:{total:e.table.total,page:e.table.page,limit:e.table.limit},on:{"update:page":function(t){return e.$set(e.table,"page",t)},"update:limit":function(t){return e.$set(e.table,"limit",t)},pagination:e.getProjects}})],1)])}),i=[],c=(n("7f7f"),n("6b54"),n("333d")),l=n("24d2"),s=n("cf45"),u={components:{Pagination:c["a"]},data:function(){return{loading:!1,searchValue:{projectId:"",name:"",statusId:""},statusOptions:this.Constant.projectStatus,table:{data:[],searchCondition:{projectId:null,name:null,statusId:null},total:0,page:1,limit:10}}},mounted:function(){this.getProjects()},methods:{indexMethod:function(e){return e+1},getStatusId:function(e){return parseInt(e.toString().charAt(0))},getProjects:function(){var e=this;this.loading=!0,Object(l["e"])({current:this.table.page,size:this.table.limit,searchCondition:this.table.searchCondition}).then((function(t){Object(s["b"])(t.data,e.table,e.setSearchValue())})).catch((function(e){console.error(e)})).finally((function(){e.loading=!1}))},setSearchValue:function(){this.searchValue.projectId=this.table.searchCondition.projectId,this.searchValue.name=this.table.searchCondition.name,this.searchValue.statusId=this.table.searchCondition.statusId},search:function(){this.table.searchCondition.projectId=Object(s["a"])(this.searchValue.projectId),this.table.searchCondition.name=Object(s["a"])(this.searchValue.name),this.table.searchCondition.statusId=Object(s["a"])(this.searchValue.statusId),this.table.page=1,this.getProjects()},addProject:function(){this.$emit("changePage")},selectProject:function(e){this.$emit("selectProject",e)}}},d=u,p=n("2877"),m=Object(p["a"])(d,o,i,!1,null,null,null),f=m.exports,h=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("el-page-header",{attrs:{content:"立项申请"},on:{back:e.goBack}}),e._v(" "),n("el-form",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],ref:"project",staticClass:"add-form",attrs:{model:e.project,rules:e.addProjectRules,"label-width":"90px","label-position":"left","hide-required-asterisk":!0}},[n("el-form",{ref:"projectInline",attrs:{model:e.project,rules:e.addProjectRules,inline:!0,"hide-required-asterisk":!0}},[n("el-form-item",{attrs:{label:"客户ID",prop:"clientId","label-width":"90px"}},[n("el-input",{attrs:{name:"newProjectClientId"},model:{value:e.project.clientId,callback:function(t){e.$set(e.project,"clientId",t)},expression:"project.clientId"}})],1),e._v(" "),n("el-form-item",{staticStyle:{"margin-left":"20px"},attrs:{label:"研发类型",prop:"projectId"}},[n("el-select",{model:{value:e.project.projectId,callback:function(t){e.$set(e.project,"projectId",t)},expression:"project.projectId"}},e._l(e.projectTypeOptions,(function(e){return n("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1)],1),e._v(" "),n("el-form-item",{attrs:{label:"项目名称",prop:"name"}},[n("el-input",{attrs:{name:"newProjectName"},model:{value:e.project.name,callback:function(t){e.$set(e.project,"name",t)},expression:"project.name"}})],1),e._v(" "),n("el-form",{attrs:{inline:!0}},[n("el-form-item",{attrs:{label:"预定时间",prop:"scheduledDate","label-width":"90px"}},[n("el-date-picker",{attrs:{"value-format":"yyyy-MM-dd",type:"date",clearable:!1,name:"newProjectScheduledDate","picker-options":{disabledDate:function(t){return t.getTime()<e.today}}},model:{value:e.project.scheduledDate,callback:function(t){e.$set(e.project,"scheduledDate",t)},expression:"project.scheduledDate"}})],1),e._v(" "),n("el-form-item",{staticStyle:{"margin-left":"20px"},attrs:{label:"交付日",prop:"deliveryDate"}},[n("el-date-picker",{attrs:{"value-format":"yyyy-MM-dd",type:"date",name:"newProjectDeliveryDate",clearable:!1,"picker-options":{disabledDate:function(t){return t.getTime()<new Date(e.project.scheduledDate)}}},model:{value:e.project.deliveryDate,callback:function(t){e.$set(e.project,"deliveryDate",t)},expression:"project.deliveryDate"}})],1)],1),e._v(" "),n("el-form-item",{attrs:{label:"项目上级",prop:"superior"}},[n("el-select",{attrs:{name:"newProjectSuperior",filterable:""},model:{value:e.project.superior,callback:function(t){e.$set(e.project,"superior",t)},expression:"project.superior"}},e._l(e.superiorOptions,(function(t){return n("el-option",{key:t.id,attrs:{value:t.id,label:t.name,name:"NewProjectSuperiorOption"}},[n("span",{staticStyle:{float:"left"}},[e._v(e._s(t.name))]),e._v(" "),n("span",{staticStyle:{float:"right",color:"#8492a6","font-size":"13px"}},[e._v(e._s(t.id))])])})),1)],1),e._v(" "),n("el-form-item",{attrs:{label:"主要里程碑",prop:"majorMilestone"}},[n("el-input",{attrs:{type:"textarea",resize:"none",rows:5,maxlength:"500",name:"newProjectMajorMilestone","show-word-limit":""},model:{value:e.project.majorMilestone,callback:function(t){e.$set(e.project,"majorMilestone",t)},expression:"project.majorMilestone"}})],1),e._v(" "),n("el-form-item",{attrs:{label:"采用技术",prop:"mainTechnique"}},[n("el-input",{attrs:{type:"textarea",resize:"none",rows:5,name:"newProjectMainTechnique",maxlength:"500","show-word-limit":""},model:{value:e.project.mainTechnique,callback:function(t){e.$set(e.project,"mainTechnique",t)},expression:"project.mainTechnique"}})],1),e._v(" "),n("el-form-item",{attrs:{label:"业务领域",prop:"businessField"}},[n("el-input",{attrs:{type:"textarea",resize:"none",rows:5,maxlength:"500",name:"newProjectBusinessField","show-word-limit":""},model:{value:e.project.businessField,callback:function(t){e.$set(e.project,"businessField",t)},expression:"project.businessField"}})],1),e._v(" "),n("el-form-item",{attrs:{label:"主要功能",prop:"mainFunction"}},[n("el-input",{attrs:{type:"textarea",resize:"none",rows:8,maxlength:"1000",name:"newProjectMainFunction","show-word-limit":""},model:{value:e.project.mainFunction,callback:function(t){e.$set(e.project,"mainFunction",t)},expression:"project.mainFunction"}})],1)],1),e._v(" "),n("div",{attrs:{align:"center"}},[n("el-button",{attrs:{type:"primary"},on:{click:e.resetForm}},[e._v("重置")]),e._v(" "),n("el-button",{attrs:{type:"primary",name:"newProjectSubmitButton"},on:{click:e.addProject}},[e._v("添加")])],1)],1)},g=[],j=n("c466"),b=n("ef75"),v={data:function(){return{project:{clientId:"",projectId:"D",name:"",scheduledDate:new Date,deliveryDate:new Date,superior:"",majorMilestone:"",mainTechnique:"",businessField:"",mainFunction:""},addProjectRules:this.Constant.projectRules,projectTypeOptions:this.Constant.projectType,today:null,superiorOptions:[],loading:!1}},mounted:function(){var e=this;this.loading=!0;var t=new Date;t.setHours(0,0,0,0),this.today=t.getTime(),this.project.scheduledDate=Object(j["a"])(t),this.project.deliveryDate=Object(j["a"])(t);var n={current:-1,size:0,searchCondition:"ROLE_SUPERIOR"};Object(b["d"])(n).then((function(t){e.superiorOptions=t.data.records})).finally((function(){e.loading=!1}))},methods:{goBack:function(){this.$emit("changePage")},resetForm:function(){this.$refs.project.resetFields()},addProject:function(){var e=this;this.$refs.projectInline.validate((function(t){e.$refs.project.validate((function(n){if(!t||!n)return e.$message.error("请完整填写所需字段"),!1;Object(l["a"])(e.project).then((function(t){e.$message.success("立项成功"),e.goBack()})).catch((function(e){console.info(e)}))}))}))}}},y=v,_=(n("41dd"),Object(p["a"])(y,h,g,!1,null,"c326ac70",null)),w=_.exports,x={components:{Search:f,Add:w},data:function(){return{searchPage:!0}},methods:{changePage:function(){this.searchPage=!this.searchPage},selectProject:function(e){var t=this;this.$store.dispatch("project/getCurrentProject",e.id).then((function(){t.$router.replace({path:"/project-detail"})}))}}},I=x,P=Object(p["a"])(I,a,r,!1,null,"e53ff35e",null);t["default"]=P.exports},aa51:function(e,t,n){"use strict";var a=n("5afe"),r=n.n(a);r.a},c466:function(e,t,n){"use strict";function a(e){var t=new Date(e),n=t.getMonth()+1;return t.getFullYear()+"年"+n+"月"+t.getDate()+"日"}function r(e){var t=new Date(e),n=t.getHours(),a=t.getMinutes(),r=t.getSeconds();return(n<10?"0":"")+n+":"+(a<10?"0":"")+a+":"+(r<10?"0":"")+r}function o(e){var t=new Date(e),n=t.getMonth()+1,a=t.getHours(),r=t.getMinutes(),o=t.getSeconds();return t.getFullYear()+"年"+n+"月"+t.getDate()+"日  "+(a<10?"0":"")+a+":"+(r<10?"0":"")+r+":"+(o<10?"0":"")+o}function i(e){var t=e.getFullYear(),n=e.getMonth()+1,a=e.getDate();return t+"-"+(n<10?"0":"")+n+"-"+(a<10?"0":"")+a}n.d(t,"b",(function(){return a})),n.d(t,"d",(function(){return r})),n.d(t,"c",(function(){return o})),n.d(t,"a",(function(){return i}))},cf45:function(e,t,n){"use strict";n.d(t,"b",(function(){return r})),n.d(t,"a",(function(){return o}));var a=n("61f7");function r(e,t,n){t.data=e.records,t.total=e.total,t.page=e.current,n&&n()}function o(e){return Object(a["c"])(e)?null:e}},ef75:function(e,t,n){"use strict";n.d(t,"f",(function(){return c})),n.d(t,"c",(function(){return l})),n.d(t,"a",(function(){return s})),n.d(t,"b",(function(){return u})),n.d(t,"i",(function(){return d})),n.d(t,"h",(function(){return p})),n.d(t,"e",(function(){return m})),n.d(t,"d",(function(){return f})),n.d(t,"g",(function(){return h}));var a=n("b775");function r(e){return Object(a["a"])({url:"/employee"+e,method:"get"})}function o(e,t){return Object(a["a"])({url:"/employee"+e,method:"post",data:t})}function i(e){return Object(a["a"])({url:"/employee"+e,method:"delete"})}function c(e){return o("/project_employee_vo",e)}function l(e){return r("/get_project_employee_basics?id="+e.id)}function s(e){return o("/add_project_employee",e)}function u(e){return console.info(e),i("/delete_project_employee?id="+e.projectEmployeeId)}function d(e){return o("/set_role",e)}function p(e){return o("/set_permission",e)}function m(e){return Object(a["a"])({url:"/employee/employees_by_project_id",method:"get",params:{projectId:e}})}function f(e){return Object(a["a"])({url:"/employee/search_employee_by_global_roles",method:"post",data:e})}function h(e){return r("/get_project_manhour_employee?id="+e.id+"&isSubordinate="+e.isSubordinate)}}}]);