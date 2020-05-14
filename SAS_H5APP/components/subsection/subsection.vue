<template>
	<view>
		<uni-segmented-control class="borderStyle" :current="current" :values="items" @clickItem="onClickItem" style-type="button" active-color="#09a0f7">
		</uni-segmented-control>
		<scroll-view :scroll-top="scrollTop" scroll-y="true" class="scroll-Y" @scrolltoupper="upper" @scrolltolower="lower" @scroll="scroll">
			<card v-for="(item,index) in dataSource" :info="item" :key="index"></card>
			<uni-load-more :status="'loading'" v-show="load"></uni-load-more>
		</scroll-view>
	</view>
</template>

<script>
	import { mapGetters } from 'vuex'
	export default {
		name: 'subSection',
		components: {
			uniSegmentedControl: () => import('@dcloudio/uni-ui/lib/uni-segmented-control/uni-segmented-control.vue'), 
			card: () => import('@/components/card/card.vue'),
			uniLoadMore: () => import('@dcloudio/uni-ui/lib/uni-load-more/uni-load-more.vue')
		},
		data() {
			return {
				current: 0,
				scrollTop: 0,
				old: {
					scrollTop: 0
				},
				load: false,
				pageNo: 1,
				pageSize: 5,
				dataSource: [],
				total: 0
			}
		},
		methods: {
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex;
					this.dataSource = [];
					this.pageNo = 1;
					let obj = {
						userId: this.$store.getters['getUserId'],
						status: this.current+2+"",
						pageNo: this.pageNo,
						pageSize: this.pageSize
					}
					this.loadTicketList(obj)
				}
			},
			upper() {
				// console.log("到顶了");
			},
			lower() {
				// console.log("到底了");
				if (this.dataSource.length != this.total) {
					this.load = true;
					this.pageNo += 1;
					let obj = {
						userId: this.$store.getters['getUserId'],
						status: this.current+2+"",
						pageNo: this.pageNo,
						pageSize: this.pageSize
					}
					this.loadTicketList(obj);
				}
			},
			scroll() {
				// console.log("滚动了");
			},
			loadTicketList(obj) {
				this.$store.dispatch('workOrder/GetDataList', obj).then(res=>{
					this.total = res.total
					let result = res.list
					result.forEach(item => {
						this.dataSource.push(item)
						this.load = false;
					})
					const dataList = this.dataSource.slice(0);
					this.$store.commit('workOrder/setTicketList', dataList)
				});
			}
		},
		computed:{
			items() {
				let item = this.$store.getters['dic/getStatusList'];
				let items = []
				item.forEach((list) => {
					items.push(list.text) 
				})
				items.shift();
				items.pop();
				// result = result.splice(items.length-1, 1);
				return items
			}
		},
		created() {
			let obj = {
				userId: this.$store.getters['getUserId'],
				status: this.current+2+"",
				pageNo: this.pageNo,
				pageSize: this.pageSize
			}
			this.loadTicketList(obj);
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
