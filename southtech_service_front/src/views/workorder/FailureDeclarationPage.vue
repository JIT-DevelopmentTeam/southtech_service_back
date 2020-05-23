<template>
    <a-spin :spinning="confirmLoading">
      <a-layout>
      <a-layout>
        <a-layout-content>
      <a-form 
        :form="form"
        :title="title"
        :visible="visible"
        :confirmLoading="confirmLoading"
        @ok="handleOk"
      >
        <a-row class="form-row" :gutter="32">
          <a-col :lg="12">
            <a-form-item label="编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input :disabled="true" v-decorator="[ 'number', validatorRules.number]" placeholder="请输入编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="12">
            <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-dict-select-tag type="list" :disabled="true" v-decorator="['type',validatorRules.type]" :trigger-change="true" dictCode="work_order_type" placeholder="请选择类型"/>
            </a-form-item>
          </a-col>
          <a-col :lg="12">
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
          <a-col :lg="12">
            <a-form-item label="联系人(选)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select v-decorator="['contactId',validatorRules.contactId]" placeholder="请选择联系人" @change="selectContact">
                <a-select-option v-for="(item, index) in options.contactOptions" :key="index" :value="item.value">{{item.label}}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="12">
            <a-form-item label="联系人(填)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input :disabled="disabledWrite" v-model="contactName" placeholder="请输入联系人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="12">
            <a-form-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input :disabled="disabledWrite" v-model="contactMobile" placeholder="请选择联系人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="12">
           <a-form-item label="接入方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
             <a-radio-group v-decorator="['accessMethod',validatorRules.accessMethod]" :options="options.accessMethodOptions"/>
            </a-form-item>
          </a-col>
          <a-col :lg="12">
            <a-form-item label="紧急程度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group v-decorator="['emergencyLevel',validatorRules.emergencyLevel]" :options="options.emergencyLevelOptions"/>
            </a-form-item>
          </a-col>
          <a-col :lg="12">
           <a-form-item label="需要派工" :labelCol="labelCol" :wrapperCol="wrapperCol">
             <a-radio-group v-decorator="['needDispatch',validatorRules.needDispatch]" :options="options.needDispatchOptions"/>
            </a-form-item>
          </a-col>
          <a-col :lg="12">
            <a-form-item label="申报时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择申报时间" v-decorator="[ 'declarationTime', validatorRules.declarationTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :lg="12">
           <a-form-item label="代报人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['correspondentName',validatorRules.correspondentName]" :multi="false" :trigger-change="true"/>
            </a-form-item>
          </a-col>
          <a-col :lg="12">
            <a-form-item label="客服" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-select-user-by-dep v-decorator="['customerServiceName',validatorRules.customerServiceName]" :multi="false" :disabled="true"/>
              </a-form-item>
          </a-col>
          <a-col :lg="12">
            <a-form-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-upload v-decorator="['annex']" name="annex" :trigger-change="true"></j-upload>
            </a-form-item>
          </a-col>
        </a-row>

        <!-- 子表单区域 -->
        <a-tabs defaultActiveKey="1" >
          <a-tab-pane tab="工单明细" key="1">
            <div>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="5">设备档案</a-col>
                <a-col :span="7">故障部位</a-col>
                <a-col :span="11">描述</a-col>
                <!-- <a-col :span="2">操作</a-col> -->
              </a-row>

              <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, index) in model.workOrderDetailList" :key="index">
                <a-col :span="5">
                  <a-form-item>
                    <a-select v-decorator="['workOrderDetailList['+index+'].deviceNumber', {'initialValue':item.deviceNumber,rules: [{ required: true, message: '请选择设备档案!' }]}]" placeholder="设备档案">
                      <a-select-option v-for="(item, index) in options.deviceNumberOptions" :key="index" :value="item.value">{{item.label}}</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :span="7">
                  <a-form-item>
                    <j-checkbox
                      v-decorator="['workOrderDetailList['+index+'].faultLocation', {'initialValue':item.faultLocation,rules: [{ required: true, message: '请选择故障部位!' }]}]"
                      :options="options.faultLocationOptions"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="11">
                  <a-form-item>
                    <a-textarea rows="4" v-decorator="['workOrderDetailList['+index+'].description', {'initialValue':item.description}]" placeholder="描述"></a-textarea>
                  </a-form-item>
                </a-col>
                <!-- <a-col :span="2">
                  <a-form-item>
                    <a-button @click="addRowDetail" icon="plus"></a-button>&nbsp;
                    <a-button @click="delRowDetail(index)" icon="minus"></a-button>
                  </a-form-item>
                </a-col> -->
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
          <a-row class="form-row" :gutter="32" style="margin-top:50%;">
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
      <div id="components-layout-demo-basic">
            <a-layout>
            <a-layout-footer><a-button @click="handleOk" type="primary" icon="check">提交</a-button></a-layout-footer>
            </a-layout>
         </div>
    </a-layout>
    </a-spin>
</template>

<script>

  import { httpAction,getAction,postAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JUpload from '@/components/jeecg/JUpload'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JMultiSelectTag from '@/components/dict/JMultiSelectTag'
  import JCheckbox from '@/components/jeecg/JCheckbox'
  import {formatDate} from '@/utils/util.js'
  import {ajaxGetDictItems} from '@/api/api'

  export default {
    name: "FailureDeclarationPage",
    components: { 
      JDate,
      JUpload,
      JSelectUserByDep,
      JDictSelectTag,
      JSearchSelectTag,
      JMultiSelectTag,
      JCheckbox
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:1600,
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
        contactName:null,
        contactMobile:null,
        validatorRules:{
        number:{rules: [{ required: true, message: '请输入编号!' }]},
        status:{rules: [{ required: true, message: '请选择状态!' }]},
        type:{rules: [{ required: true, message: '请选择类型!' }]},
        clientId:{rules: [{ required: true, message: '请选择客户!' }]},
        contactId:{},
        accessMethod:{rules: [{ required: true, message: '请选择接入方式!' }]},
        correspondentName:{},
        emergencyLevel:{rules: [{ required: true, message: '请选择紧急程度!' }]},
        customerServiceName:{},
        needDispatch:{rules: [{ required: true, message: '请选择需要派工!' }]},
        declarationTime:{rules: [{ required: true, message: '请选择申报时间!' }]},
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
          addContactReturn:"/client/client/addContactReturn"
        },
        listSize:5,
        clientList:[],
        client:{},
        contact:{},
        customerService:{},
        customerServiceList:[],
        options:{
          accessMethodOptions:[],
          emergencyLevelOptions:[],
          faultLocationOptions:[],
          deviceNumberOptions:[],
          needDispatchOptions:[],
          contactOptions:[]
        },
      }
    },
    created () {
      this.initAccessMethodOptions();
      this.initEmergencyLevelOptions();
      this.initFaultLocationOptions();
      this.initNeedDispatchOptions();
    },
    mounted () {
        this.form.resetFields();
        this.model.number = 'W'+formatDate(Date.parse(new Date()),'yyyyMMddhhmmss');
        this.model.type = "1";
        this.model.accessMethod = "1";
        this.model.emergencyLevel = "3";
        this.model.needDispatch = "0";
        this.model.declarationTime = formatDate(Date.parse(new Date()),'yyyy-MM-dd hh:mm:ss');
        this.model.workOrderDetailList = [{}];
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'number','status','type','clientId','contactId','accessMethod','needDispatch','correspondentName','emergencyLevel','customerServiceName','declarationTime','annex'))
        })
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
       handleOk () {
        let that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.addContact().then((res)=>{
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
              this.form.resetFields();
              this.model.number = 'W'+formatDate(Date.parse(new Date()),'yyyyMMddhhmmss');
              this.model.type = "1";
              this.model.accessMethod = "1";
              this.model.emergencyLevel = "3";
              this.model.needDispatch = "0";
              this.model.declarationTime = formatDate(Date.parse(new Date()),'yyyy-MM-dd hh:mm:ss');
              this.model.workOrderDetailList = [{}];
              this.options.deviceNumberOptions = [];
              this.contact = {};
              this.contactName = null;
              this.contactMobile = null;
              this.visible = true;
              this.$nextTick(() => {
                this.form.setFieldsValue(pick(this.model,'number','type','accessMethod','needDispatch','emergencyLevel','declarationTime'))
              });
            })
            });
          }
        })
      },
      addContact(){
        let that = this;
        return new Promise(function (resolve,reject) {
          let contactId = that.form.getFieldValue('contactId');
          if (!contactId) {
            if (!that.contactName && !that.contactMobile) {
              that.$message.error('请填写联系人和电话!');
              return;
            }
            // 先插入联系人
            let clientId = that.form.getFieldValue('clientId');
            let addContact = {name:that.contactName,mobilePhone:that.contactMobile,clientId:clientId};
            postAction(that.url.addContactReturn,addContact).then((res) => {
              resolve(res);
            }).catch(err => {
              reject()
            });
          } else {
            resolve();
          }
        });
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'number','status','type','clientId','contactId','accessMethod','correspondentName','emergencyLevel','customerServiceName','declarationTime','annex'))
      },
      contactCondition() {
        return "tb_contact,name,id,client_id='"+this.client.id+"'";
      },
      deviceNumberCondition(){
       return "tb_device_number,name,id,client_id='"+this.client.id+"'";
      },
      addRowDetail () {
        this.model.workOrderDetailList.push({});
        this.$forceUpdate();
      },
      delRowDetail (index) {
        this.model.workOrderDetailList.splice(index,1);
        this.$forceUpdate();
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
              this.initContactOptions();
              this.initDeviceNumberOptions();
              this.form.setFieldsValue({
                clientId: this.client.id
              });
            }
          });
        }
      },
      searchCustomerService(val) {
         getAction(this.url.listCustomerService+"?roleCode=customer_service",{pageSize:this.listSize,realname:val}).then((res) => {
          if (res.success) {
            this.customerServiceList = res.result.records;
          }
        });
      },
      selectContact(value) {
        if (value) {
          this.disabledWrite = true;
        } else {
          this.disabledWrite = false;
          this.contactName = null;
          this.contactMobile = null;
          return;
        }
        getAction(this.url.getContactById+"?id="+value,null).then((res) => {
          if (res.success) {
            this.contact = res.result;
            this.form.setFieldsValue({
                contactId: this.contact.id
            });
            this.contactMobile = this.contact.mobilePhone;
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
        this.initContactOptions();
        this.initDeviceNumberOptions();
      },
      async initContactOptions(){
        this.options.contactOptions = [{label:'请选择',value:null}];
        let _this = this;
        let contactId;
        if (this.client.id) {
          await ajaxGetDictItems("tb_contact,name,id,client_id='"+this.client.id+"'", null).then((res) => {
            if (res.success) {
              for (let index = 0; index < res.result.length; index++) {
                  _this.options.contactOptions.push({label:res.result[index].text,value:res.result[index].value});
                 if (index === 0) {
                    _this.disabledWrite = true;
                    contactId = res.result[index].value;
                  }
              }
              if (res.result.length === 0) {
                _this.disabledWrite = false;
                _this.contactMobile = null;
              }
            }
          });
            await getAction(_this.url.getContactById+'?id='+contactId,null).then((res) => {
             if (res.success) {
               _this.contactMobile = res.result.mobilePhone;
             }
          });
        }
        this.form.setFieldsValue({
          contactId:contactId,
        });
      },
      initDeviceNumberOptions(){
        this.options.deviceNumberOptions = [];
        this.model.workOrderDetailList[0].deviceNumber = null;
        ajaxGetDictItems("tb_device_number,name,id,client_id='"+this.client.id+"'", null).then((res) => {
          if (res.success) {
            for (let index = 0; index < res.result.length; index++) {
                this.options.deviceNumberOptions.push({label:res.result[index].text,value:res.result[index].value});
                if (index === 0) {
                   this.model.workOrderDetailList[0].deviceNumber = res.result[index].value;
                }
            }
           
          }
        });
        this.$forceUpdate();
      },
      initAccessMethodOptions(){
        ajaxGetDictItems("work_order_access_method", null).then((res) => {
          if (res.success) {
            for (let index = 0; index < res.result.length; index++) {
                this.options.accessMethodOptions.push({label:res.result[index].text,value:res.result[index].value});
            }
          }
        })
      },
      initEmergencyLevelOptions(){
        ajaxGetDictItems("work_order_emergency_level", null).then((res) => {
          if (res.success) {
            for (let index = 0; index < res.result.length; index++) {
                this.options.emergencyLevelOptions.push({label:res.result[index].text,value:res.result[index].value});
            }
          }
        })
      },
      initFaultLocationOptions(){
        ajaxGetDictItems("work_order_detail_fault_location", null).then((res) => {
          if (res.success) {
            for (let index = 0; index < res.result.length; index++) {
                this.options.faultLocationOptions.push({label:res.result[index].text,value:res.result[index].value});
            }
          }
        })
      },
      initNeedDispatchOptions(){
        ajaxGetDictItems("DIC_YES_OR_NOT", null).then((res) => {
          if (res.success) {
            for (let index = 0; index < res.result.length; index++) {
                this.options.needDispatchOptions.push({label:res.result[index].text,value:res.result[index].value});
            }
          }
        })
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
    .ant-layout-footer {
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