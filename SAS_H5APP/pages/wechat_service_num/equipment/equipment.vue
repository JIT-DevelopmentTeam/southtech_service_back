<template>
	<view>
		<scroll-view :scroll-top="scrollTop" scroll-y="true" class="scroll-Y" @scrolltoupper="upper" @scrolltolower="lower" @scroll="scroll">
			<list-card :dataList="getContactList"></list-card>
		</scroll-view>
		<view class="gird">
			<button form-type="submit" type="primary" size="mini" @click="btn">故障申报</button>
			<!-- <button form-type="reset" type="default" size="mini" @click="btn1">服务评价</button> -->
			<button form-type="reset" type="warn" size="mini" @click="btn3">投诉建议</button>
		</view>
	</view>
</template>

<script>
	import {ticketEvaluate} from '@/api/serviceNumber.js'
	export default {
		name: 'equipment',
		data() {
			return {
				name:"name",
				scrollTop: 0,
				old: {
					scrollTop: 0
				}
			}
		},
		components: {
			listCard: () => import('./component/listCard.vue')
		},
		methods:{
			btn(){
				/* uni.navigateTo({
					url: '../declare/declare'
				}) */
			},
			btn1(){
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
			getContactList(){
				let item = this.$store.getters['contact/getContactList'];
				return item;
			}
		},
		onLoad() {
			//跳转页面前获取字典列表信息
			this.$store.dispatch('dic/GetRegionList', '片区').then(res=>{
				this.$store.dispatch('dic/GetServiceFaultLocaList', '故障部位')
			})
			this.$store.dispatch('contact/GetDataList')
		}
	}
</script>

<style>
	.gird {
		padding: 10px 0px;
		position: fixed;
		bottom: 0;
	}
	button {
		margin: 0 16px;
	}
	.scroll-Y {
		height: calc(100vh - 54px);
	}
	
	/* iphone X */
	@media only screen and (device-width: 375px) and (device-height: 812px) and (-webkit-device-pixel-ratio: 3) {
		.scroll-Y {
			height: calc(100vh - 54px);
		}
		button {
			margin: 0 19px;
		}
	}
	/* iphone 6~8 */
	@media only screen and (device-width : 375px) and (device-height : 667px) and (-webkit-device-pixel-ratio : 2) {
		.scroll-Y {
			height: calc(100vh - 54px);
		}
		button {
			margin: 0 19px;
		}
	}
	/* iphone 6 plus~8 plus */
	@media only screen and (device-width : 414px) and (device-height : 736px) and (-webkit-device-pixel-ratio : 3) {
		.scroll-Y {
			height: calc(100vh - 54px);
		}
		button {
			margin: 0 19px;
		}
	}
</style>
