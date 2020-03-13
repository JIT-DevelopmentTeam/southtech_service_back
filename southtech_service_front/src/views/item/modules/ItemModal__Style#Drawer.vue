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

        <a-form-item label="物料名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入物料名称"></a-input>
        </a-form-item>
        <a-form-item label="物料编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'number', validatorRules.number]" placeholder="请输入物料编码"></a-input>
        </a-form-item>
        <a-form-item label="规格型号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'model', validatorRules.model]" placeholder="请输入规格型号"></a-input>
        </a-form-item>
        <a-form-item label="计量单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'uname', validatorRules.uname]" placeholder="请输入计量单位"></a-input>
        </a-form-item>
        <a-form-item label="所在仓位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sname', validatorRules.sname]" placeholder="请输入所在仓位"></a-input>
        </a-form-item>
        <a-form-item label="保修期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'ikfperiod', validatorRules.ikfperiod]" placeholder="请输入保修期"></a-input>
        </a-form-item>
        <a-form-item label="modifyTime" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'modifytime', validatorRules.modifytime]" placeholder="请输入modifyTime"></a-input>
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
  
  export default {
    name: "ItemModal",
    components: { 
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
          name: {rules: [
          ]},
          number: {rules: [
          ]},
          model: {rules: [
          ]},
          uname: {rules: [
          ]},
          sname: {rules: [
          ]},
          ikfperiod: {rules: [
          ]},
          modifytime: {rules: [
          ]},
        },
        url: {
          add: "/item/item/add",
          edit: "/item/item/edit",
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
          this.form.setFieldsValue(pick(this.model,'name','number','model','uname','sname','ikfperiod','modifytime'))
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
        this.form.setFieldsValue(pick(row,'name','number','model','uname','sname','ikfperiod','modifytime'))
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