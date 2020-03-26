<template>
	<view class="uni-padding-wrap">
		<view class="uni-title">
			<text>故障申报</text>
		</view>
		<form @submit="formSubmit" @reset="formReset">
			<view class="uni-flex uni-column">
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>编号:</view>
					<input type="text" v-model="model.number" class="uni-input" placeholder="编号"/>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>客户</view>
					<picker v-model="model.clientId" mode ="selector" @change="pickerChange($event,'clientIndex');loadContact($event);loadDeviceNumber($event);" :value="dataIndex.clientIndex" :range="clientList" range-key="text">
						<view class="uni-input">{{ clientList[dataIndex.clientIndex] != null ? clientList[dataIndex.clientIndex].text : '' }}</view>
					</picker>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>联系人</view>
					<picker v-model="model.contactId" mode ="selector" @change="pickerChange($event,'contactIndex');" :value="dataIndex.contactIndex" :range="contactList" range-key="text">
						<view class="uni-input">{{ contactList[dataIndex.contactIndex] != null ? contactList[dataIndex.contactIndex].text : '' }}</view>
					</picker>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>接入方式</view>
					<picker v-model="model.accessMethod" mode ="selector" @change="pickerChange($event,'accessMethodIndex');" :value="dataIndex.accessMethodIndex" :range="accessMethodList" range-key="text">
						<view class="uni-input">{{ accessMethodList[dataIndex.accessMethodIndex] != null ? accessMethodList[dataIndex.accessMethodIndex].text : '' }}</view>
					</picker>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>紧急程度</view>
					<picker v-model="model.emergencyLevel" mode ="selector" @change="pickerChange($event,'emergencyLevelIndex');" :value="dataIndex.emergencyLevelIndex" :range="emergencyLevelList" range-key="text">
						<view class="uni-input">{{ emergencyLevelList[dataIndex.emergencyLevelIndex] != null ? emergencyLevelList[dataIndex.emergencyLevelIndex].text : '' }}</view>
					</picker>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>客服</view>
					<picker v-model="model.customerServiceName" mode ="selector" @change="pickerChange($event,'customerServiceIndex');" :value="dataIndex.customerServiceIndex" :range="customerServiceList" range-key="realname">
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
				<!-- <view class="flex-item flex-item-V">
					<view class="title">附件</view>
					<chooseImage v-model="model.annox" :num="9" :size="150" :imageList="imageList" saveStr="annox"/>
				</view> -->
				<view class="uni-title">
					<text>故障明细</text>
				</view>
				<view class="flex-item flex-item-V" style="text-align:right;">
					<button type="primary" size="mini" @click="addWorkOrderDetail">添加</button>
				</view>
				<view class="flex-item flex-item-V">
					<uni-card title="故障明细" note="true" v-for="(workOrderDetail,index) in model.workOrderDetailList">
						<view class="uni-list">
							<view class="uni-list-cell">
								<view class="uni-list-cell-left">
									<span style="color: red;">*</span>设备编号:
								</view>
								<view class="uni-list-cell-db">
									<picker v-model="workOrderDetail.deviceNumber" @change="pickerChange($event,'deviceNumberIndex');" :value="dataIndex.deviceNumberIndex" :range="deviceNumberList" range-key="text">
										<view class="uni-input">{{ deviceNumberList[dataIndex.deviceNumberIndex] != null ? deviceNumberList[dataIndex.deviceNumberIndex].text : '' }}</view>
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
									<checkbox-group>
										<label style="display: block;" v-for="(faultLocation,index) in faultLocationList">
											<checkbox :value="faultLocation.id" v-model="workOrderDetail.faultLocation" checked="true" color="#FFCC33" style="transform:scale(0.7)" />{{faultLocation.text}}
										</label>
									</checkbox-group>
								</view>
							</view>
						</view>
						<view class="uni-list">
							<view class="uni-title uni-common-pl">描述</view>
							<textarea  v-model="workOrderDetail.description" auto-height/>
						</view>
						<template v-slot:footer><button type="warn" size="mini" @click="delWorkOrderDetail(workOrderDetail)">删除</button></template>
					</uni-card>
					<view class="flex-item flex-item-V" style="text-align:center;">
						<button type="primary" form-type="submit">提交</button>
					</view>
				</view>
    		</view>
    	</form>
    </view>
</template>

<script>
	import uniCard from "@dcloudio/uni-ui/lib/uni-card/uni-card.vue"
	import uniCombox from "@dcloudio/uni-ui/lib/uni-combox/uni-combox"
	import timePicker from '@/components/wing-time-selector/wing-time-selector.vue'
	import chooseImage from '@/components/xyz-choose-image/xyz-choose-image.vue'
	import { getDicList,listUserByRoleCode } from '@/api/Ticket.js'
	
	export default {
		name:'FailureDeclaration',
		components:{
			uniCard,
			uniCombox,
			timePicker,
			chooseImage
		},
		async mounted() {
			// 初始化
			let clientId = null;
			await getDicList("tb_client,name,id").then((res) => {
				if (res.data.success) {
					this.clientList = res.data.result;
					if (res.data.result.length > 0) {
						clientId = res.data.result[0].value;
					}
				}
			});
			await getDicList("tb_contact,name,id,client_id="+clientId).then((res) => {
				if (res.data.success) {
					this.contactList = res.data.result;
				}
			});
			getDicList("work_order_access_method").then((res) => {
				if (res.data.success) {
					this.accessMethodList = res.data.result;
				}
			});
			getDicList("work_order_emergency_level").then((res) => {
				if (res.data.success) {
					this.emergencyLevelList = res.data.result;
				}
			});
			listUserByRoleCode("customer_service").then((res) => {
				if (res.data.success) {
					this.customerServiceList = res.data.result;
				}
			});
			await getDicList("tb_device_number,name,id,client_id="+clientId).then((res) => {
				if (res.data.success) {
					this.deviceNumberList = res.data.result;
				}
			});
			getDicList("work_order_detail_fault_location").then((res) => {
				if (res.data.success) {
					this.faultLocationList = res.data.result;
				}
			});
		},
		data() {
			return {
				dataIndex:{
					clientIndex:0,
					contactIndex:0,
					accessMethodIndex:0,
					emergencyLevelIndex:0,
					customerServiceIndex:0,
					deviceNumberIndex:0,
					faultLocationIndex:[0]
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
				model: {
					number:'',
					status:1,
					type:1,
					clientId:null,
					contactId:null,
					accessMethod:null,
					correspondentName:null,
					emergencyLevel:null,
					customerServiceName:null,
					declarationTime:null,
					annox:null,
					workOrderDetailList:[],
				},
			}
		},
		computed: {
			
		},
		methods: {
			formSubmit: function(e) {
				
				console.log(JSON.stringify(this.model));
			},
			formReset: function() {
				
			},
			pickerChange: function(e,val) {
				this.dataIndex[val] = e.target.value
			},
			loadContact: function(e) {
				this.contactList.splice(0,this.contactList.length);
				let clientId = this.clientList[e.target.value].value;
				getDicList("tb_contact,name,id,client_id="+clientId).then((res) => {
					if (res.data.susccess) {
						this.contactList = res.data.result;
					}
				});
			},
			loadDeviceNumber: function (e) {
				this.deviceNumberList.splice(0,this.deviceNumberList.length);
				let clientId = this.clientList[e.target.value].value;
				getDicList("tb_device_number,name,id,client_id="+clientId).then((res) => {
					debugger;
					if (res.data.success) {
						this.deviceNumberList = res.data.result;
					}
				});
			},
			addWorkOrderDetail: function () {
				this.model.workOrderDetailList.push({});
			},
			delWorkOrderDetail: function(e) {
				this.model.workOrderDetailList = this.model.workOrderDetailList.filter(f=>{return f!==e});
			},
			btnConfirm: function(e) {
				let year = e.year;
				let month = e.month > 10 ? e.month : "0" + e.month;
				let day = e.day > 10 ? e.day : "0" + e.day;
				let hour = e.hour > 10 ? e.hour : "0" + e.hour;
				let minute = e.minute > 10 ? e.minute : "0" + e.minute;
				this.declarationTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
				this.model.declarationTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
			},
		}
	}
</script>

<style>
	
</style>
