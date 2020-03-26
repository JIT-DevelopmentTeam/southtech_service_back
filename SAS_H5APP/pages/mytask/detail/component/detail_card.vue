<template>
	<uni-card class="uniCard">
		<view>
			<view class="inline">设备档案：</view>
			<view class="inline">佛山何立波_TPG8026*3.8-V</view>
		</view>
	    <view>
	    	<view class="inline">服务工程师：</view>
	    	<view class="inline">莫伟杰</view>
	    </view>
		<view class="gird">
			<view>
				<view class="inline">当前阶段：</view>
				<view class="inline">故障预判</view>
			</view>
			<view>
				<view class="inline">完成状态：</view>
				<view class="inline">完成</view>
			</view>
		</view>
	</uni-card>
</template>

<script>
	export default{
		name: 'detailCard',
		components:{
			uniCard: () => import('@dcloudio/uni-ui/lib/uni-card/uni-card.vue'), 
		},
		props: {
			ticketId: {
				type: String 
			}
		},
		data() {
			return {
				dataSource: []
			}
		},
		created() {
			let params = {
				workOrderId: this.ticketId
			}
			this.$store.dispatch('workOrder/GetWorkOrderDetail', params).then(res => {
				
			})
		}
	}
</script>

<style scoped>
	.uniCard {
		margin: 10upx !important;
	}
	
	.inline {
		display: inline;
	}
	
	.gird {
		display: grid;
		grid-template-columns: 50% 50%
	}
</style>
