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
        <a-row class="form-row" :gutter="16">
          <a-col :lg="8">
            <a-form-item label="服务工程师" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['serviceEngineerName',validatorRules.serviceEngineerName]" :multi="false" :trigger-change="true"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="派工时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择派工时间" v-decorator="[ 'declarationTime', validatorRules.declarationTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
    import { httpAction,getAction } from '@/api/manage'
    import pick from 'lodash.pick'
    import JDate from '@/components/jeecg/JDate' 
    import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'

    export default {
    name: "DispatchModal",
    components: {
      JDate,
      JSelectUserByDep
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
        serviceEngineerName:{rules: [{ required: true, message: '请选择服务工程师!' }]},
        dispatchTime:{rules: [{ required: true, message: '请选择派工时间!' }]},
        },
        url: {
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
          this.form.setFieldsValue(pick(this.model,'serviceEngineerName','dispatchTime'))
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
        this.form.setFieldsValue(pick(row,'serviceEngineerName','dispatchTime'))
      },


    }
  }
</script>

<style lang="scss" scoped>

</style>