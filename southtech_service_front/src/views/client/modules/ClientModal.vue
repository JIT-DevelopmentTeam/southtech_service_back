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
            <a-form-item label="编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input :disabled="true" v-decorator="[ 'number', validatorRules.number]" placeholder="请输入编码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['type',validatorRules.type]" :trigger-change="true" dictCode="client_type" placeholder="请选择类型"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['sourceId',validatorRules.sourceId]" :trigger-change="true" dictCode="client_source" placeholder="请选择来源"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
             <a-form-item label="所属用户" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['userId',validatorRules.userId]"  :trigger-change="true" :multi="false"/>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row class="form-row" :gutter="16">
          <a-col :lg="16">
            <a-form-item label="详细地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea @change="getLocationByAddress" style="resize: none;" v-decorator="['address',validatorRules.address]" rows="4" placeholder="请输入详细地址"/>
            </a-form-item>
          </a-col>
        </a-row>
        
        <a-row class="form-row" :gutter="16">
          <a-col :lg="8">
            <a-form-item label="行业" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['industry']" :trigger-change="true" dictCode="client_industry" placeholder="请选择行业"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="性质" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['property']" :trigger-change="true" dictCode="client_property" placeholder="请选择性质"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="16">
           <a-col :lg="8">
            <a-form-item label="经度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'longitude', validatorRules.longitude]" :disabled='true' placeholder="请输入经度" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="纬度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'latitude', validatorRules.latitude]" :disabled='true' placeholder="请输入纬度" style="width: 100%"/>
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
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import {formatDate} from '@/utils/util.js'

  export default {
    name: "ClientModal",
    components: { 
      JDate,
      JSelectUserByDep,
      JDictSelectTag,
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
        number:{rules: [{ required: true, message: '请输入编码!' }]},
        name:{rules: [{ required: true, message: '请输入名称!' }]},
        type:{rules: [{ required: true, message: '请选择类型!' }]},
        sourceId:{},
        address:{rules: [{ required: true, message: '请输入详细地址!' }]},
        userId:{},
        industry:{},
        property:{},
        longitude:{},
        latitude:{},
        },
        url: {
          add: "/client/client/add",
          edit: "/client/client/edit",
          getLocationByAddress:"/amap/amap/getLocationByAddress"
        },
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
          record.type = '1';
          record.number = 'C'+formatDate(Date.parse(new Date()),'yyyyMMddhhmmss');
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'number','name','type','sourceId','address','userId','industry','property','longitude','latitude'))
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
        this.form.setFieldsValue(pick(row,'number','name','type','sourceId','address','userId','lastContactTime','industry','property','longitude','latitude'))
      },
      async getLocationByAddress(e) {
        let address = e.target.value;
        await getAction(this.url.getLocationByAddress,{address:address}).then((res) => {
          if (res.success) {
            this.form.setFieldsValue({
              longitude:res.result.longitude,
              latitude:res.result.latitude
            });
          }
        });
      }
      
    }
  }
</script>