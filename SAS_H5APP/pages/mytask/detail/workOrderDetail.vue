<template>
	<view class="content">
		<uni-card class="uniCard" note="true">
			<view class="info">
				<view class="line">
					<view class="label">{{getTicket.clientName}}</view>
				</view>
				<view class="line">
					<view class="sameLine">
						<view class="label sameLine fontsmall bold">
							客户地址：
						</view>
						<location :labelStyle="labelStyle" :label="getTicket.address" :long="getTicket.longitude" :lati="getTicket.latitude" :left_right="left_right"></location>
					</view>
				</view>
				<view class="line">
					<view class="sameLine">
						<view class="label sameLine fontsmall bold">
							联系人：
						</view>
						<view class="label sameLine fontsmall">
							{{getTicket.contactName}}
						</view>
						<view class="label sameLine fontsmall">
							{{getTicket.contactPhone}}
						</view>
					</view>
					<phone :phoneNum="getTicket.contactPhone"></phone>
				</view>
			</view>
			<view class="btn">
				<model-label :modelLabel="formatModel"></model-label>
			</view>
			<template v-slot:footer>
				<view class="line">
					<view class="label sameLine fontsmall bold">
						工单编号：
					</view>
					<view class="label sameLine fontsmall">
						{{getTicket.number}}
					</view>
				</view>
				<view class="line">
					<view class="label sameLine fontsmall bold">
						设备名称：
					</view>
					<view class="label sameLine fontsmall">
						{{getTicket.deviceName}}
					</view>
				</view>
				<view class="line">
					<view class="label sameLine fontsmall bold">
						签约日期：
					</view>
					<view class="label sameLine fontsmall">
						{{getTicket.signing}}
					</view>
				</view>
				<view class="line">
					<view class="label sameLine fontsmall bold">
						描述：
					</view>
					<view class="label sameLine fontsmall">
						{{getTicket.description}}
					</view>
				</view>
			</template>
		</uni-card>
		<segment-control :ticketId="getTicket.id" :ticketType="getTicket.type"></segment-control>
	</view>
</template>

<script>
	import {format} from '@/utils/formatDate.js'

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
			segmentControl: () => import('@/components/segment/segment-control.vue'), 
			modelLabel: () => import('@/components/model-label/model-label.vue')
		},
		onLoad(option) {
			this.id = uni.getStorageSync('ticketId')
		},
		computed: {
			getTicket() {
				let ticketList = this.$store.getters['workOrder/getTicketList'];
				return ticketList.filter(e => e.id === this.id)[0]
			},
			formatDate(dateTime) {
				return dateTime =>{
					return format(dateTime)
				}
			},
			formatModel() {
				let dic = this.$store.getters['dic/getTypeList'];
				return dic.filter(e=>e.value == this.getTicket.type)[0].text
			}
		},
		methods: {
			
		}
	}
</script>

<style scoped>
	.uniCard {
		margin: 10upx 0 !important;
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
		top: 100upx;
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
