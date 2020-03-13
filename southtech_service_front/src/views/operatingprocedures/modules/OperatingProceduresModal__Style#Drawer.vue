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

        <a-form-item label="规程编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'operatingNumber', validatorRules.operatingNumber]" placeholder="请输入规程编号"></a-input>
        </a-form-item>
        <a-form-item label="规程名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入规程名称"></a-input>
        </a-form-item>
        <a-form-item label="必须拍照" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['takePicture', validatorRules.takePicture]" :trigger-change="true" dictCode="DIC_YES_OR_NOT" placeholder="请选择必须拍照"/>
        </a-form-item>
        <a-form-item label="工单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['orderType', validatorRules.orderType]" :trigger-change="true" dictCode="work_order_type" placeholder="请选择工单类型"/>
        </a-form-item>
        <a-form-item label="检查要求" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'inspectionRequirements', validatorRules.inspectionRequirements]" placeholder="请输入检查要求"></a-input>
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
    name: "OperatingProceduresModal",
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
          operatingNumber: {rules: [
            {required: true, message: '请输入规程编号!'},
            { validator: (rule, value, callback) => validateDuplicateValue('tb_operating_procedures', 'operating_number', value, this.model.id, callback)},
          ]},
          name: {rules: [
            {required: true, message: '请输入规程名称!'},
            { validator: (rule, value, callback) => validateDuplicateValue('tb_operating_procedures', 'name', value, this.model.id, callback)},
          ]},
          takePicture: {rules: [
            {required: true, message: '请输入必须拍照!'},
          ]},
          orderType: {rules: [
            {required: true, message: '请输入工单类型!'},
          ]},
          inspectionRequirements: {rules: [
            {required: true, message: '请输入检查要求!'},
          ]},
        },
        url: {
          add: "/operatingprocedures/operatingProcedures/add",
          edit: "/operatingprocedures/operatingProcedures/edit",
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
          this.form.setFieldsValue(pick(this.model,'operatingNumber','name','takePicture','orderType','inspectionRequirements'))
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
        this.form.setFieldsValue(pick(row,'operatingNumber','name','takePicture','orderType','inspectionRequirements'))
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