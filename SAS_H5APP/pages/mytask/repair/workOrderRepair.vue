<template>
	<view class="content">
		<view class="header">
			<uni-card class="uniCard">
				<view class="info">
					<view class="line">
						<view class="label">{{getTicket.clientName}}</view>
					</view>
					<view class="line">
						<view class="sameLine">
							<view class="label sameLine fontsmall bold">
								工单编号：
							</view>
							<view class="label sameLine fontsmall">
								{{getTicket.number}}
							</view>
						</view>
						<view class="sameLine">
							<view class="label sameLine fontsmall bold">
								分配时间：
							</view>
							<view class="label sameLine fontsmall">
								{{formatDate(getTicket.assignedTime)}}
							</view>
						</view>
					</view>
					<view class="line">
						<view class="sameLine">
							<view class="label sameLine fontsmall bold">
								客户地址：
							</view>
							<location :labelStyle="labelStyle" :label="getTicket.province+getTicket.city+getTicket.area+getTicket.community+getTicket.address"
							 :left_right="left_right"></location>
						</view>
					</view>
				</view>
				<view class="btn">
					<model-label :modelLabel="formatModel"></model-label>
				</view>
			</uni-card>
		</view>

		<form @submit="formSubmit" @reset="formReset">
			<view class="context">
				<conf-div title="工作要求:">
					<span class="label">{{stage.jobDescription}}</span>
				</conf-div>
				<!-- <conf-div title="当前所在位置:">
					<location :labelStyle="labelStyle" :label="getTicket.address" :left_right="left_right"></location>
				</conf-div> -->
				<!-- <conf-div title="同行人员:">
					<view class="big">
						<span class="label user">{{person}}</span>
						<span class="iconfont icontianjiayonghu iconStyle Btn" @click="stageStatus != 1 ? selectUser() : ''"></span>
					</view>
				</conf-div> -->
				<view v-if="formatModel == '工单预约'">
					<conf-div title="预约时间:" :required="required">
						<time-selector showType="yearToMinute" beginDate="1970-01-01" endDate="2030-12-31" beginTime="06:00:00" endTime="23:59:59"
						 @btnConfirm="bindAppointmentConfirm" :isClick="stageStatus ==1 ? true : false">
							<text>当前选择：{{ appointment }}</text>
						</time-selector>
					</conf-div>
				</view>
				<view v-if="stage.checkIn === 'true'">
					<conf-div title="签到时间:" :required="required">
						<time-selector showType="yearToMinute" beginDate="1970-01-01" endDate="2030-12-31" beginTime="06:00:00" endTime="23:59:59"
						 @btnConfirm="bindInTimeConfirm" :isClick="stageStatus ==1 ? true : false">
							<text>当前选择：{{ signInTime }}</text>
						</time-selector>
					</conf-div>
				</view>
				<view v-if="stage.takePicture === 'true'">
					<conf-div title="现场拍照(最多只能上传9张):" :required="required">
						<chooseImage :num="9" :isSave="false" :imageList="imageList" @uploadPhotoSuccess="uploadPhotoSuccess"
						 @deletePhotoSuccess="deletePhotoSuccess" :stageStatus="stageStatus" />
					</conf-div>
				</view>
				<conf-div title="完成情况:">
					<radio-btn :items="completion" @radioChange="comChange" :stageStatus="stageStatus" type="complete"></radio-btn>
				</conf-div>
				<view v-if="formatModel == '故障研判'">
					<conf-div title="故障判断:" :required="required">
						<textarea placeholder="请输入故障判断" v-model="faultJudgement" :disabled="stageStatus ==1 ? true : false" />
						<view class="separator"></view>
						<label class="label operation">操作规程 >></label>
					</conf-div>
					<conf-div title="故障部位:">
						<fl-picker
							:listArr="faultLocaList"
							:defaultArr = "faultLocaDefault"
							type="multiple" 
							@click="priceChange"
							:disabled="stageStatus ==1 ? true : false"
							:mess="''"
							:open="'1'"
							typeStyle="center">
							<view class="label">
								{{faultLocaDefault}}
							</view>
						</fl-picker>
					</conf-div>
				</view>
				<view v-if="stage.costTemplate === 'true'">
					<conf-div title="是否保质期内:">
						<radio-btn :items="yes_no" @radioChange="yes_noChange" :stageStatus="stageStatus" type="isQGP"></radio-btn>
					</conf-div>
					<conf-div title="费用合计(元):">
						<input placeholder="请输入费用合计(元)" type="number" v-model="cost" :disabled="stageStatus ==1 ? true : false"/>
					</conf-div>
				</view>
				<view v-if="stage.attachment === 'true'">
					<conf-div title="附件(最多只能上传9份):" :required="required">
						<Attachment :canUploadFile="true" :showProcess="true" :attachmentList.sync="attachmentList" @uploadSuccess="uploadSuccess" @deleteSuccess="deleteSuccess" :stageStatus="stageStatus"></Attachment>
					</conf-div>
				</view>
				<view v-if="stage.checkOut === 'true'">
					<conf-div title="签出时间:" :required="required">
							<time-selector
							    showType="yearToMinute"
							    beginDate="1970-01-01"
							    endDate="2030-12-31"
							    beginTime="06:00:00"
							    endTime="23:59:59"
							    @btnConfirm="bindOutTimeConfirm" 
								:isClick="stageStatus ==1 ? true : false"
							>
							    <text>当前选择：{{ signOutTime }}</text>
							</time-selector>
					</conf-div>
				</view>
				<view v-if="stageStatus != 1">
					<view class="occupying-box"></view>
				</view>
			</view>
		
			<view v-if="stageStatus != 1 && jurisdiction !== 'view'">
				<view class="bottom">
					<uni-grid :column="3" :show-border="false"  :square="false">
						<uni-grid-item>
							<button class="bottomIcon" @click="" form-type="submit">
								<span class="iconfont icontijiao bottomIcon" style="color: #09a0f7;"></span>
								<text class="text">提交</text>
							</button>
						</uni-grid-item>
						<uni-grid-item>
							<button class="bottomIcon" @click="">
								<span class="iconfont iconzancun bottomIcon" style="color: #999999;"></span>
								<text class="text">暂存</text>
							</button>
						</uni-grid-item>
						<uni-grid-item>
							<button class="bottomIcon" @click="" form-type="reset">
								<span class="iconfont iconfangqi bottomIcon" style="color: #d81e06;"></span>
								<text class="text">放弃</text>
							</button>
						</uni-grid-item>
					</uni-grid>
				</view>
			</view>
		</form>
	</view>
</template>

<script>
	import {format} from '@/utils/formatDate.js'
	import {ticketRepairSave} from '@/api/Ticket.js'
	import {delUploadFile} from '@/api/Ticket.js'
	import * as dd from 'dingtalk-jsapi'
	import {GetJsapiTicket} from '@/api/ddjsapi.js'
	import {getReportById} from '@/api/Ticket.js'
	
	var formChecker = require('@/components/form/validate.js')
	export default {
		name: "mytaskRepair",
		data() {
			return {
				picRequestRUl: this.$IP + '/mobile/upload/uploadPicture',  // 请求地址
				uploadFileUrl: this.$IP + '/mobile/upload/uploadFile',
				jurisdiction: '',/* 可操作权限 */
				reportId: '',
				detailId: '',/* 工单明细id */
				id: '',/* 阶段id */
				ticketId: "",/* 工单id */
				stageStatus: '',/* 阶段完成状态 */
				ticketType: '',/* 工单类型 */
				labelStyle: {
					'fontSize': '25rpx',
					'display': 'inline-block'
				},
				left_right: '右',
				completion: [{
						value: '1',
						name: '完成',
						checked: true
					},
					{
						value: '0',
						name: '继续',
						checked: false
					}
				],
				yes_no: [{
						value: '1',
						name: '是',
						checked: true
					},
					{
						value: '0',
						name: '否',
						checked: false
					}
				],
				attachmentList: [],
				imageList: [],
				stage: {},/* 阶段对象 */
				stageLists: [],/* 阶段列表（VUEX） */
				person: '',/* 同行人员 */
				person_DD_ID: '',/* 同行人员钉钉ID */
				signInTime: '',/* 签到时间 */
				signOutTime: '',/* 签出时间 */
				completeStatus: '',/* 完成情况 */
				isQGP: '',/* 是否保质期内 */
				faultJudgement: '',/* 故障判断 */
				faultLocation: '',/* 故障部位 */
				cost: '',/* 费用合计 */
				faultLocaDefault: '',
				required: true,
				timeStamp: "", //签名的方法的参数
				nonceStr: "",
				signature: "",
				signInLongitude: 0.0,
				signInLatitude: 0.0,
				signOutLongitude: 0.0,
				signOutLatitude: 0.0,
				/* ---------------- */
				photoCommit: [],// 图片上传数组
				photoDel: [],//要删除的图片数组
				appointment: '',// 预约时间
				fileCommit: [],// 文件上传数组
				fileDel: [],// 要删除的文件数组
			}
		},
		components: {
			uniCard: () => import('@dcloudio/uni-ui/lib/uni-card/uni-card.vue'), 
			uniGrid: () => import('@dcloudio/uni-ui/lib/uni-grid/uni-grid.vue'), 
			uniGridItem: () => import('@dcloudio/uni-ui/lib/uni-grid-item/uni-grid-item.vue'), 
			confDiv: () => import('@/components/conf-div/conf-div.vue'), 
			location: () => import('@/components/location/location.vue'), 
			radioBtn: () => import('@/components/radio-btn/radio-btn.vue'),
			chooseImage: () => import('@/components/xyz-choose-image/xyz-choose-image.vue'),
			Attachment: () => import('@/components/jin-attachment/jin-attachment.vue'),
			modelLabel: () => import('@/components/model-label/model-label.vue'),
			flPicker: () => import('@/components/fl-picker/fl-picker.vue'),
			timeSelector: () => import('@/components/wing-time-selector/wing-time-selector.vue')
		},
		onLoad(option) {
			console.log(option);
			this.reportId = option.reportId
			this.detailId = option.detailId
			this.id = option.id
			this.ticketId = option.ticketId
			this.stageStatus = option.stageStatus
			this.ticketType = option.ticketType
			this.jurisdiction = option.jurisdiction
			/**-------阶段配置型显示-------*/
			this.stageLists = this.$store.getters['stage/getStageList']
			this.stage = this.stageLists.filter(e=>e.id === this.id)[0]
			console.log(this.stage);
			/**-------故障部位初始化--------*/
			let detailList = this.$store.getters['workOrder/getTicketDetailList'];
			let detail = detailList.filter(e=>e.detailId === this.detailId)[0];
			this.faultLocation = detail.faultLocation;
			this.initFaultLocation(detail.faultLocation);
			if (this.reportId !== 'null') {
				let params = {
					'userId': this.$store.getters['getUserId'],
					'progressId': this.id,
					'reportId': this.reportId
				}
				getReportById(params).then(res => {
					console.log(res);
					let result = res.data.result;
					if (result.isCompleted === '1' && result.time !== null) {
						let timeArr = result.time.split(",");
						this.signInTime = timeArr[0];
						this.signOutTime = timeArr[1];
					}
					this.completion.forEach(item => {
						item.checked = false;
						if (item.value == result.isCompleted) {
							item.checked = true;
							this.completeStatus = item.value;
						}
					});
					this.faultLocation = result.faultLocation;
					this.initFaultLocation(result.faultLocation);
					this.faultJudgement = result.description;
					this.appointment = result.appointment;
					for (var i = 0; i < result.photoList.length; i++) {
						let path = this.$IP + result.photoList[i].url;
						let obj ={id: result.photoList[i].id, path: path}
						this.imageList.push(obj);
						this.photoCommit.push(obj);
					}
					for (var i = 0; i < result.fileList.length; i++) {
						let path = this.$IP + result.fileList[i].url;
						let obj ={id: result.fileList[i].id, type: 'file', fileName: result.fileList[i].originalFilename}
						this.attachmentList.push(obj);
						this.fileCommit.push(obj);
					}
				})
			}
			
			this.completion.forEach((i) => {
				if (i.checked) {
					this.completeStatus = i.value
				}
			})
		},
		computed: {
			getTicket() {
				let ticketList = this.$store.getters['workOrder/getTicketList']
				return ticketList.filter(e => e.id === this.ticketId)[0]
			},
			formatDate(dateTime) {
				return dateTime =>{
					return format(dateTime)
				}
			},
			formatModel() {
				return this.stageLists.filter(e=>e.id === this.id)[0].name
			},
			faultLocaList() {
				return this.$store.getters['dic/getFaultLocaList']
			}
		},
		methods: {
			formSubmit(e) {
				var rule = []
				if (this.formatModel == '工单预约') {
					var appointmentRule = {value:this.appointment, checkType:'String', errorMsg:'预约时间不能为空'}
					rule.push(appointmentRule)
				}
				if (this.stage.checkIn === 'true') {
					var singinRule = {value:this.signInTime, checkType:'String', errorMsg:'签到时间不能为空'}
					rule.push(singinRule)
				}
				if (this.stage.takePicture === 'true') {
					var photoRule = {value:this.photoCommit, checkType:'length', errorMsg:'必须拍照'}
					rule.push(photoRule)
				}
				if (this.formatModel == '故障研判') {
					var faultJudgementRule = {value:this.faultJudgement, checkType:'String', errorMsg:'故障预判不能为空'}
					rule.push(faultJudgementRule)
				}
				if (this.stage.attachment === 'true') {
					var fileRule = {value:this.fileCommit, checkType:'length', errorMsg:'必须上传附件'}
					rule.push(fileRule)
				}
				if (this.stage.checkOut === 'true') {
					var singoutRule = {value:this.signOutTime, checkType:'String', errorMsg:'签出时间不能为空'}
					rule.push(singoutRule)
				}
				var checkRes = formChecker.check(rule)
				if (checkRes) {
					this.commitInfo();
				} else {
					uni.showToast({
						title:formChecker.error,
						icon:'none'
					})
				}
			},
			formReset(e) {
				uni.navigateBack({
					delta:1
				});
				var payload = {'workOrderId': this.ticketId}
				this.$store.dispatch('workOrder/GetWorkOrderDetail', payload)
				this.$store.dispatch('stage/GetDataList', payload)
			},
			initFaultLocation(valueArr) {
				let faultArr = valueArr.split(",")
				let resultArr = []
				for (var i = 0; i < faultArr.length; i++) {
					for (var j = 0; j < this.faultLocaList.length; j++) {
						if (faultArr[i] === this.faultLocaList[j].value) {
							resultArr.push(this.faultLocaList[j].text);
						}
					}
				}
				this.faultLocaDefault = resultArr.join(",");
			},
			selectUser() {
				console.log('选择人员');
				var params = {
					url: this.$url
				}
				GetJsapiTicket(params).then(res => {
					this.timeStamp = res.data.result.timeStamp;
					this.nonceStr = res.data.result.nonceStr;
					this.signature = res.data.result.signature;
					this.GETDDUSER()
				}).catch(err => {
					
				})
			},
			bindAppointmentConfirm(e) {
				this.appointment = e.key;
			},
			bindInTimeConfirm(e) {
				this.signInTime = e.key;
				let params = {
					url: this.$url
				}
				GetJsapiTicket(params).then(res => {
					this.timeStamp = res.data.result.timeStamp;
					this.nonceStr = res.data.result.nonceStr;
					this.signature = res.data.result.signature;
					this.getLocation('signIn')
				}).catch(err => {
					
				})
			},
			bindOutTimeConfirm(e) {
				this.signOutTime = e.key;
				let params = {
					url: this.$url
				}
				GetJsapiTicket(params).then(res => {
					this.timeStamp = res.data.result.timeStamp;
					this.nonceStr = res.data.result.nonceStr;
					this.signature = res.data.result.signature;
					this.getLocation('signOut')
				}).catch(err => {
					
				})
			},
			getLocation(type) {
				var _this = this;
				_this.DDConfig();
				dd.ready(() => {
					dd.device.geolocation.get({
					    targetAccuracy : 200,
					    coordinate : 1,
					    withReGeocode : true,
					    useCache:true, //默认是true，如果需要频繁获取地理位置，请设置false
					    onSuccess : function(result) {
							if (type === 'signIn') {
								_this.signInLongitude = result.longitude;
								_this.signInLatitude = result.latitude;
							} else {
								_this.signOutLongitude = result.longitude;
								_this.signOutLatitude = result.latitude;
							}
					    },
					    onFail : function(err) {}
					});
				});
			},
			async commitInfo(){
				this.delUploader(this.photoDel.join(","));
				this.delUploader(this.fileDel.join(","));
				let formData = new FormData();
				formData.append("reportId", this.reportId);
				formData.append("progressId", this.id);
				formData.append("detailId", this.detailId);
				formData.append("ticketId", this.ticketId);
				formData.append("userId", this.$store.getters['getUserId']);
				formData.append("checkIn", this.signInTime);
				formData.append("completeStatus", this.completeStatus);
				formData.append("checkOut", this.signOutTime);
				formData.append("signInLongitude", this.signInLongitude);
				formData.append("signInLatitude", this.signInLatitude);
				formData.append("signOutLongitude", this.signOutLongitude);
				formData.append("signOutLatitude", this.signOutLatitude);
				formData.append("faultLocation", this.faultLocation);
				formData.append('faultJudgement', this.faultJudgement);
				formData.append("appointment", this.appointment);
				
				ticketRepairSave(formData).then(res => {
					if (res.status === 200) {
						this.savePhoto(res.data.result);
						this.saveFile(res.data.result);
						uni.showToast({
							title: '保存成功',
							duration: 500,
							mask: true,
							complete: () => {
								uni.navigateBack({
									delta:1
								});
								var payload = {'workOrderId': this.ticketId}
								this.$store.dispatch('workOrder/GetWorkOrderDetail', payload)
								this.$store.dispatch('stage/GetDataList', payload)
							}
						})
					}
				})
			},
			savePhoto(progressReportId) {
				let photoRes = this.photoCommit
				for (var i = 0; i < photoRes.length; i++) {
					let _this = this;
					uni.uploadFile({
						url: _this.picRequestRUl ,
						filePath: photoRes[i].path,
						name: 'photo',
						formData: {
							id : photoRes[i].id,
							progressReportId: progressReportId
						},
						success: (res) => {
							console.log(res);
						}
					})
				}
			},
			saveFile(progressReportId) {
				let fileRes = this.fileCommit;
				for (var i = 0; i < fileRes.length; i++) {
					let _this = this;
					uni.uploadFile({
						url: _this.uploadFileUrl,
						filePath: fileRes[i].path,
						name: 'file',
						formData: {
							id : fileRes[i].id,
							progressReportId: progressReportId
						},
						success: res => {
							console.log(res);
						}
					});
				}
			},
			uploadPhotoSuccess(entityList) {
				this.photoCommit = entityList;
			},
			deletePhotoSuccess(entityList, id) {
				this.photoCommit = entityList;
				this.photoDel.push(id);
			},
			uploadSuccess(entityList) {
				for (var i = 0; i < entityList.length; i++) {
					this.fileCommit.push(entityList[i]);
				}
				console.log(this.fileCommit);
			},
			deleteSuccess(entityList, id) {
				this.fileCommit = entityList;
				this.fileDel.push(id);
			},
			comChange(value) {
				this.completeStatus = value
			},
			yes_noChange(value) {
				this.isQGP = value
			},
			priceChange(data){
				console.log("==返回值==");
				console.log(data);
				console.log(data.indexStr);
				console.log(data.textStr);
				this.faultLocaDefault = data.textStr
				this.faultLocation = data.valueStr
			},
			delUploader(ids) {
				if (ids != '') {
					delUploadFile(ids).then(response => {
						if (response.status === 200) {
							console.log('删除成功');
						}
					}).catch(error => {
						console.log(error);
					})
				}
			},
			GETDDUSER() {
				var _this = this;
				
				_this.DDConfig(); //钉钉配置方法
				
				dd.ready(() => {
					var pickedUsers = []
					pickedUsers.push(_this.person_DD_ID);
					dd.biz.contact.complexPicker({
					    title:"选择同行人员",            //标题
					    corpId:_this.$corpId,              //企业的corpId
					    multiple:false,            //是否多选
					    limitTips:"超出了",          //超过限定人数返回提示
					    maxUsers:1000,            //最大可选人数
					    pickedUsers:pickedUsers,            //已选用户
					    pickedDepartments:[],          //已选部门
					    disabledUsers:[],            //不可选用户
					    disabledDepartments:[],        //不可选部门
					    requiredUsers:[],            //必选用户（不可取消选中状态）
					    requiredDepartments:[],        //必选部门（不可取消选中状态）
					    appId:344468113,              //微应用的Id
					    responseUserOnly:true,        //返回人，或者返回人和部门
					    startWithDepartmentId:0 ,   //仅支持0和-1
					    onSuccess: function(result) {
							_this.person = '';
							_this.person_DD_ID = '';
							for (let i = 0; i < result.users.length; i++) {
								_this.person += result.users[i].name;
								_this.person_DD_ID += result.users[i].emplId;
								if(i != (result.users.length - 1)){
									_this.person += ",";
									_this.person_DD_ID += ",";
								}
							}
					    },
					   onFail : function(err) {
						   uni.showModal({
						   	title: '错误',
						   	content: 'dd error: ' + JSON.stringify(err)
						   })
					   }
					});
				})
				dd.error((error) => {
					uni.showModal({
						title: '错误',
						content: 'dd error: ' + JSON.stringify(error)
					})
				})
			},
			//钉钉config 配置
			DDConfig() {
				var _this = this;

				dd.config({
					agentId: _this.$agentId,
					corpId: _this.$corpId,
					timeStamp: _this.timeStamp,
					nonceStr: _this.nonceStr,
					signature: _this.signature,
					jsApiList: ["biz.contact.complexPicker","device.geolocation.get"]
				});
			}
		}
	}
</script>

<style scoped>
	.uniCard {
		margin: 10upx 0;
	}

	.label {
		font-size: 30upx;
		display: inline-block;
	}

	.sameLine {
		display: inline;
		margin-right: 15upx;
	}

	.iconStyle {
		margin-right: 25upx;
	}

	.line {
		margin-bottom: 15upx;
	}

	.info {
		width: fit-content;
		display: inline-block;
	}

	.btn {
		display: inline-block;
	}

	.mini-btn {
		position: absolute;
		top: 15upx;
		right: 10upx;
		color: #FFFFFF;
		background-color: #09a0f7;
	}

	.right {
		position: absolute;
		right: 10upx;
		border: 1rpx solid #ffe289;
		color: #fec6c6;
		padding: 10upx;
		margin-top: -7upx;
	}
	
	.fontsmall {
		font-size: 25rpx;
	}
	
	.bold {
		font-weight: bold;
	}
	
	.text {
		text-align: center;
		font-size: 20rpx;
		display: block;
	}
	
	.bottom {
		background-color: #FFFFFF;
		width: 100%;
		position: fixed;
		left: 0;
		bottom: 0;
		z-index: 999;
	}
	
	@supports (bottom: env(safe-area-inset-bottom)){
	    body,
	    .bottom{
	        padding-bottom: constant(safe-area-inset-bottom);
	        padding-bottom: env(safe-area-inset-bottom);
	    }
	}
	
	.bottomIcon {
		text-align: center;
		font-size: 60rpx;
		line-height: 1.5;
		background-color: transparent;
	}
	
	uni-button:after {
		border: none !important;
	}
	
	.big {
		width: 100%;
	}
	
	.user {
		width: 90%;
	}
	
	.Btn {
		width: 10%;
	}
	
	.separator {
		border: 1rpx solid #C0C0C0;
		margin: 10rpx 0;
	}
	
	.operation {
		color: #09a0f7;
		margin-left: 500rpx;
	}
	
	textarea {
		width: 100%;
	}
	
	.time {
		display: grid;
		grid-template-columns: 50% 50%;
	}
	
	.occupying-box {
		height: 120px;
		background-color: #f5f6f8;
	}
	
	/* iphone X */
	@media only screen and (device-width: 375px) and (device-height: 812px) and (-webkit-device-pixel-ratio: 3) {
		.occupying-box {
			height: 105px;
			background-color: #f5f6f8;
		}
	}
	/* iphone 6~8 */
	@media only screen and (device-width : 375px) and (device-height : 667px) and (-webkit-device-pixel-ratio : 2) {
		.occupying-box {
			height: 105px;
			background-color: #f5f6f8;
		}
	}
	/* iphone 6 plus~8 plus */
	@media only screen and (device-width : 414px) and (device-height : 736px) and (-webkit-device-pixel-ratio : 3) {
		.occupying-box {
			height: 105px;
			background-color: #f5f6f8;
		}
	}
	
	@media (device-height:480px) and (-webkit-min-device-pixel-ratio:2),(device-height:568px) and (-webkit-min-device-pixel-ratio:2){/* 兼容iphone4/4s5/5 */ 
		.occupying-box {
			height: 95px;
			background-color: #f5f6f8;
		}
	}
	
	@media only screen and (min-device-width:241px) and (max-device-width:360px){/*奇葩安卓机dpr统一为1  Galaxy 5s*/
		.occupying-box {
			height: 95px;
			background-color: #f5f6f8;
		}
	} 
</style>
