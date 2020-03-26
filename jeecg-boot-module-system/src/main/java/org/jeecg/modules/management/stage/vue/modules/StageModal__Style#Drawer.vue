<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="工单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['workOrderType', validatorRules.workOrderType]" :trigger-change="true" dictCode="work_order_type" placeholder="请选择工单类型"/>
        </a-form-item>
        <a-form-item label="阶段编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'stageNumber', validatorRules.stageNumber]" placeholder="请输入阶段编号"></a-input>
        </a-form-item>
        <a-form-item label="阶段名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'stageName', validatorRules.stageName]" placeholder="请输入阶段名称"></a-input>
        </a-form-item>
        <a-form-item label="累计百分比" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'cumulativePercentage', validatorRules.cumulativePercentage]" placeholder="请输入累计百分比" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="排序" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'orderIndex', validatorRules.orderIndex]" placeholder="请输入排序" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="操作规程" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['operatingProcedures', validatorRules.operatingProcedures]" :trigger-change="true" dictCode="tb_operating_procedures,name,id" placeholder="请选择操作规程"/>
        </a-form-item>
        <a-form-item label="需要签到" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'checkIn', validatorRules.checkIn]" placeholder="请输入需要签到"></a-input>
        </a-form-item>
        <a-form-item label="需要签出" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'checkOut', validatorRules.checkOut]" placeholder="请输入需要签出"></a-input>
        </a-form-item>
        <a-form-item label="需要拍照" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'takePicture', validatorRules.takePicture]" placeholder="请输入需要拍照"></a-input>
        </a-form-item>
        <a-form-item label="需要费用模板" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'costTemplate', validatorRules.costTemplate]" placeholder="请输入需要费用模板"></a-input>
        </a-form-item>
        <a-form-item label="需要原件归档" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'archive', validatorRules.archive]" placeholder="请输入需要原件归档"></a-input>
        </a-form-item>
        <a-form-item label="资料状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'stateInformation', validatorRules.stateInformation]" placeholder="请输入资料状态"></a-input>
        </a-form-item>
        <a-form-item label="触发消息" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'triggerMessage', validatorRules.triggerMessage]" placeholder="请输入触发消息"></a-input>
        </a-form-item>
        <a-form-item label="触发应收" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'triggerReceivable', validatorRules.triggerReceivable]" placeholder="请输入触发应收"></a-input>
        </a-form-item>
        <a-form-item label="提交附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'attachment', validatorRules.attachment]" placeholder="请输入提交附件"></a-input>
        </a-form-item>
        <a-form-item label="工作说明" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'jobDescription', validatorRules.jobDescription]" placeholder="请输入工作说明"></a-input>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "StageModal",
    components: { 
      JDictSelectTag,
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
        validatorRules: {
          workOrderType: {rules: [
            {required: true, message: '请输入工单类型!'},
          ]},
          stageNumber: {rules: [
            {required: true, message: '请输入阶段编号!'},
            { validator: (rule, value, callback) => validateDuplicateValue('tb_stage', 'stage_number', value, this.model.id, callback)},
          ]},
          stageName: {rules: [
            {required: true, message: '请输入阶段名称!'},
            { validator: (rule, value, callback) => validateDuplicateValue('tb_stage', 'stage_name', value, this.model.id, callback)},
          ]},
          cumulativePercentage: {rules: [
            {required: true, message: '请输入累计百分比!'},
           {pattern:/^-?\d+\.?\d*$/, message: '请输入数字!'},
          ]},
          orderIndex: {rules: [
           {pattern:/^-?\d+\.?\d*$/, message: '请输入数字!'},
          ]},
          operatingProcedures: {rules: [
          ]},
          checkIn: {rules: [
          ]},
          checkOut: {rules: [
          ]},
          takePicture: {rules: [
          ]},
          costTemplate: {rules: [
          ]},
          archive: {rules: [
          ]},
          stateInformation: {rules: [
          ]},
          triggerMessage: {rules: [
          ]},
          triggerReceivable: {rules: [
          ]},
          attachment: {rules: [
          ]},
          jobDescription: {rules: [
          ]},
        },
        url: {
          add: "/stage/stage/add",
          edit: "/stage/stage/edit",
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
          this.form.setFieldsValue(pick(this.model,'workOrderType','stageNumber','stageName','cumulativePercentage','orderIndex','operatingProcedures','checkIn','checkOut','takePicture','costTemplate','archive','stateInformation','triggerMessage','triggerReceivable','attachment','jobDescription'))
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
        this.form.setFieldsValue(pick(row,'workOrderType','stageNumber','stageName','cumulativePercentage','orderIndex','operatingProcedures','checkIn','checkOut','takePicture','costTemplate','archive','stateInformation','triggerMessage','triggerReceivable','attachment','jobDescription'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>