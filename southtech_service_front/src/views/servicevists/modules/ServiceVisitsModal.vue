<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <div class="modalContain">
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">
          <a-row>
            <a-col :span="3" >
              <a-form-item><a-button @click="saveData" type="primary">保存</a-button></a-form-item>
              
            </a-col>
          </a-row>

          <a-row>
            <a-col :span="8">
              <a-form-item label="工单单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  v-decorator="[ 'workOrderId', validatorRules.workOrderId]"
                  placeholder="请输入工单单号"
                ></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label="回访人" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-select-user-by-dep
                  v-decorator="['visitPeople', validatorRules.visitPeople]"
                  :trigger-change="true"
                />
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label="回访时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-date
                  placeholder="请选择回访时间"
                  v-decorator="[ 'visitTime', validatorRules.visitTime]"
                  :trigger-change="true"
                  :show-time="true"
                  date-format="YYYY-MM-DD HH:mm:ss"
                  style="width: 100%"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="8">
              <a-form-item label="客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-search-select-tag
                  v-decorator="['customer', validatorRules.customer]"
                  dict="tb_client,name,id"
                />
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label="受访人" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  v-decorator="[ 'respondents', validatorRules.respondents]"
                  placeholder="请输入受访人"
                ></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label="是否完成" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-dict-select-tag
                  type="list"
                  v-decorator="['isCompleted', validatorRules.isCompleted]"
                  :trigger-change="true"
                  dictCode="DIC_YES_OR_NOT"
                  placeholder="请选择是否完成"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="8">
              <a-form-item label="回访评分" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-rate allowHalf v-model="rateValue" v-decorator="[ 'score', validatorRules.score]" />
                <span>{{rateValue}}分</span>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label="回访方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-dict-select-tag
                  type="list"
                  v-decorator="['visitWay', validatorRules.visitWay]"
                  :trigger-change="true"
                  dictCode="service_visits_way"
                  placeholder="请选择回访方式"
                />
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-decorator="[ 'contact', validatorRules.contact]" placeholder="请输入联系电话"></a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row type="flex" justify="start">
            <a-col :span="24">
              <a-form-item label="回访意见" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-editor v-decorator="['opinion',{trigger:'input'}]" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-spin>
      <ServiceViitsList ref="ServiceViitsList" :workOrderNum="workOrder.number" />
    </div>
  </a-modal>
</template>

<script>
import { httpAction } from '@/api/manage'
import pick from 'lodash.pick'
import { validateDuplicateValue } from '@/utils/util'
import JDate from '@/components/jeecg/JDate'
import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import JEditor from '@/components/jeecg/JEditor'
import ServiceViitsList from '../ServiceVisitsList'

export default {
  name: 'ServiceVisitsModal',
  components: {
    JDate,
    JSelectUserByDep,
    JDictSelectTag,
    JSearchSelectTag,
    JEditor,
    ServiceViitsList
  },
  props: {
    workOrder: {
      type: Object
    }
  },
  data() {
    return {
      form: this.$form.createForm(this),
      title: '操作',
      width: 1300,
      visible: false,
      model: {},
      rateValue: 0,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      confirmLoading: false,
      validatorRules: {
        visitPeople: { rules: [{ required: true, message: '请输入回访人!' }] },
        visitTime: { rules: [{ required: true, message: '请输入回访时间!' }] },
        customer: { rules: [{ required: true, message: '请输入客户!' }] },
        respondents: { rules: [{ required: true, message: '请输入受访人!' }] },
        isCompleted: {
          rules: [{ required: true, message: '请输入是否完成!' }]
        },
        score: { rules: [{ required: true, message: '请输入回访评分!' }] },
        visitWay: { rules: [{ required: true, message: '请输入回访方式!' }] },
        contact: { rules: [{ required: true, message: '请输入联系电话!' }] },
        opinion: { rules: [] },
        workOrderId: { rules: [{ required: true, message: '请输入工单单号!' }] }
      },
      url: {
        add: '/servicevisits/serviceVisits/add',
        edit: '/servicevisits/serviceVisits/edit'
      }
    }
  },
  created() {},
  methods: {
    add() {
      this.edit({})
      this.$refs.ServiceViitsList.LoadData()
    },
    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      console.log(this.workOrder)
      this.model.workOrderId = this.workOrder.number
      this.model.customer = this.workOrder.clientId
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(
            this.model,
            'visitPeople',
            'visitTime',
            'customer',
            'respondents',
            'isCompleted',
            'score',
            'visitWay',
            'contact',
            'opinion',
            'workOrderId'
          )
        )
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleOk() {
      this.close()
    },
    saveData() {
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          let formData = Object.assign(this.model, values)
          console.log('表单提交数据', formData)
          httpAction(httpurl, formData, method)
            .then(res => {
              if (res.success) {
                that.$message.success(res.message)
              } else {
                that.$message.warning(res.message)
              }
            })
            .finally(() => {
              console.log(this.$refs.ServiceViitsList)
              this.$refs.ServiceViitsList.LoadData()
              that.confirmLoading = false
            })
        }
      })
    },
    handleCancel() {
      this.close()
    },
    popupCallback(row) {
      this.form.setFieldsValue(
        pick(
          row,
          'visitPeople',
          'visitTime',
          'customer',
          'respondents',
          'isCompleted',
          'score',
          'visitWay',
          'contact',
          'opinion',
          'workOrderId'
        )
      )
    }
  }
}
</script>
<style scoped>
.modalContain {
  height: 100vh;
  overflow-y: auto;
}
.ant-col-sm-5 {
  width: auto !important;
}
</style>