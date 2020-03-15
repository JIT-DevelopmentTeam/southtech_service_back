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
        <a-form-item label="工作说明" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-decorator="['jobDescription',{trigger:'input'}]"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JEditor from '@/components/jeecg/JEditor'

  export default {
    name: "StageModal",
    components: { 
      JDictSelectTag,
      JEditor,
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
          this.form.setFieldsValue(pick(this.model,'workOrderType','stageNumber','stageName','cumulativePercentage','orderIndex','operatingProcedures','jobDescription'))
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
        this.form.setFieldsValue(pick(row,'workOrderType','stageNumber','stageName','cumulativePercentage','orderIndex','operatingProcedures','jobDescription'))
      },

      
    }
  }
</script>