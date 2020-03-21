<template>
	<view class="body_view">
		<form @submit="onSubmit" @reset="formReset">
			<field label="工单编号:">
				<label>{{ticketNum}}</label>
				<model-label :modelLabel="formatModel"></model-label>
			</field>
			<view class="grid">
				<view>
					<field label="编号:">
						<label>{{enginnerNo}}</label>
					</field>
					<field label="电话:">
						<label>{{enginnerMobile}}</label>
					</field>
				</view>
				<view class="imgDiv img_grid">
					<view>
						<image src="../../../static/user.png" class="img"></image>
					</view>
					<view>
						<label>{{enginnerName}}</label>
					</view>
				</view>
			</view>
			<field label="满意度评价:" :required="true">
				<uni-rate :is-fill="isFill" v-on:change="product" :value="rateScore" :disabled="rateScore > 0 ? true : false"></uni-rate>
				<span class="label">{{satisfiedScore}}</span>
				<span class="label text-body">{{satisfiedRateScoreText}}</span>
			</field>
			<view class="uni-textarea">
				<textarea  style="border:#EFEFF4 solid;" placeholder="服务评价:"  v-model="ticketComemt" :disabled="rateScore > 0 ? true : false"/>
			</view>
			<view class="example-body-btn">
				<button class="" form-type="submit" type="primary" :disabled="rateScore > 0 ? true : false">提交</button><!--  -->
			</view>
		</form>
	</view>
</template>

<script>
	import {ticketEvaluate} from '@/api/serviceNumber.js'
	
	var formChecker = require('@/components/form/validate.js')
	export default {
		name:'evaluate',
		data() {
			return {
				isFill: false,
				satisfiedScore: '',
				satisfiedRateScoreText: '',
				rateScoreDesc: ['非常不满意，各方面都很差', '不满意，比较差', '一般满意，还需改善', '比较满意，仍可改善', '非常满意，无可挑剔'],
				rateScore: 0,
				ticketId:"",
				ticketNum:"",
				ticketType:"维修工单",
				ticketModelId:"",
				ticketScore: "",
				ticketComemt: "",
				enginnerNo: "",
				enginnerName:"",
				enginnerMobile : "",
			}
		},
		components: {
			field: () => import('../declare/component/field.vue'),
			modelLabel: () => import('@/components/model-label/model-label.vue'),
			uniRate: () => import('../suggests/component/rate-xing.vue')
		},
		methods:{
			product(index) {
				if(index === undefined || index ===null){return;}
				this.satisfiedRateScoreText = this.rateScoreDesc[index.value - 1]
				this.satisfiedScore = index.value + "星";
				this.ticketScore = index.value;
			},
			onSubmit(){
				if(this.rateScore > 0){return;}
				var  params = new FormData();
				params.append("id",this.ticketId);
				params.append("ticketNum",this.ticketNum);
				params.append("ticketComemt",this.ticketComemt);
				params.append("ticketScore",this.ticketScore);
				var rule = [
					{value: this.ticketScore, checkType:'String', errorMsg:'满意度评价必选'}
				]
				var checkRes = formChecker.check(rule)
				if (checkRes) {
					ticketEvaluate(params).then(response => {
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
				} else {
					uni.showToast({
						title:formChecker.error,
						icon:'none'
					})
				}
			},
			loadData(option){
				if(option.detailData != null){
					this.detail = JSON.parse(option.detailData);
					//console.log(this.detail);
					this.ticketId = this.detail.id;
					this.ticketNum = this.detail.ticketNum;
					this.ticketModelId = this.detail.ticketModelId;
					this.ticketComemt  = this.detail.ticketComemt;
					this.ticketScore = this.detail.ticketScore;
					this.rateScore = this.detail.ticketScore;
					this.satisfiedRateScoreText = this.rateScoreDesc[this.detail.ticketScore - 1];
					this.enginnerNo = this.detail.enginnerNo;
					this.enginnerName = this.detail.enginnerName;
					this.enginnerMobile = this.detail.enginnerMobile;
				}
			}
		},
		computed:{
			formatModel() {
				let dic = this.$store.getters['dic/getTypeList']
				let result
				if (dic != '') {
					result = dic.filter(e=>e.key == this.ticketModelId)[0].value
				} else {
					result = ''
				}
				return result
			}
		},
		onLoad(option){
			this.loadData(option);
		}
	}
</script>

<style>
	.body_view{
		background-color: #FFFFFF;
	}
	.grid {
		display: grid;
		grid-template-columns: 60% 40%;
	}
	
	.img {
		border: 2rpx solid #999999;
		width: 100rpx;
		height: 100rpx;
		border-radius: 100rpx;
		margin-top: 15rpx;
	}
	
	.imgDiv {
		display: table-cell;
		vertical-align: middle;
		text-align: center;
		background-color: #FFFFFF;
	}
	
	.img_grid {
		display: grid;
		grid-template-rows: 70% 30%
	}
	uni-textarea {
	    width: 97.8%;
	    height: 300rpx;
	    display: block;
	    position: relative;
	    font-size: 16px;
	    line-height: normal;
	    margin: 0px 1px;
	}
	.label{
		font-size: 12px;
	}
	.label.text-body{
		color: #DD524D;
		margin-left: 18rpx;
	}
	.example-body-btn {
		margin: 20rpx;
		width: 95%;
		height: 30%;
		bottom:0;
	}
</style>
