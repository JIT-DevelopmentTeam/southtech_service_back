<template>
	<view>
		<uni-card class="uniCard" note="true" @click="goDetail(info.id)">
			<view class="info">
				<view class="line">
					<view class="label">{{ info.clientName }}</view>
				</view>
				<view class="line">
					<span class="iconfont iconhuizhengongzuoliang iconStyle"></span>
					<view class="label sameLine">联系人：{{ info.contactName }}</view>
					<view class="label sameLine">{{ info.contactPhone }}</view>
				</view>
				<view class="line">
					<location :labelStyle="label" :label="info.address" :left_right="left_right"></location>
				</view>
			</view>
			<view class="btn">
				<model-label :modelLabel="formatModel"></model-label>
				<phone :phoneNum="info.contactPhone"></phone>
			</view>
			<template v-slot:footer>
				<view class="footer-box">
					<view class="label">{{ formatDate(info.assignedTime) }}</view>
					<view class="label right">{{ calTime(formatDate(info.assignedTime)) }}</view>
				</view>
			</template>
		</uni-card>
	</view>
</template>

<script>
	import {calculationTime} from '@/utils/moment.js'
	import {format} from '@/utils/formatDate.js'

	export default {
		name: 'card',
		data() {
			return {
				label: {
					'fontSize': '30upx',
					'display': 'inline-block'
				},
				left_right: '左'
			}
		},
		components: {
			uniCard: () => import('@dcloudio/uni-ui/lib/uni-card/uni-card.vue'), 
			location: () => import('../location/location.vue'), 
			phone: () => import('../phone/phone.vue'), 
			modelLabel: () => import('@/components/model-label/model-label.vue')
		},
		props: {
			'info': Object
		},
		computed: {
			formatDate(dateTime) {
				return dateTime => {
					return format(dateTime)
				}
			},
			calTime(dateTime) {
				return dateTime => {
					return calculationTime(dateTime)
				}
			},
			formatModel() {
				let dic = this.$store.getters['dic/getTypeList']
				let result
				if (dic != '') {
					result = dic.filter(e=>e.value == this.info.type)[0].text
				} else {
					result = ''
				}
				return result
			}
		},
		methods: {
			goDetail(id) {
				uni.setStorageSync('ticketId', id)
				uni.navigateTo({
					url: '../../pages/mytask/detail/workOrderDetail'
				})
			}
		}
	}
</script>

<style scoped>
	.uniCard {
		margin: 10upx !important;
	}

	.label {
		font-size: 30upx;
		display: inline-block;
	}

	.sameLine {
		display: inline;
		margin-right: 35upx;
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

	.phone {
		top: 120upx;
	}

	.right {
		position: absolute;
		right: 10upx;
		border: 1rpx solid #ffe289;
		color: #fec6c6;
		padding: 10upx;
		margin-top: -7upx;
	}
</style>
