<template>
    <a-spin :spinning="confirmLoading">
      <a-form 
        :form="form"
        :title="title"
        :visible="visible"
        :confirmLoading="confirmLoading"
        @ok="handleOk"
      >
        <a-row class="form-row" :gutter="16">
          <a-col :lg="6">
            <a-form-item label="编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'number', validatorRules.number]" placeholder="请输入编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="6">
            <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-dict-select-tag type="list" :disabled="true" v-decorator="['type',validatorRules.type]" :trigger-change="true" dictCode="work_order_type" placeholder="请选择类型"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6">
            <a-form-item label="客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
               <j-search-select-tag
                placeholder="请选择客户"
                v-decorator="['clientId',validatorRules.clientId]"
                dict="tb_client,name,id"
                @change="selectClient"
                :async="true">
              </j-search-select-tag>
            </a-form-item>
          </a-col>
          <a-col :lg="6">
           <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag v-decorator="['contactId',validatorRules.contactId]" placeholder="请选择联系人" :trigger-change="true" :dictCode="contactCondition()"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6">
           <a-form-item label="接入方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['accessMethod',validatorRules.accessMethod]" :trigger-change="true" dictCode="work_order_access_method" placeholder="请选择接入方式"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6">
           <a-form-item label="代报人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['correspondentName',validatorRules.correspondentName]" :multi="false" :trigger-change="true"/>
            </a-form-item>
          </a-col>
           <a-col :lg="6">
            <a-form-item label="紧急程度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['emergencyLevel',validatorRules.emergencyLevel]" :trigger-change="true" dictCode="work_order_emergency_level" placeholder="请选择紧急程度"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6">
            <a-form-item label="客服" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-select-user-by-dep v-decorator="['customerServiceName',validatorRules.customerServiceName]" :multi="false" :trigger-change="true"/>
              </a-form-item>
          </a-col>
          <a-col :lg="6">
            <a-form-item label="申报时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择申报时间" v-decorator="[ 'declarationTime', validatorRules.declarationTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :lg="6">
            <a-form-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-upload v-decorator="['annex']" :trigger-change="true"></j-upload>
            </a-form-item>
          </a-col>
        </a-row>

        <!-- 子表单区域 -->
        <a-tabs defaultActiveKey="1" >
          <a-tab-pane tab="工单明细" key="1">
            <div>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="5">设备编号</a-col>
                <a-col :span="7">故障部位</a-col>
                <a-col :span="9">描述</a-col>
                <a-col :span="2">操作</a-col>
              </a-row>

              <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, index) in model.workOrderDetailList" :key="index">
                <a-col :span="5">
                  <a-form-item>
                    <j-dict-select-tag v-decorator="['workOrderDetailList['+index+'].deviceNumber', {'initialValue':item.deviceNumber,rules: [{ required: true, message: '请选择设备编号!' }]}]" placeholder="设备编号" :trigger-change="true" :dictCode="deviceNumberCondition()"/>
                  </a-form-item>
                </a-col>
                <a-col :span="7">
                  <a-form-item>
                    <j-multi-select-tag placeholder="故障部位" v-decorator="['workOrderDetailList['+index+'].faultLocation', {'initialValue':item.faultLocation,rules: [{ required: true, message: '请选择故障部位!' }]}]" dictCode="work_order_detail_fault_location"/>
                  </a-form-item>
                </a-col>
                <a-col :span="9">
                  <a-form-item>
                    <a-input v-decorator="['workOrderDetailList['+index+'].description', {'initialValue':item.description}]" placeholder="描述"/>
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
         <div id="components-layout-demo-basic">
            <a-layout>
            <a-layout-footer><a-button @click="handleOk" type="primary" icon="plus">申报</a-button></a-layout-footer>
            </a-layout>
         </div>
      </a-form>
    </a-spin>
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

  export default {
    name: "FailureDeclarationPage",
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

        confirmLoading: false,
        validatorRules:{
        number:{rules: [{ required: true, message: '请输入编号!' }]},
        status:{rules: [{ required: true, message: '请输入状态!' }]},
        type:{rules: [{ required: true, message: '请输入类型!' }]},
        clientId:{rules: [{ required: true, message: '请输入客户!' }]},
        contactId:{rules: [{ required: true, message: '请输入联系人!' }]},
        accessMethod:{rules: [{ required: true, message: '请输入接入方式!' }]},
        correspondentName:{},
        emergencyLevel:{rules: [{ required: true, message: '请输入紧急程度!' }]},
        customerServiceName:{rules: [{ required: true, message: '请输入客服!' }]},
        declarationTime:{rules: [{ required: true, message: '请输入申报时间!' }]},
        },
        url: {
          add: "/workorder/workOrder/add",
          edit: "/workorder/workOrder/edit",
          workOrderDetailList: "/workorder/workOrder/queryWorkOrderDetailListByMainId",
        },
        client:null,
      }
    },
    created () {
    },
    mounted () {
        this.form.resetFields();
        this.model.type = "1";
        this.model.workOrderDetailList = [{}];
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'number','status','type','clientId','contactId','accessMethod','correspondentName','emergencyLevel','customerServiceName','declarationTime','annex'))
        })
    },
    methods: {
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
              this.model.workOrderDetailList = [{}];
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
        return "tb_contact,name,id,client_id="+this.client;
      },
      deviceNumberCondition(){
        return "tb_device_number,name,id,client_id="+this.client;
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
        this.client = id;
      }
    }
  }
</script>
<style>
    #components-layout-demo-basic {
        text-align: right;
    }
    #components-layout-demo-basic .ant-layout-header,
    #components-layout-demo-basic .ant-layout-footer {
        color: #fff;
    }
    #components-layout-demo-basic .ant-layout-footer {
        line-height: 1.5;
    }
    #components-layout-demo-basic .ant-layout-sider {
        background: #3ba0e9;
        color: #fff;
        line-height: 120px;
    }
    #components-layout-demo-basic .ant-layout-content {
        background: rgba(16, 142, 233, 1);
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