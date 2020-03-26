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
      <a-form :form="form">

        <a-form-item label="设备档案" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['deviceNumber']" :trigger-change="true" dictCode="" placeholder="请选择设备档案"/>
        </a-form-item>
        <a-form-item label="服务工程师" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'serviceEngineerId', validatorRules.serviceEngineerId]" placeholder="请输入服务工程师"></a-input>
        </a-form-item>
        <a-form-item label="故障部位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag type="list_multi" v-decorator="['faultLocation']" :trigger-change="true" dictCode="" placeholder="请选择故障部位"/>
        </a-form-item>
        <a-form-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'description', validatorRules.description]" placeholder="请输入描述"></a-input>
        </a-form-item>
        <a-form-item label="派工时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择派工时间" v-decorator="[ 'dispatchTime', validatorRules.dispatchTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="同行人员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['peers']" :trigger-change="true" dictCode="" placeholder="请选择同行人员"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JMultiSelectTag from "@/components/dict/JMultiSelectTag"

  export default {
    name: "WorkOrderDetailModal",
    components: {
      JDate,
      JDictSelectTag,
      JMultiSelectTag,
    },
    props:{
      mainId:{
        type:String,
        required:false,
        default:''
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
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
        deviceNumber:{rules: [{ required: true, message: '请输入设备档案!' }]},
        serviceEngineerId:{rules: [{ required: true, message: '请输入服务工程师!' }]},
        faultLocation:{rules: [{ required: true, message: '请输入故障部位!' }]},
        description:{},
        dispatchTime:{rules: [{ required: true, message: '请输入派工时间!' }]},
        peers:{},
        },
        url: {
          add: "/workorder/workOrder/addWorkOrderDetail",
          edit: "/workorder/workOrder/editWorkOrderDetail",
        }

      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'createBy','createTime','updateBy','updateTime','sysOrgCode','deviceNumber','serviceEngineerId','faultLocation','description','dispatchTime','peers','workOrderId'))
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
            let formData = Object.assign(this.model, values);
            formData['workOrderId'] = this.mainId
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
        this.form.setFieldsValue(pick(row,'createBy','createTime','updateBy','updateTime','sysOrgCode','deviceNumber','serviceEngineerId','faultLocation','description','dispatchTime','peers','workOrderId'))
      },


    }
  }
</script>
