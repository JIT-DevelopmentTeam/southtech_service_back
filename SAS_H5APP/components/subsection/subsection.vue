<template>
	<view>
		<uni-segmented-control class="borderStyle" :current="current" :values="items" @clickItem="onClickItem" style-type="button" active-color="#09a0f7">
		</uni-segmented-control>
		<scroll-view :scroll-top="scrollTop" scroll-y="true" class="scroll-Y" @scrolltoupper="upper" @scrolltolower="lower" @scroll="scroll">
			<card v-for="(item,index) in displayList" :info="item" :key="index"></card>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		name: 'subSection',
		components: {
			uniSegmentedControl: () => import('@dcloudio/uni-ui/lib/uni-segmented-control/uni-segmented-control.vue'), 
			card: () => import('@/components/card/card.vue')
		},
		data() {
			return {
				current: 0,
				scrollTop: 0,
				old: {
					scrollTop: 0
				}
			}
		},
		methods: {
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex;
				}
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
				let item = this.$store.getters['workOrder/getTicketList']
				return item.filter(e=>e.ticketStatus===this.current + 2)
			},
			items() {
				let item = this.$store.getters['dic/getStatusList']
				let items = []
				item.forEach((list) => {
					items.push(list.name) 
				})
				return items.splice(1)
			}
		},
		beforeMount() {
			this.$store.dispatch('workOrder/GetDataList', this.$store.getters['getUserId']);
		}
	}
</script>

<style scoped>
	.borderStyle {
		margin: 15upx;
	}
	.borderStyle /deep/ .segmented-control__text {
		font-size: 0.6rem !important;
	}
	/* iphone X */
	@media only screen and (device-width: 375px) and (device-height: 812px) and (-webkit-device-pixel-ratio: 3) {
		.scroll-Y {
			height: calc(100vh - 180px - 36px - 100px);
		}
	}
	/* iphone 6~8 */
	@media only screen and (device-width : 375px) and (device-height : 667px) and (-webkit-device-pixel-ratio : 2) {
		.scroll-Y {
			height: calc(100vh - 180px - 36px - 70px);
		}
	}
	/* iphone 6 plus~8 plus */
	@media only screen and (device-width : 414px) and (device-height : 736px) and (-webkit-device-pixel-ratio : 3) {
		.scroll-Y {
			height: calc(100vh - 180px - 36px - 70px);
		}
	}
</style>
