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

        <a-form-item label="回访人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-user-by-dep v-decorator="['visitPeople', validatorRules.visitPeople]" :trigger-change="true"/>
        </a-form-item>
        <a-form-item label="回访时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择回访时间" v-decorator="[ 'visitTime', validatorRules.visitTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'customer', validatorRules.customer]" placeholder="请输入客户"></a-input>
        </a-form-item>
        <a-form-item label="受访人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'respondents', validatorRules.respondents]" placeholder="请输入受访人"></a-input>
        </a-form-item>
        <a-form-item label="是否完成" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'isCompleted', validatorRules.isCompleted]" placeholder="请输入是否完成"></a-input>
        </a-form-item>
        <a-form-item label="回访评分" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'score', validatorRules.score]" placeholder="请输入回访评分"></a-input>
        </a-form-item>
        <a-form-item label="回访方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['visitWay', validatorRules.visitWay]" :trigger-change="true" dictCode="service_visits_way" placeholder="请选择回访方式"/>
        </a-form-item>
        <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contact', validatorRules.contact]" placeholder="请输入联系电话"></a-input>
        </a-form-item>
        <a-form-item label="回访意见" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'opinion', validatorRules.opinion]" placeholder="请输入回访意见"></a-input>
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
  import JDate from '@/components/jeecg/JDate'  
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "ServiceVisitsModal",
    components: { 
      JDate,
      JSelectUserByDep,
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
          visitPeople: {rules: [
          ]},
          visitTime: {rules: [
          ]},
          customer: {rules: [
          ]},
          respondents: {rules: [
          ]},
          isCompleted: {rules: [
          ]},
          score: {rules: [
          ]},
          visitWay: {rules: [
            {required: true, message: '请输入回访方式!'},
          ]},
          contact: {rules: [
          ]},
          opinion: {rules: [
          ]},
        },
        url: {
          add: "/servicevisits/serviceVisits/add",
          edit: "/servicevisits/serviceVisits/edit",
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
          this.form.setFieldsValue(pick(this.model,'visitPeople','visitTime','customer','respondents','isCompleted','score','visitWay','contact','opinion'))
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
        this.form.setFieldsValue(pick(row,'visitPeople','visitTime','customer','respondents','isCompleted','score','visitWay','contact','opinion'))
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