<template>
	<view>
		<form @submit="formSubmit" @reset="formReset">
			<field label="设备编号: ">
				<label class="label">{{deviceNo}}</label>
			</field>
			<field label="设备名称: ">
				<label class="label">{{name}}</label>
			</field>
			<field label="故障时间: ">
				<label class="label">{{faultApplyTime}}</label>
			</field>
			<field label="项目地址: " :required="true">
				<label class="label"><input class="uni-input" placeholder="填入项目地址" v-model="address"/></label>
			</field>
			<field label="所在片区: " :required="true">
				<view class="inline">
					<fl-picker
						:listArr="pqLocaList"
						:defaultArr = "areaText"
						@click="pqChange"
						:disabled="2 ==1 ? true : false"
						:mess="''"
						open="1" :show="pqShow">
						<view class="label">
							{{areaText}}
						</view>
					</fl-picker>
				</view>
			</field>
			<field label="项目联系人: " :required="true">
				<label class="label"><input class="uni-input" placeholder="填入联系人" v-model="contact"></label>
			</field>
			<field label="联系电话: " :required="true">
				<label class="label"><input class="uni-input" placeholder="填入联系电话"
				 maxlength="11"
				 v-model="telephone" @blur="phone"><span class="validation">{{showTitle}}</span></label>
			</field>
			<field label="故障部位: " :required="true">
				<view class="inline">
					<fl-picker
						:listArr="faultLocaList"
						:defaultArr = "faultLocaDefault"
						type="multiple" 
						@click="priceChange"
						:disabled="2 ==1 ? true : false"
						:mess="''"
						open="1" :show="show">
						<view class="label">
							{{faultLocaDefault}}
						</view>
					</fl-picker>
				</view>
			</field>
			<field label="故障描述: " :required="true">
				<textarea class="inline textarea" :adjust-position='adjust' v-model="memo"></textarea>
			</field>
			<field label="紧急程度: " :required="true">
				<radio-group class="inline" @change="radioChange">
					<label v-for="(item, index) in items" :key="item.value">
						<radio :value="item.value" :checked="index === current" />
						<view class="inline spacing">{{item.name}}</view>
					</label>
				</radio-group>
			</field>
			<field label="照片上传: ">
				<chooseImage :num="9" :size="150" :isSave="false" saveStr="chooseImage" :isClear="hasChooseImg" :imageList="imageList"
				 :photoList.sync="photoList" @uploadPhotoSuccess="uploadPhotoSuccess" @deletePhotoSuccess="deletePhotoSuccess"
				 :photoArr="photoArr" :picRequestRUl="picRequestRUl"/>
			</field>
			<field>
				<view class="gird">
					<button form-type="submit" type="primary" size="mini">提交</button>
					<button form-type="reset" type="warn" size="mini">关闭</button>
				</view>
			</field>
		</form>
	</view>
</template>

<script>
	import {saveMalfunction} from '@/api/serviceNumber.js'
    import {isTime} from '@/utils/moment.js'
	
	var formChecker = require('@/components/form/validate.js')
	export default {
		name:"declare",
		data() {
			return {
				showTitle:'',
				picRequestRUl: '/f/mobile/upload/uploadPicture',  // 请求地址
				show:true,
				pqShow: true,
				adjust: true,
				items: [
					{
						value: '1',
						name: '非常紧急'
					},
					{
						value: '2',
						name: '紧急'
					},
					{
						value: '3',
						name: '一般',
						checked: 'true'
					},
					{
						value: '4',
						name: '不紧急'
					},
				],
				current: 2,   // 紧急程度
				hasChooseImg: '',
				imageList: [],
				photoList: [],
				photoArr: [],
				fileImag:[],
				del_photoId: "",// 删除附件Id
				area:"",  //  所在片区
				areaText:"",  //  所在片区文本
				address:"",
				contact:"",
				telephone:"",  
				name:"",    //名称
				warrantyPeriod: "",  // 是否保质日期
				contactNo: "",  // 编号
				deviceNo:"",   // 设备编号
				arress: "4",    // 接入方式(1 电话 2代报 3信息 4 微信公众号)
				longitude : "",		// 经度
				latitude: "",		// 纬度
				faultType : "",		// 故障类型值
				faultLocaDefault: '',  // 故障类型文本
				memo: "",		// 故障描述
				equipmentNum:"",		// 设备编号
				faultApplyTime: "",		// 故障申报时间
				faultAssignTime: "",		// 派工时间
				enginnerUser:"",		// 工程师
				attachId:""		,// 附件ID
				urgentLevel:"",  // 紧急程度
				
			}
		},
		components: {
			field: () => import('./component/field.vue'),
			flPicker: () => import('@/components/fl-picker/fl-picker.vue'),
			chooseImage: () => import('@/components/xyz-choose-image/xyz-choose-image.vue'),
		},
		methods: {
			formSubmit(e) {
				let params = new FormData();
				params.append("arress",this.arress);// 接入方式(1 电话 2代报 3信息 4 微信公众号)
				params.append("address",this.address);  // 项目地址
				params.append("contact",this.contact);  // 联系人
				params.append("telephone",this.telephone);  // 联系电话
				params.append("area",this.area);  // 所在片区
				params.append("longitude","");// 经度
				params.append("latitude","");// 纬度
				params.append('faultType',this.faultType);// 故障类型值
				params.append('faultTypeName', this.faultLocaDefault);// 故障类型文本
				params.append('memo', this.memo);// 故障描述
				params.append('equipmentNum', this.deviceNo);// 设备编号
				params.append('faultApplyTime', this.faultApplyTime);// 故障申报时间
				params.append('faultAssignTime', '');// 派工时间
				params.append('enginnerUser', "");// 工程师
				params.append('urgentLevel', this.current);// 紧急程度
				params.append('warrantyPeriod', this.warrantyPeriod);// 是否保质期
				var imageInfo = this.fileImag;
				var  imageId = ""; var imageName = ""; var imagePath = "";
				if( imageInfo != null){
					for (var i = 0; i < imageInfo.length; i++) {
						if(imageInfo.length-1 === i){
							imageId = imageId + imageInfo[i].id;
							imageName = imageName + imageInfo[i].fileName;
							imagePath = imagePath + imageInfo[i].path;
						}else{
							imageId = imageId + imageInfo[i].id+",";
							imageName = imageName + imageInfo[i].fileName+",";
							imagePath = imagePath + imageInfo[i].path + ",";
						}
					}
				}
				params.append("attachId",imageId);
				params.append("phototName",imageName);
				params.append("photoPath", imagePath);
				/* let json = {
					arress: this.arress,    
					longitude : "",		
					latitude: "",		
					faultType : this.faultType,		
					faultTypeName : this.faultLocaDefault,		// 故障类型文本
					memo: this.memo,		// 故障描述
					equipmentNum: this.deviceNo,		// 设备编号
					faultApplyTime: this.faultApplyTime,		// 故障申报时间
					faultAssignTime: this.$moment().format('YYYY-MM-DD hh:mm:ss'),		// 派工时间
					enginnerUser:"",		// 工程师
					"photoArr":  this.photoArr,// 附件ID
					"fileImag":  this.fileImag,// 附件ID
					"imageList": this.imageList, // 图片列表
					"photoList" : this.photoList,// 图片信息
					"urgentLevel": this.current // 紧急程度
				};
				 console.log(JSON.parse(JSON.stringify(json))); */
				/*console.log('form发生了submit事件，携带数据为：' + JSON.stringify(params)); */
				var rule = [
					{value: this.address, checkType:'String', errorMsg:'项目地址不能为空'},
					{value: this.areaText, checkType:'String', errorMsg:'所在片区不能为空'},
					{value: this.contact, checkType:'String', errorMsg:'项目联系人不能为空'},
					{value: this.telephone, checkType:'String', errorMsg:'联系电话不能为空'},
					{value: this.faultLocaDefault, checkType:'String', errorMsg:'故障部位不能为空'},
					{value: this.memo, checkType:'String', errorMsg:'故障描述不能为空'}
				]
				var checkRes = formChecker.check(rule)
				if (checkRes) {
					this.onSubmit(params);
				} else {
					uni.showToast({
						title:formChecker.error,
						icon:'none'
					})
				}
				
			},
			formReset(e) {
				console.log('清空数据');
			},
			priceChange(data){
				/* console.log("==返回值==");
				console.log(data);
				console.log(data.indexStr);
				console.log(data.textStr); */
				var  key =data.valueStr;
				if(key === undefined || key ===""){ return;}
				this.faultType =key;
				this.show  =  false;
				this.faultLocaDefault = data.textStr
			},
			pqChange(data){
				var  key =data.valueStr;
				if(key === undefined || key ===""){ return;}
				this.area =key;
				this.pqShow  =  false;
				this.areaText = data.textStr;
			},
			radioChange(evt) {
				for (let i = 0; i < this.items.length; i++) {
					if (this.items[i].value === evt.target.value) {
						this.current = i;
						this.urgentLevel=evt.target.value;
						//console.log(this.current);
						//console.log(this.items[i].value);
						break;
					}
				}
			},
			uploadPhotoSuccess(result, entityList) {
				if(result.statusCode == 200) {
					this.fileImag = entityList;
				}
			},
			deletePhotoSuccess(result, entityList, photoId) {
				if(result.confirm) {
					this.fileImag = entityList;
					this.del_photoId = photoId
				}
			},
			onSubmit(formData){
				saveMalfunction(formData).then(response => {
					console.log(response);
					if (response.status === 200) {
						uni.showToast({
							title: '保存成功',
							duration: 1000,
							mask: true
						})
						uni.navigateBack({
							delta:1
						})
						// var payload = {'id': "", 'name': ""}
						this.$store.dispatch('contact/GetDataList');
					}
				}).catch(error => {
					console.log(error);
				})
			},
			delUploader(id) {
				if (id != '') {
					delUploadFile(id).then(response => {
						if (response.status === 200) {
							console.log('删除成功');
						}
					}).catch(error => {
						console.log(error);
					})
				}
			},
			phone(){
				if (this.telephone === '') {
					this.showTitle = '不可为空';
				} else if (!(/^1[34578]\d{9}$/.test(this.telephone))) {
					this.showTitle = '联系电话格式错误';
					this.telephone = '';
				}else{
					this.showTitle = "";
				}
			}
		},
		onLoad(option){
			let projcet = JSON.parse(option.detailData);
			this.name = projcet.name;
			this.deviceNo = projcet.deviceNo;
			this.name = projcet.name;
			this.warrantyPeriod = this.time(projcet.lastDate);
			this.faultApplyTime = this.$moment().format('YYYY-MM-DD hh:mm:ss');
		},
		computed:{
			faultLocaList() {
				return this.$store.getters['dic/getServiceFaultLocaList'];
			},
			pqLocaList() {
				return this.$store.getters['dic/getRegionList'];
			},
			time(time){
				return time =>{
					return isTime(time);
				}
			}
		}
	}
</script>

<style>
	.label {
		font-size: 32rpx;
		display: inline-flex;
	}
	
	.inline {
		display: inline-block;
		font-size: 30rpx;
	}
	
	.textarea {
		width: 100%;
	}
	
	.spacing {
		margin-right: 20rpx;
	}
	
	.gird {
		display: grid;
		grid-template-columns: 50% 50%;
	}
	
	.validation{
		color: red;
		font-size: 24rpx;
	}
</style>
