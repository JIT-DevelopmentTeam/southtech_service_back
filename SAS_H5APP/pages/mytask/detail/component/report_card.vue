<template>
	<view>
		<view v-for="(item, index) in showList" :key="index">
				<uni-card class="uniCard" @click="dClick(item.detailId, item.progressId, item.isCompleted, item.reportId)">
					<view>
						<view class="inline">设备档案：</view>
						<view class="inline">{{ item.deviceName }}</view>
					</view>
				    <view>
				    	<view class="inline">汇报人：</view>
				    	<view class="inline">{{item.createrName}}</view>
				    </view>
					<view class="gird">
						<view>
							<view class="inline">当前阶段：</view>
							<view class="inline">{{item.currentProgress}}</view>
						</view>
						<view>
							<view class="inline">完成状态：</view>
							<view class="inline">{{item.isCompleted === '1' ? '完成' : '' || item.isCompleted === '0' ? '继续' : ''}}</view>
						</view>
					</view>
					<view v-if="item.currentProgress === '工单预约'">
						<view class="inline">预约时间：</view>
						<view class="inline">{{item.appointment}}</view>
					</view>
				</uni-card>
		</view>
	</view>
</template>

<script>
	export default{
		name: 'reportCard',
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
				dataSource: [],
				userId: this.$store.getters['getUserId']
			}
		},
		computed: {
			showList() {
				let result = this.$store.getters['workOrder/getReportList'];
				return result;
			}
		},
		created() {
			let params = {
				workOrderId: this.ticketId
			}
			this.$store.dispatch('workOrder/GetReportList', params)
		},
		methods: {
			dClick(detailId, progressId, isCompleted, reportId) {
				let stageStatus = 0;
				if(isCompleted !== null && isCompleted === "1") {stageStatus = 1}
				uni.navigateTo({
					url: '../../mytask/repair/workOrderRepair?id=' + progressId + "&ticketId=" + this.ticketId + "&stageStatus=" + stageStatus + "&ticketType=" + this.ticketType + "&detailId=" + detailId + "&reportId=" + reportId + "&jurisdiction=view"
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
