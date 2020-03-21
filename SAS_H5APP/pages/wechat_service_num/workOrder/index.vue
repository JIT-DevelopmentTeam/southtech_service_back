<template>
	<view class="content">
		<uni-segmented-control class="borderStyle" :current="current" :values="items" style-type="button" active-color="#09a0f7" @clickItem="onClickItem"></uni-segmented-control>
		<scroll-view :scroll-top="scrollTop" scroll-y="true" class="scroll-Y" @scrolltoupper="upper" @scrolltolower="lower" @scroll="scroll">
			<work-order-list :list="displayList"></work-order-list>
		</scroll-view>
		<view class="gird">
			<button form-type="reset" type="default" size="mini" @click="btnEquipment">我的设备</button>
			<button form-type="submit" type="primary" size="mini" @click="btnDetail">故障详情</button>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'subSection',
		components: {
			uniSegmentedControl: () => import('@dcloudio/uni-ui/lib/uni-segmented-control/uni-segmented-control.vue'), 
			workOrderList: () => import('./component/work-order-list.vue')
		},
		data() {
			return {
				items: ['未完工','已完工','全部'],
				current: 0,
				scrollTop: 0,
				old: {
					scrollTop: 0
				},
			}
		},
		methods: {
			/**
			 * 点击segmentedControl 事件回调
			 */
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex;
				}
			},
			btnEquipment(){
				uni.navigateTo({
					url: '../equipment/equipment'
				})
			},
			btnDetail(){
				/* uni.navigateTo({
					url: '../declare/declare'
				}) */
			},
			btn2(){
				uni.navigateTo({
					url: '../evaluate/evaluate'
				})
			},
			btn3(){
				uni.navigateTo({
					url: '../suggests/suggest'
				})
			},
			upper() {
				// console.log("到顶了");
			},
			lower() {
				// console.log("到底了");
			},
			scroll() {
				// console.log("滚动了");
			},
		},
		computed:{
			displayList(){
				//this.$store.getters.weChatUser
				let item = this.$store.getters['workOrder/getServiceTicketList']
				if (this.current != 2) {
					item = item.filter(e=>e.ticketStatus === this.current + 3)
				}
				return item;
			}
		},
		onLoad(option) {
			console.log(option.openId);
			console.log(option.clientUserId);
			console.log(option.clientId);
			this.$store.dispatch('dic/GetServiceTypeList', '工单类型').then(res=>{
				this.$store.dispatch('workOrder/GetServiceDataList')
			})
		}
	}
</script>

<style scoped>
	.borderStyle {
		margin: 15upx;
	}
	/* iphone X */
	@media only screen and (device-width: 375px) and (device-height: 812px) and (-webkit-device-pixel-ratio: 3) {
		.scroll-Y {
			height: calc(100vh - 29px - 60px);
		}
	}
	/* iphone 6~8 */
	@media only screen and (device-width : 375px) and (device-height : 667px) and (-webkit-device-pixel-ratio : 2) {
		.scroll-Y {
			height: calc(100vh - 29px - 60px);
		}
	}
	/* iphone 6 plus~8 plus */
	@media only screen and (device-width : 414px) and (device-height : 736px) and (-webkit-device-pixel-ratio : 3) {
		.scroll-Y {
			height: calc(100vh - 29px - 60px);
		}
	}
	.scroll-Y {
		height: calc(100vh - 29px - 60px);
	}
	.gird {
		margin: 4px 2px;
		display: flex;
		grid-template-columns: 50% 50%;
	}
</style>
