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
              <a-input v-decorator="[ 'number', validatorRules.number]" placeholder="请输入编码"></a-input>
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
          <a-col :lg="8">
             <a-form-item label="省" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input @blur="getLocationByAddress" v-decorator="[ 'province', validatorRules.province]" placeholder="请输入省"></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="市" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input @blur="getLocationByAddress" v-decorator="[ 'city', validatorRules.city]" placeholder="请输入市"></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="区" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input @blur="getLocationByAddress" v-decorator="[ 'area', validatorRules.area]" placeholder="请输入区"></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="镇/街道" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input @blur="getLocationByAddress" v-decorator="[ 'community', validatorRules.community]" placeholder="请输入镇/街道"></a-input>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row class="form-row" :gutter="16">
          <a-col :lg="32">
            <a-form-item label="详细地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea @blur="getLocationByAddress" style="resize: none;" v-decorator="['address']" rows="4" placeholder="请输入详细地址"/>
            </a-form-item>
          </a-col>
        </a-row>
        
        <a-row class="form-row" :gutter="16">
          <a-col :lg="8">
             <a-form-item label="最近联系时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择最近联系时间" v-decorator="[ 'lastContactTime', validatorRules.lastContactTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="行业" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['industry']" :trigger-change="true" dictCode="client_industry" placeholder="请选择行业"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="社会信用代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'creditCode', validatorRules.creditCode]" placeholder="请输入社会信用代码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="性质" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['property']" :trigger-change="true" dictCode="client_property" placeholder="请选择性质"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="法人代表" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'legalPerson', validatorRules.legalPerson]" placeholder="请输入法人代表"></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="注册资金" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'registeredCapital', validatorRules.registeredCapital]" placeholder="请输入注册资金" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="成立日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择成立日期" v-decorator="[ 'establishmentDate', validatorRules.establishmentDate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="网址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'webSite', validatorRules.webSite]" placeholder="请输入网址"></a-input>
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
        sourceId:{rules: [{ required: true, message: '请选择来源!' }]},
        province:{rules: [{ required: true, message: '请输入省!' }]},
        city:{rules: [{ required: true, message: '请输入市!' }]},
        area:{rules: [{ required: true, message: '请输入区!' }]},
        community:{rules: [{ required: true, message: '请输入镇/街道!' }]},
        address:{},
        userId:{rules: [{ required: true, message: '请选择所属用户!' }]},
        lastContactTime:{},
        industry:{},
        creditCode:{},
        property:{},
        legalPerson:{},
        registeredCapital:{},
        establishmentDate:{},
        webSite:{
          rules:[
            {
                pattern: /^(?:([A-Za-z]+):)?(\/{0,3})([0-9.\-A-Za-z]+)(?::(\d+))?(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/,
                message: '请输入正确格式的网址!'
            }
            ]
        },
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
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'number','name','type','sourceId','province','city','area','community','address','userId','lastContactTime','industry','creditCode','property','legalPerson','registeredCapital','establishmentDate','webSite','longitude','latitude'))
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
        this.form.setFieldsValue(pick(row,'number','name','type','sourceId','province','city','area','community','address','userId','lastContactTime','industry','creditCode','property','legalPerson','registeredCapital','establishmentDate','webSite','longitude','latitude'))
      },
      getLocationByAddress() {
        let formValus = this.form.getFieldsValue();
        if (!formValus.province || !formValus.city || !formValus.area || !formValus.community || !formValus.address) {
          return;
        }
        let fullAddress = formValus.province + formValus.city + formValus.area + formValus.community + formValus.address;
        getAction(this.url.getLocationByAddress,{address:fullAddress}).then((res) => {
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