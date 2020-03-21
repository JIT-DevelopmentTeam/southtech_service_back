<template>
	<view class="content">
		<uni-card class="uniCard title" note="true">
			<view class="info">
				<view class="line">
						<view class="sameLine">
							<view class="label sameLine fontsmall bold">
								工单编号：
							</view>
							<view class="label sameLine fontsmall">
								{{getTicket.ticketNum}}
							</view>
						</view>
				</view>
			</view>
			<view class="btn">
				<model-label :modelLabel="formatModel"></model-label>
			</view>
			<template v-slot:footer>
				<view class="line">
						<view class="sameLine">
							<view class="label sameLine fontsmall bold">
								设备编号：
							</view>
							<view class="label sameLine fontsmall">
								{{getTicket.equipmentNum}}
							</view>
						</view>
						<view class="sameLine">
							<view class="label sameLine fontsmall bold">
								申报时间：
							</view>
							<view class="label sameLine fontsmall">
								{{formatTime(getTicket.faulApplyTime,"YYYY-MM-DD")}}
							</view>
						</view>
				</view>
				<view class="line">
						<view class="sameLine">
							<view class="label sameLine fontsmall bold">
								设备名称：
							</view>
							<view class="label sameLine fontsmall">
								{{getTicket.name}}
							</view>
						</view>
				</view>
				<view class="line">
						<view class="sameLine">
							<view class="label sameLine fontsmall bold">
								主联系人：
							</view>
							<view class="label sameLine fontsmall">
								{{getTicket.enginnerName}}
							</view>
						</view>
				</view>
				<view class="line">
						<view class="sameLine">
							<view class="label sameLine fontsmall bold">
								联系方式：
							</view>
							<view class="label sameLine fontsmall">
								{{getTicket.enginnerMobile}}
							</view>
						</view>
						<phone :phoneNum="getTicket.enginnerMobile"></phone>
				</view>
			</template>
		</uni-card>
		<segment-control :ticketId="getTicket.id" :ticketType="getTicket.ticketModelId" :isNavigateTo="false"></segment-control>
	</view>
</template>

<script>
	import {formatDate} from '@/utils/formatDate.js'

	export default {
		name: "mytaskDetail",
		data() {
			return {
				id: '',
				labelStyle: {
					'fontSize': '25rpx',
					'display': 'inline-block'
				},
				left_right: '右'
			}
		},
		components: { 
			uniCard: () => import('@dcloudio/uni-ui/lib/uni-card/uni-card.vue'), 
			location: () => import('@/components/location/location.vue'), 
			phone: () => import('@/components/phone/phone.vue'), 
			segmentControl: () => import('../component/segment.vue'), 
			modelLabel: () => import('@/components/model-label/model-label.vue')
		},
		onLoad(option) {
			this.id = uni.getStorageSync('ticketId');
		},
		computed: {
			getTicket() {
				let ticketList = this.$store.getters['workOrder/getServiceTicketList']
				return ticketList.filter(e => e.ticketId === this.id)[0]
			},
			formatModel() {
				let dic = this.$store.getters['dic/getServiceTypeList']
				return dic.filter(e=>e.key == this.getTicket.ticketModelId)[0].value
			},
			formatTime(dateTime,prop) {
				return (dateTime,prop) =>{
					return formatDate(dateTime,prop)
				}
			}
		},
		methods: {
			
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

	.phone {
		top: 270upx;
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
</style>
