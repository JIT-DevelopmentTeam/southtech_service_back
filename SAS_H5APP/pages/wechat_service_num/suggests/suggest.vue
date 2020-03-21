<template>
	<view class="page">
		<form @submit="save" @reset="formReset">
			<text class="example-info">
				尊敬的客户，您好！				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;感谢您对索奥斯一直以来的关心和支持！麻烦您再花上十分钟的时间，对我们的工作给出建议！
			</text>
			<view class="example-body">
				<label class="label">产品满意度：</label>
				<myRate v-on:change="product" />
				<span>{{productScore}}</span>
				<span class="label text-body">{{productRateScoreText}}</span>
			</view>
			<view class="example-body">
				<label class="label">服务满意度：</label>
				<myRate v-on:change="service" />
				<span>{{serviceScore}}</span>
				<span class="label text-body">{{serviceRateScoreText}}</span>
			</view>
			<view class="uni-textarea">
				<textarea  style="border:#EFEFF4 solid;" placeholder="投诉与建议:" v-model="memo"/>
			</view>
			<view class="example-body-btn">
				<button  form-type="submit" type="primary">提交</button>
			</view>
		</form>
	</view>
</template>

<script>
	import {saveSuggest} from '@/api/serviceNumber.js'
	
	var formChecker = require('@/components/form/validate.js')
	export default {
		name:'evaluate',
		data() {
			return {
				productScore: '',
				serviceScore: '',
				proScore:"",
				serScore:"",
				productRateScoreText: '',
				serviceRateScoreText: '',
				rateScoreDesc: ['非常不满意，各方面都很差', '不满意，比较差', '一般满意，还需改善', '比较满意，仍可改善', '非常满意，无可挑剔'],
				memo:"",
			}
		},
		components: {
			modelLabel: () => import('@/components/model-label/model-label.vue'),
			uniRate: () => import('@dcloudio/uni-ui/lib/uni-rate/uni-rate.vue'),
			myRate: () => import('./component/rate-xing.vue')
		},
		methods: {
			product(index) {
				this.productRateScoreText=this.rateScoreDesc[index.value-1]
				this.productScore = index.value + "星";
				this.proScore = index.value;
			},
			service(index) {
				this.serviceRateScoreText = this.rateScoreDesc[index.value-1]
				this.serviceScore = index.value + "星";
				this.serScore = index.value;
			},
			save(){
				let params = new FormData();
				params.append("proposerId","");// 提议者ID
				params.append('productScore',this.proScore);// 产品满意度
				params.append('productScoreLabel', this.productRateScoreText);// 满意度标签
				params.append('serviceScore', this.serScore);// 服务满意度
				params.append('serviceScoreLabel', this.serviceRateScoreText);// 满意度标签
				params.append('memo', this.memo);// 投诉与建议
				var rule = [
					{value: this.proScore, checkType:'String', errorMsg:'产品满意度必选'},
					{value: this.serScore, checkType:'String', errorMsg:'服务满意度必选'},
					{value: this.memo, checkType:'String', errorMsg:'投诉与建议不能为空'}
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
			onSubmit(formData){
				saveSuggest(formData).then(response => {
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
						//this.$store.dispatch('');
					}
				}).catch(error => {
					console.log(error);
				})
			},
		}
	}
</script>

<style>
	page {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		background-color: #FFFFFF;
		min-height: 100%;
		height: auto;
	}
	
	.example-info {
		padding: 30rpx;
		color: #3b4144;
		background: #ffffff;
		/* #ifndef APP-NVUE */
		display: block;
		/* #endif */
		background-color: #ffffff;
		font-size: 26rpx;
		line-height: 62rpx;
	}
	
	.example-body {
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: center;
		padding: 30rpx;
		font-size: 28rpx;
		background-color: #ffffff;
		border:solid #F1F1F1 2upx;
	}
	.example-body-btn {
		margin: 18rpx;
		width: 95%;
		height: 30%;
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
		color: #DD524D;
		font-size: 12px;
	}
	.text-body{
		margin-left: 20rpx;
	}
</style>