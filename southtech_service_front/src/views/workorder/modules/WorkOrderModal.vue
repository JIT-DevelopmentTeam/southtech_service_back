<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-layout>
        <a-layout>
          <a-layout-content>
      <a-form :form="form">
        <a-row class="form-row" :gutter="16">
          <a-col :lg="8">
            <a-form-item label="编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input :disabled="true" v-decorator="[ 'number', validatorRules.number]" placeholder="请输入编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-dict-select-tag @change="setWorkOrderType" type="list" v-decorator="['type',validatorRules.type]" :trigger-change="true" dictCode="work_order_type" placeholder="请选择类型"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
               <j-search-select-tag
                placeholder="请选择客户"
                v-decorator="['clientId',validatorRules.clientId]"
                dict="tb_client,name,id"
                @change="selectClientInSelect"
                :disabled="true"
                :async="true">
              </j-search-select-tag>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
           <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag v-decorator="['contactId',validatorRules.contactId]" placeholder="请选择联系人" :trigger-change="true" :dictCode="contactCondition()" @change="selectContact($event)"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
           <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请选择联系人" :value="contact != null ? contact.mobilePhone : ''" :disabled="true"></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
           <a-form-item label="接入方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['accessMethod',validatorRules.accessMethod]" :trigger-change="true" dictCode="work_order_access_method" placeholder="请选择接入方式"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
           <a-form-item label="代报人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['correspondentName',validatorRules.correspondentName]" :multi="false" :trigger-change="true"/>
            </a-form-item>
          </a-col>
           <a-col :lg="8">
            <a-form-item label="紧急程度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['emergencyLevel',validatorRules.emergencyLevel]" :trigger-change="true" dictCode="work_order_emergency_level" placeholder="请选择紧急程度"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="客服" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-select-user-by-dep v-decorator="['customerServiceName',validatorRules.customerServiceName]" :multi="false" :trigger-change="true"/>
              </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="申报时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择申报时间" v-decorator="[ 'declarationTime', validatorRules.declarationTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-upload v-decorator="['annex']" name="workOrderDetail"></j-upload>
            </a-form-item>
          </a-col>
        </a-row>

        <!-- 子表单区域 -->
        <a-tabs defaultActiveKey="1" >
          <a-tab-pane tab="工单明细" key="1">
            <div>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="5">设备档案</a-col>
                <a-col v-if="workOrderType == '1'" :span="7">故障部位</a-col>
                <a-col :span="9">描述</a-col>
                <a-col :span="2">操作</a-col>
              </a-row>

              <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, index) in model.workOrderDetailList" :key="index">
                <a-col :span="5">
                  <a-form-item>
                    <j-dict-select-tag v-decorator="['workOrderDetailList['+index+'].deviceNumber', {'initialValue':item.deviceNumber,rules: [{ required: true, message: '请选择设备档案!' }]}]" placeholder="设备档案" :trigger-change="true" :dictCode="deviceNumberCondition()"/>
                  </a-form-item>
                </a-col>
                <a-col v-if="workOrderType == '1'" :span="7">
                  <a-form-item>
                    <j-multi-select-tag placeholder="故障部位" v-decorator="['workOrderDetailList['+index+'].faultLocation', {'initialValue':item.faultLocation,rules: [{ required: true, message: '请选择故障部位!' }]}]" dictCode="work_order_detail_fault_location"/>
                  </a-form-item>
                </a-col>
                <a-col :span="9">
                  <a-form-item>
                    <a-textarea rows="4" v-decorator="['workOrderDetailList['+index+'].description', {'initialValue':item.description}]" placeholder="描述"></a-textarea>
                  </a-form-item>
                </a-col>
                <a-col :span="2">
                  <a-form-item>
                    <a-button @click="addRowDetail" icon="plus"></a-button>&nbsp;
                    <a-button @click="delRowDetail(index)" icon="minus"></a-button>
                  </a-form-item>
                </a-col>
              </a-row>
            </div>
          </a-tab-pane>
        </a-tabs>
      </a-form>
          </a-layout-content>
          <a-layout-sider>
          <a-row class="form-row" :gutter="32">
            <a-col :lg="32">
              <strong>快捷搜索</strong>
              <a-input placeholder="客户" @change="searchClient($event.target.value)"></a-input>
                <a-radio-group v-model="model.clientId" @change="selectClient">
                  <a-radio v-for="(client,index) in clientList" :style="radioStyle" v-bind:key="index" :value="client.id" :title="client.name">{{client.name}}</a-radio>
              </a-radio-group>
            </a-col>
          </a-row>
          <a-row class="form-row" :gutter="32" style="margin-top:20%;">
            <a-col :lg="32">
              <strong>快捷搜索</strong>
              <a-input placeholder="客服" @change="searchCustomerService($event.target.value)"></a-input>
                <a-radio-group v-model="model.customerServiceName" @change="selectCustomerService">
                  <a-radio v-for="(customerService,index) in customerServiceList" :style="radioStyle" v-bind:key="index" :value="customerService.username" :title="customerService.realname">{{customerService.realname}}</a-radio>
              </a-radio-group>
            </a-col>
          </a-row>
        </a-layout-sider>
        </a-layout>
      </a-layout>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JUpload from '@/components/jeecg/JUpload'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JMultiSelectTag from '@/components/dict/JMultiSelectTag'
  import {formatDate} from '@/utils/util.js'

  export default {
    name: "WorkOrderModal",
    components: { 
      JDate,
      JUpload,
      JSelectUserByDep,
      JDictSelectTag,
      JSearchSelectTag,
      JMultiSelectTag
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:1400,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        radioStyle: {
          display: 'block',
          height: '30px',
          lineHeight: '30px',
        },
        confirmLoading: false,
        disabledWrite:false,
        contactId:null,
        validatorRules:{
        number:{rules: [{ required: true, message: '请输入编号!' }]},
        status:{rules: [{ required: true, message: '请输入状态!' }]},
        type:{rules: [{ required: true, message: '请输入类型!' }]},
        clientId:{rules: [{ required: true, message: '请输入客户!' }]},
        contactId:{},
        accessMethod:{rules: [{ required: true, message: '请输入接入方式!' }]},
        correspondentName:{},
        emergencyLevel:{rules: [{ required: true, message: '请输入紧急程度!' }]},
        customerServiceName:{rules: [{ required: true, message: '请输入客服!' }]},
        declarationTime:{rules: [{ required: true, message: '请输入申报时间!' }]},
        annex:{}
        },
        url: {
          add: "/workorder/workOrder/add",
          edit: "/workorder/workOrder/edit",
          workOrderDetailList: "/workorder/workOrder/queryWorkOrderDetailListByMainId",
          listClient: "/client/client/list",
          listCustomerService:"/sys/user/listUserByRoleCode",
          getClientById:"/client/client/getClientById",
          getContactById:"/client/client/getContactById",
          getByUserName:"/sys/user/getByUserName",
        },
        workOrderType:null,
        listSize:5,
        clientList:[],
        client:{},
        contact:{},
        customerService:{},
        customerServiceList:[]
      }
    },
    created () {
    },
    mounted () {
      getAction(this.url.listClient,null).then((res) => {
        if (res.success) {
          this.clientList = res.result.records;
        }
      });
      getAction(this.url.listCustomerService+"?roleCode=customer_service",{pageSize:this.listSize}).then((res) => {
        if (res.success) {
          this.customerServiceList = res.result.records;
        }
      });
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        if (!record.id) {
          record.number = 'W'+formatDate(Date.parse(new Date()),'yyyyMMddhhmmss');
          record.declarationTime = formatDate(Date.parse(new Date()),'yyyy-MM-dd hh:mm:ss');
        }
        this.model = Object.assign({}, record);
        this.model.workOrderDetailList = [{}];
        if(this.model.id){
          this.client.id = this.model.clientId;
          this.workOrderType = this.model.type;
          let params = {id:this.model.id}
          getAction(this.url.getContactById+"?id="+record.contactId,null).then((res) => {
            if (res.success) {
              this.contact = res.result;
            }
          })
          //初始化订单明细列表
          getAction(this.url.workOrderDetailList,params).then((res)=>{
            if(res.success){
              this.model.workOrderDetailList = res.result;
              this.$forceUpdate()
            }
          })
        }
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'number','status','type','clientId','contactId','accessMethod','correspondentName','emergencyLevel','customerServiceName','declarationTime','annex'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
              method = 'put';
            }
            if (res && res.success) {
              values.contactId = res.result.id;
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'number','status','type','clientId','contactId','accessMethod','correspondentName','emergencyLevel','customerServiceName','declarationTime','annex'))
      },
      contactCondition() {
        return "tb_contact,name,id,client_id="+this.client.id;
      },
      deviceNumberCondition(){
        return "tb_device_number,name,id,client_id="+this.client.id;
      },
      addRowDetail () {
        this.model.workOrderDetailList.push({});
        this.$forceUpdate();
      },
      delRowDetail (index) {
        //console.log(index)
        this.model.workOrderDetailList.splice(index,1);
        this.$forceUpdate();
      },
      selectClient(id) {
        this.client.id = id;
      },
      setWorkOrderType(value) {
        this.workOrderType = value;
      },
      searchClient(val) {
        getAction(this.url.listClient,{name:val}).then((res) => {
          if (res.success) {
            this.clientList = res.result.records;
          }
        });
      },
      selectClient() {
        if (this.model.clientId) {
          getAction(this.url.getClientById+"?id="+this.model.clientId,null).then((res) => {
             if (res.success) {
              this.client = res.result;
              this.contact = {};
              this.form.setFieldsValue({
                clientId: this.client.id
              });
            }
          });
        }
      },
      searchCustomerService(val) {
         getAction(this.url.listCustomerService,{pageSize:this.listSize,username:val}).then((res) => {
          if (res.success) {
            this.customerServiceList = res.result.records;
          }
        });
      },
      selectContact(value) {
        this.contactId = value;
        getAction(this.url.getContactById+"?id="+value,null).then((res) => {
          if (res.success) {
            this.contact = res.result;
            this.form.setFieldsValue({
                contactId: this.contact.id
            });
          }
        });
      },
      selectCustomerService() {
        if (this.model.customerServiceName) {
          getAction(this.url.getByUserName+"?userName="+this.model.customerServiceName,null).then((res) => {
             if (res.success) {
              this.customerService = res.result;
              this.form.setFieldsValue({
                  customerServiceName: this.customerService.username
              });
            }
          });
        }
      },
      selectClientInSelect(id) {
        this.client.id = id;
      },
      selectContact(value) {
        getAction(this.url.getContactById+"?id="+value,null).then((res) => {
          if (res.success) {
            this.contact = res.result;
            this.form.setFieldsValue({
                contactId: this.contact.id
            });
          }
        });
      },
    }
  }
</script>
<style>
    #components-layout-demo-basic { 
      text-align: center;
    }
    #components-layout-demo-basic .ant-layout-header,
    #components-layout-demo-basic .ant-layout-footer {
      color: #fff;
    }
    #components-layout-demo-basic .ant-layout-footer {
      line-height: 1.5;
    }
    .ant-layout-sider {
      background:white;
    }
    .ant-layout-content {
      background: white;
    }
    #components-layout-demo-basic .ant-layout-sider {
      color: #fff;
      line-height: 120px;
      
    }
    #components-layout-demo-basic .ant-layout-content {
      color: #fff;
      min-height: 120px;
      line-height: 120px;
    }
    #components-layout-demo-basic > .ant-layout {
      margin-bottom: 48px;
    }
    #components-layout-demo-basic > .ant-layout:last-child {
      margin: 0;
    }
</style>