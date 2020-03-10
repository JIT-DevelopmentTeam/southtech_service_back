<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :span="12">
            <a-form-item label="编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'number', validatorRules.number]" placeholder="请输入编码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['type']" :trigger-change="true" dictCode="client_type" placeholder="请选择类型"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['sourceId']" :trigger-change="true" dictCode="client_source" placeholder="请选择来源"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="省" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'province', validatorRules.province]" placeholder="请输入省"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="市" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'city', validatorRules.city]" placeholder="请输入市"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="区" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'area', validatorRules.area]" placeholder="请输入区"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="镇" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'community', validatorRules.community]" placeholder="请输入镇"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="详细地址" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['address']" rows="4" placeholder="请输入详细地址"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="所属用户" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['userId']" :trigger-change="true"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="最近联系时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择最近联系时间" v-decorator="[ 'lastContactTime', validatorRules.lastContactTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="行业" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['industry']" :trigger-change="true" dictCode="client_industry" placeholder="请选择行业"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="社会信用代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'creditCode', validatorRules.creditCode]" placeholder="请输入社会信用代码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="性质" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['property']" :trigger-change="true" dictCode="client_property" placeholder="请选择性质"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="法人代表" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'legalPerson', validatorRules.legalPerson]" placeholder="请输入法人代表"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="注册资金" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'registeredCapital', validatorRules.registeredCapital]" placeholder="请输入注册资金" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="成立日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择成立日期" v-decorator="[ 'establishmentDate', validatorRules.establishmentDate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="网址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'webSite', validatorRules.webSite]" placeholder="请输入网址"></a-input>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="联系人信息" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="contactTable.loading"
            :columns="contactTable.columns"
            :dataSource="contactTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
        <a-tab-pane tab="设备编号" :key="refKeys[1]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[1]"
            :loading="deviceNumberTable.loading"
            :columns="deviceNumberTable.columns"
            :dataSource="deviceNumberTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </a-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import JDate from '@/components/jeecg/JDate'  
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: 'ClientModal',
    mixins: [JEditableTableMixin],
    components: {
      JDate,
      JSelectUserByDep,
      JDictSelectTag,
    },
    data() {
      return {
        labelCol: {
          span: 6
        },
        wrapperCol: {
          span: 16
        },
        labelCol2: {
          span: 3
        },
        wrapperCol2: {
          span: 20
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          number: { rules: [{ required: true, message: '请输入编码!' }] },
          name: { rules: [{ required: true, message: '请输入名称!' }] },
          type: { rules: [{ required: true, message: '请输入类型!' }] },
          sourceId: { rules: [{ required: true, message: '请输入来源!' }] },
          province: { rules: [{ required: true, message: '请输入省!' }] },
          city: { rules: [{ required: true, message: '请输入市!' }] },
          area: { rules: [{ required: true, message: '请输入区!' }] },
          community: { rules: [{ required: true, message: '请输入镇!' }] },
          address:{},
          userId: { rules: [{ required: true, message: '请输入所属用户!' }] },
          lastContactTime:{},
          industry:{},
          creditCode:{},
          property:{},
          legalPerson:{},
          registeredCapital:{},
          establishmentDate:{},
          webSite:{},
        },
        refKeys: ['contact', 'deviceNumber', ],
        tableKeys:['contact', 'deviceNumber', ],
        activeKey: 'contact',
        // 联系人信息
        contactTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '名称',
              key: 'name',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '性别',
              key: 'sex',
              type: FormTypes.select,
              dictCode:"contact_sex",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '部门',
              key: 'department',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '职务',
              key: 'position',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '手机',
              key: 'mobilePhone',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '电话',
              key: 'phone',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '微信号',
              key: 'wechatNumber',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: 'qq号',
              key: 'qqNumber',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '电子邮件',
              key: 'email',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        // 设备编号
        deviceNumberTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '编号',
              key: 'number',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '名称',
              key: 'name',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '类型',
              key: 'type',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '描述',
              key: 'description',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '数量',
              key: 'quantity',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: 'ERP客户编号',
              key: 'erpClientNum',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/client/client/add",
          edit: "/client/client/edit",
          contact: {
            list: '/client/client/queryContactByMainId'
          },
          deviceNumber: {
            list: '/client/client/queryDeviceNumberByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'number','name','type','sourceId','province','city','area','community','address','userId','lastContactTime','industry','creditCode','property','legalPerson','registeredCapital','establishmentDate','webSite')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.contact.list, params, this.contactTable)
          this.requestSubTableData(this.url.deviceNumber.list, params, this.deviceNumberTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          contactList: allValues.tablesValue[0].values,
          deviceNumberList: allValues.tablesValue[1].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'number','name','type','sourceId','province','city','area','community','address','userId','lastContactTime','industry','creditCode','property','legalPerson','registeredCapital','establishmentDate','webSite'))
     },

    }
  }
</script>

<style scoped>
</style>