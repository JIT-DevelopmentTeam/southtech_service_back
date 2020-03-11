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

        <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入名称"></a-input>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['sex',validatorRules.sex]" :trigger-change="true" dictCode="contact_sex" placeholder="请选择性别"/>
        </a-form-item>
        <a-form-item label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'department', validatorRules.department]" placeholder="请输入部门"></a-input>
        </a-form-item>
        <a-form-item label="职务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'position', validatorRules.position]" placeholder="请输入职务"></a-input>
        </a-form-item>
        <a-form-item label="手机" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'mobilePhone', validatorRules.mobilePhone]" placeholder="请输入手机"></a-input>
        </a-form-item>
        <a-form-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'phone', validatorRules.phone]" placeholder="请输入电话"></a-input>
        </a-form-item>
        <a-form-item label="微信号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'wechatNumber', validatorRules.wechatNumber]" placeholder="请输入微信号"></a-input>
        </a-form-item>
        <a-form-item label="qq号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'qqNumber', validatorRules.qqNumber]" placeholder="请输入qq号"></a-input>
        </a-form-item>
        <a-form-item label="电子邮件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'email', validatorRules.email]" placeholder="请输入电子邮件"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "ContactModal",
    components: {
      JDictSelectTag,
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
        name:{rules: [{ required: true, message: '请输入名称!' }]},
        sex:{rules: [{ required: true, message: '请输入性别!' }]},
        department:{},
        position:{},
        mobilePhone:{rules: [{ required: true, message: '请输入正确格式手机!',pattern: /^1[3456789]\d{9}$/ }]},
        phone:{},
        wechatNumber:{},
        qqNumber:{},
        email:{rules:[{pattern: /^([\w]+\.*)([\w]+)@[\w]+\.\w{3}(\.\w{2}|)$/,message:'请输入正确格式邮箱!'}]},
        },
        url: {
          add: "/client/client/addContact",
          edit: "/client/client/editContact",
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
        if (!record.id) {
          record.sex = '1';
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'createBy','createTime','updateBy','updateTime','sysOrgCode','name','sex','department','position','mobilePhone','phone','wechatNumber','qqNumber','email','clientId'))
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
            formData['clientId'] = this.mainId
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
        this.form.setFieldsValue(pick(row,'createBy','createTime','updateBy','updateTime','sysOrgCode','name','sex','department','position','mobilePhone','phone','wechatNumber','qqNumber','email','clientId'))
      },


    }
  }
</script>
