<template>
	<view @click="dClick(info.detailId, info.progressId, info.isCompleted)">
		<uni-card class="uniCard">
			<view>
				<view class="inline">设备档案：</view>
				<view class="inline">{{ info.deviceName }}</view>
			</view>
		    <view>
		    	<view class="inline">服务工程师：</view>
		    	<view class="inline">{{info.enginerName}}</view>
		    </view>
			<view class="gird">
				<view>
					<view class="inline">当前阶段：</view>
					<view class="inline">{{info.currentProgress}}</view>
				</view>
				<view>
					<view class="inline">完成状态：</view>
					<view class="inline">{{info.isCompleted}}</view>
				</view>
			</view>
		</uni-card>
	</view>
</template>

<script>
	export default{
		name: 'detailCard',
		components:{
			uniCard: () => import('@dcloudio/uni-ui/lib/uni-card/uni-card.vue'), 
		},
		props: {
			info: {
				type: Object
			},
			ticketId: {
				type: String,
				default () {
					return ""
				}
			},
			ticketType: {
				type: String,
				default () {
					return ""
				}
			},
		},
		data() {
			return {
			}
		},
		created() {
		},
		methods: {
			dClick(detailId, progressId, isCompleted) {
				let stageStatus = 0;
				if(isCompleted !== null && isCompleted === "1") {stageStatus = 1}
				uni.navigateTo({
					url: '../../mytask/repair/workOrderRepair?id=' + progressId + "&ticketId=" + this.ticketId + "&stageStatus=" + stageStatus + "&ticketType=" + this.ticketType + "&detailId=" + detailId
				})
			}
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
