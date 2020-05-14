<template>
	<view>
		<uni-swipe-action-item :disabled="current != 1 || serviceCommenteryList.length > 0" :options="options" @click="onClick" @change="change(info.id)" >
			<uni-card class="uniCard" @click="goDetail(info.id)">
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
						<location :labelStyle="label" :label="info.province+info.city+info.area+info.community+info.address" :left_right="left_right"></location>
					</view>
				</view>
				<view class="btn">
					<model-label :modelLabel="formatModel"></model-label>
				</view>
			</uni-card>
		</uni-swipe-action-item>
	</view>
</template>

<script>
	import {calculationTime} from '@/utils/moment.js'
	import {format} from '@/utils/formatDate.js'
	import {getServiceCommentList} from '@/api/serviceNumber.js'

	export default {
		name: 'workOrderList',
		data() {
			return {
				workOrderId:'',
				label: {
					'fontSize': '30upx',
					'display': 'inline-block'
				},
				left_right: '左',
				options:[
					{
						text: '服务评价',
						style: {
							backgroundColor: '#FFA500'
						},
						url:"../servicecommentery/servicecommentery"
					}
				],
				serviceCommenteryList: []
			}
		},
		components: {
			uniCard: () => import('@dcloudio/uni-ui/lib/uni-card/uni-card.vue'), 
			location: () => import('@/components/location/location.vue'), 
			phone: () => import('@/components/phone/phone.vue'), 
			modelLabel: () => import('@/components/model-label/model-label.vue'),
			uniSwipeActionItem: () => import('@dcloudio/uni-ui/lib/uni-swipe-action-item/uni-swipe-action-item.vue')
		},
		props: {
			'info': Object,
			'current': Number
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
				let dic = this.$store.getters['dic/getServiceTypeList'];
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
					url: './detail/work-order-detail'
				})
			},
			onClick(e){
				if (e.index == 0) {
					uni.navigateTo({
						url: e.content.url +'?workOrder=' + encodeURIComponent(JSON.stringify(this.info))
					})
				}
			},
			change(id){
				this.workOrderId = id;
			}
		},
		mounted() {
			let params = {
				workOrderId: this.info.id
			}
			getServiceCommentList(params).then(res => {
				this.serviceCommenteryList = res.data.result;
			})
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
