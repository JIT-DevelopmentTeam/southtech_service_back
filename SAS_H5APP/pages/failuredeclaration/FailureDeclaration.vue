<template>
	<view class="uni-padding-wrap">
		<view class="uni-title">
			<text>故障申报</text>
		</view>
		<form @submit="formSubmit" @reset="formReset">
			<view class="uni-flex uni-column">
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>编号:</view>
					<input type="text" :disabled="true" v-model="model.number" class="uni-input" placeholder="编号"/>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>客户</view>
					<button type="primary" v-if="dingTalkUserId" size="mini" @click="openClients">选择客户</button>
					<p><strong>当前选择:</strong>{{ client != null ? client.name : ''}}</p>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>联系人</view>
					<picker v-model="model.contactId" mode ="selector" @change="pickerChange($event.target.value,'contactIndex');" :range="contactList" range-key="text">
						<view class="uni-input">{{ contactList[dataIndex.contactIndex] != null ? contactList[dataIndex.contactIndex].text : '' }}</view>
					</picker>
				</view>
				<!-- <view class="flex-item flex-item-V" v-if="dingTalkUserId">
					<view class="title"><span style="color: red;">*</span>接入方式</view>
					<picker v-model="model.accessMethod" mode ="selector" @change="pickerChange($event.target.value,'accessMethodIndex');" :value="dataIndex.accessMethodIndex" :range="accessMethodList" range-key="text">
						<view class="uni-input">{{ accessMethodList[dataIndex.accessMethodIndex] != null ? accessMethodList[dataIndex.accessMethodIndex].text : '' }}</view>
					</picker>
				</view> -->
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>紧急程度</view>
					<picker v-model="model.emergencyLevel" mode ="selector" @change="pickerChange($event.target.value,'emergencyLevelIndex');" :value="dataIndex.emergencyLevelIndex" :range="emergencyLevelList" range-key="text">
						<view class="uni-input">{{ emergencyLevelList[dataIndex.emergencyLevelIndex] != null ? emergencyLevelList[dataIndex.emergencyLevelIndex].text : '' }}</view>
					</picker>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title">客服</view>
					<picker v-model="model.customerServiceName" mode ="selector" @change="pickerChange($event.target.value,'customerServiceIndex');" :value="dataIndex.customerServiceIndex" :range="customerServiceList" range-key="realname">
						<view class="uni-input">{{ customerServiceList[dataIndex.customerServiceIndex] != null ? customerServiceList[dataIndex.customerServiceIndex].realname : ''}}</view>
					</picker>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>申报时间</view>
					<time-picker
						showType="yearToMinute"
						beginDate="1900-01-01"
						endDate="2500-12-31"
						beginTime="00:00:00"
						endTime="23:59:59"
						class="uni-input"
						@btnConfirm="btnConfirm" 
					>
					<text v-model="model.declarationTime">当前选择:{{declarationTime}}</text>
					</time-picker>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title">附件</view>
					<chooseImage v-model="model.annex" :num="9" :isSave="false" :imageList="imageList" @uploadPhotoSuccess="uploadPhotoSuccess"
					 @deletePhotoSuccess="deletePhotoSuccess" />
				</view>
				<view class="uni-title">
					<text>故障明细</text>
				</view>
				<!-- <view class="flex-item flex-item-V" style="text-align:right;">
					<button type="primary" size="mini" @click="addWorkOrderDetail">添加</button>
				</view> -->
				<view class="flex-item flex-item-V">
					<uni-card title="故障明细" v-for="(workOrderDetail,index) in model.workOrderDetailList">
						<view class="uni-list">
							<view class="uni-list-cell">
								<view class="uni-list-cell-left">
									<span style="color: red;">*</span>设备编号:
								</view>
								<view class="uni-list-cell-db">
									<picker v-model="workOrderDetail.deviceNumber" @change="pickerDeviceNumberChange($event,index);" :range="deviceNumberList" range-key="text">
										<view class="uni-input">{{ deviceNumberList[dataIndex.deviceNumberIndexList[index]] != null ? deviceNumberList[dataIndex.deviceNumberIndexList[index]].text : '' }}</view>
									</picker>
								</view>
							</view>
						</view>
						
						<view class="uni-list">
							<view class="uni-list-cell">
								<view class="uni-list-cell-left">
									<span style="color: red;">*</span>故障部位:
								</view>
								<view class="uni-list-cell-db">
									<checkbox-group @change="selectFaultLocations($event.target.value,index)" v-model="workOrderDetail.faultLocation">
										<label style="display: block;" v-for="(faultLocation,index) in faultLocationList">
											<checkbox :value="faultLocation.value" color="#FFCC33" style="transform:scale(0.7)" />{{faultLocation.text}}
										</label>
									</checkbox-group>
								</view>
							</view>
						</view>
						<view class="uni-list">
							<view class="uni-title uni-common-pl">描述</view>
							<textarea v-model="workOrderDetail.description" auto-height/>
						</view>
						<!-- <template v-slot:footer><button type="warn" size="mini" @click="delWorkOrderDetail(workOrderDetail)">删除</button></template> -->
					</uni-card>
					<view class="flex-item flex-item-V" style="text-align:center;">
						<button type="primary" form-type="submit">提交</button>
					</view>
				</view>
    		</view>
    	</form>
		<uni-popup ref="popup" type="center">
			<view class="uni-flex uni-column" style="background: white;">
				<view class="flex-item flex-item-V">
					<uni-search-bar @input="searchClientByName" ></uni-search-bar>
				</view>
				<view class="flex-item flex-item-V">
					<radio-group @change="selectClient">
						<label class="uni-list-cell uni-list-cell-pd" v-for="(client, index) in clientList" :key="client.id">
							<view>
								<radio :value="client.id" :title="client.name"/>
							</view>
							<view>{{client.name}}</view>
						</label>
					</radio-group>
				</view>
			</view>
		</uni-popup>
    </view>
</template>

<script>
	import uniCard from "@dcloudio/uni-ui/lib/uni-card/uni-card"
	import uniCombox from "@dcloudio/uni-ui/lib/uni-combox/uni-combox"
	import timePicker from '@/components/wing-time-selector/wing-time-selector'
	import chooseImage from '@/components/xyz-choose-image/xyz-choose-image'
	import uniPopup from "@dcloudio/uni-ui/lib/uni-popup/uni-popup"
	import uniSearchBar from '@dcloudio/uni-ui/lib/uni-search-bar/uni-search-bar'
	import validate from '@/components/form/validate'
	import { getDicList,listUserByRoleCode,listClient,getClientById,addWorkOrder,getUserByEnterpriseId,getClientByOpenId } from '@/api/Ticket.js'
	import { formatDateStr } from '@/utils/formatDate.js'
	
	export default {
		name:'FailureDeclaration',
		components:{
			uniCard,
			uniCombox,
			timePicker,
			uniPopup,
			uniSearchBar,
			chooseImage,
			validate
		},
	     async mounted() {
			 // 初始化
			this.dingTalkUserId = sessionStorage.getItem("userId");
			this.wechatOpenId = sessionStorage.getItem("openId");
			if (this.dingTalkUserId) {
				getUserByEnterpriseId(this.dingTalkUserId).then((res) => {
					if (res.data.success) {
						this.model.correspondentName = res.data.result.username;
					}
				});
				listClient(null).then((res) => {
					if (res.data.success) {
						this.clientList = res.data.result.records;
					}
				});
				this.model.accessMethod = '2';
			}
			if (this.wechatOpenId) {
				await getClientByOpenId(this.wechatOpenId).then((res) => {
					if (res.data.success) {
						this.client = res.data.result;
						this.model.clientId = res.data.result.id;
					} 
				});
				await getDicList("tb_contact,name,id,client_id="+this.client.id).then((res) => {
					if (res.data.success) {
				 		this.contactList = res.data.result;
						if (res.data.result.length > 0) {
							this.model.contactId = res.data.result[0].value;
							this.dataIndex.contactIndex = 0;
						}
					}
				});
				await getDicList("tb_device_number,name,id,client_id="+this.client.id).then((res) => {
					if (res.data.success) {
				 		this.deviceNumberList = res.data.result;
						this.model.workOrderDetailList = [];
						if (res.data.result.length > 0) {
							this.model.workOrderDetailList.push({deviceNumber:res.data.result[0].value});
							this.dataIndex.deviceNumberIndexList[0] = 0;
						}
					}
				});
				this.model.accessMethod = '4';
			}
			getDicList("work_order_access_method").then((res) => {
				if (res.data.success) {
					this.accessMethodList = res.data.result;
				}
			});
			getDicList("work_order_emergency_level").then((res) => {
				if (res.data.success) {
					this.emergencyLevelList = res.data.result;
					this.dataIndex.emergencyLevelIndex = 2;
					this.model.emergencyLevel = '3';
				}
			});
			listUserByRoleCode("customer_service").then((res) => {
				if (res.data.success) {
					this.customerServiceList = res.data.result;
				}
			});
			getDicList("work_order_detail_fault_location").then((res) => {
				if (res.data.success) {
					this.faultLocationList = res.data.result;
				}
			});
			this.model.number = 'W'+formatDateStr(Date.parse(new Date()),'yyyyMMddhhmmss');
			this.model.declarationTime = formatDateStr(Date.parse(new Date()),'yyyy-MM-dd hh:mm:ss');
			this.declarationTime = formatDateStr(Date.parse(new Date()),'yyyy-MM-dd hh:mm:ss');
		},
		data() {
			return {
				dataIndex:{
					contactIndex:null,
					accessMethodIndex:null,
					emergencyLevelIndex:null,
					customerServiceIndex:null,
					deviceNumberIndexList:[],
					faultLocationIndex:[]
				},
				checkDetailIndexList:[],
				clientList: [],
				contactList: [],
				accessMethodList: [],
				emergencyLevelList:[],
				customerServiceList:[],
				deviceNumberList:[],
				faultLocationList:[],
				declarationTime:null,
				imageList:[],
				client:{},
				dingTalkUserId:null,
				wechatOpenId:null,
				photoCommit:[],
				picRequestURL: this.$IP + '/mobile/upload/uploadDetailPicture',  // 请求地址
				model: {
					number:'',
					status:1,
					type:1,
					clientId:null,
					contactId:null,
					accessMethod:null,
					emergencyLevel:null,
					customerServiceName:null,
					declarationTime:null,
					correspondentName:null,
					annex:null,
					workOrderDetailList:[],
				}
			}
		},
		computed: {
			
		},
		methods: {
		    formSubmit(e) {
				var fields = [
					{value:this.model.number, checkType:'String', errorMsg:'请输入编号!'},
					{value:this.model.clientId, checkType:'String', errorMsg:'请选择客户!'},
					{value:this.model.contactId, checkType:'String', errorMsg:'请选择联系人!'},
					{value:this.model.accessMethod, checkType:'String', errorMsg:'请选择接入方式!'},
					{value:this.model.emergencyLevel, checkType:'String', errorMsg:'请选择紧急程度!'},
					{value:this.model.declarationTime, checkType:'String', errorMsg:'请选择申报时间!'}
				];
				var checkFields = validate.check(fields)
				if (!checkFields) {
					uni.showToast({
						title:validate.error,
						icon:'none'
					});
					return;
				}
				if (this.model.workOrderDetailList.length == 0) {
					uni.showToast({
						title:'请至少添加一条工单明细!',
						icon:'none'
					});
					return;
				}
				let selectDeviceNumber = true;
				let selectFaultLocations = true;
				for (let i = 0; i < this.model.workOrderDetailList.length; i++) {
					if (this.model.workOrderDetailList[i].deviceNumber == undefined || this.model.workOrderDetailList[i].deviceNumber === '') {
						selectDeviceNumber = false;
					}
					if (this.model.workOrderDetailList[i].faultLocation == undefined || this.model.workOrderDetailList[i].faultLocation === '') {
						selectFaultLocations = false;
					}
				}
				if (!selectDeviceNumber) {
					uni.showToast({
						title:'请选择设备编号!',
						icon:'none'
					});
					return;
				}
				if (!selectFaultLocations) {
					uni.showToast({
						title:'请选择故障部位!',
						icon:'none'
					});
					return;
				}
			    this.savePhoto().then(()=>{
					this.saveWorkOrder();
				});
			},
			formReset: function() {
				
			},
			pickerChange: function(val,fieldName) {
				this.dataIndex[fieldName] = val;
				switch(fieldName) {
					case 'contactIndex':
						this.model.contactId = this.contactList[val].value;
						break;
					case 'accessMethodIndex':
						this.model.accessMethod = this.accessMethodList[val].value;
						break;
					case 'emergencyLevelIndex':
						this.model.emergencyLevel = this.emergencyLevelList[val].value;
						break;
					case 'customerServiceIndex':
						this.model.customerServiceName = this.customerServiceList[val].username;
						break;
				}
			},
			addWorkOrderDetail: function () {
				this.model.workOrderDetailList.push({});
			},
			delWorkOrderDetail: function(e) {
				this.model.workOrderDetailList = this.model.workOrderDetailList.filter(f=>{return f!==e});
			},
			btnConfirm: function(e) {
				let year = e.year;
				let month = e.month >= 10 ? e.month : "0" + e.month;
				let day = e.day >= 10 ? e.day : "0" + e.day;
				let hour = e.hour >= 10 ? e.hour : "0" + e.hour;
				let minute = e.minute >= 10 ? e.minute : "0" + e.minute;
				this.declarationTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
				this.model.declarationTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
			},
			openClients:function(){
				this.$refs.popup.open();
			},
			close:function(){
				this.$refs.popup.close();
			},
		  searchClientByName:function(e) {
			 if (e.value) {
				listClient({name:e.value}).then((res) => {
					if (res.data.success) {
						this.clientList = res.data.result.records;
					}
				});
			 }
		  },
		  selectClient:function(e) {
			 if (!e.target.value) {
				 return;
			 }
			 this.model.clientId = e.target.value;
			 getClientById(e.target.value).then((res) => {
				 if (res.data.success) {
					this.dataIndex.contactIndex = null;
					this.dataIndex.deviceNumberIndexList = [];
					this.client = res.data.result;
					this.close();
				 }
			  });
			  getDicList("tb_contact,name,id,client_id="+e.target.value).then((res) => {
				if (res.data.success) {
					this.contactList = res.data.result;
					if (res.data.result.length > 0) {
						this.model.contactId = res.data.result[0].value;
						this.dataIndex.contactIndex = 0;
					}
				}
			  });
			  getDicList("tb_device_number,name,id,client_id="+e.target.value).then((res) => {
				if (res.data.success) {
					this.deviceNumberList = res.data.result;
					this.model.workOrderDetailList = [];
					if (res.data.result.length > 0) {
						this.model.workOrderDetailList.push({deviceNumber:res.data.result[0].value});
						this.dataIndex.deviceNumberIndexList[0] = 0;
					}
				}
			  });
		  },
		  pickerDeviceNumberChange:function(e,index) {
			  this.dataIndex.deviceNumberIndexList.push(e.target.value);
			  this.model.workOrderDetailList[index].deviceNumber = this.deviceNumberList[e.target.value].value;
		  },
		  selectFaultLocations:function(val,index) {
			   this.model.workOrderDetailList[index].faultLocation = val.toString();
		  },
		  uploadPhotoSuccess:function(entityList) {
			  for (var i = 0; i < entityList.length; i++) {
			  	this.photoCommit.push(entityList[i]);
			  }
		  },
		  deletePhotoSuccess:function(entityList) {
			  this.photoCommit.splice()(entityList[i]);
		  },
		  async savePhoto() {
			return new Promise(async (resolve,reject)=>{
				let photoRes = this.photoCommit
				for (let i = 0; i < photoRes.length; i++) {
					let that = this;
					await uni.uploadFile({
						url: that.picRequestURL ,
						filePath: photoRes[i].path,
						name: 'photos',
						success: (res) => {
							let data = JSON.parse(res.data);
							if (data.success) {
								if (i === 0) {
									this.model.annex = data.result;
								} else {
									this.model.annex += ','+data.result;
								}
							}
						},
						complete: () => {
							if (i === photoRes.length-1) {
								resolve();
							}
						}
					})
				}
				if (photoRes.length === 0) {
					this.saveWorkOrder();
				}
			});
		  },
		  saveWorkOrder:function () {
			  let formData = Object.assign({},this.model);
			  addWorkOrder(formData).then((res) => {
			  	if (res.data.success) {
			  		uni.showToast({
			  			title: res.data.message,
			  			icon: 'none'
			  		});
					setTimeout(function (){
						uni.navigateBack({
						})
					},3000);
			  	}
			  });
		  }
	  }
	}
</script>

<style>
	
</style>
