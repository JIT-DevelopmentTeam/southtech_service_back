<template>
	<view class="content">
			<uni-swipe-action>
				<view  v-for="(item, index) in dataList" class="box">
					<uni-swipe-action-item :options="options" @click="onClick" @change="change(item)">
						<uni-collapse :accordion="true">
							<uni-collapse-item :title="'设备编号：' + item.deviceNo + '  质保期' + time(item.lastDate)" :show-animation="true">
								<uni-card note="true">
									<label>设备描述：</label>
									<textarea v-text="item.devicesMemo"></textarea>
									<template v-slot:footer>
										<view class="footer-box">
											<view class="gridContent">
												<label>验收日期：{{formatTime(item.gmrq,'YYYY-MM-DD')}}</label>
												<label>质保期至：{{formatTime(item.lastDate,"YYYY-MM-DD")}}</label>
											</view>
										</view>
									</template>
								</uni-card>
							</uni-collapse-item>
						</uni-collapse>
					</uni-swipe-action-item>
				</view>
			</uni-swipe-action>
	</view>
</template>

<script>
	import {formatDate} from '@/utils/formatDate.js'
	import {calculationTime} from '@/utils/moment.js'
	
	export default {
		name: 'listCard',
		props: {
			dataList: {
				type: Array,
				default: []
			}
		},
		data() {
			return {
				detail:[],
				options:[
					{
						text: '故障申报',
						style: {
							backgroundColor: '#007aff'
						}
					}
				],
			}
		},
		components: {
			uniCard: () => import('@dcloudio/uni-ui/lib/uni-card/uni-card.vue'),
			uniCollapse: () => import('@dcloudio/uni-ui/lib/uni-collapse/uni-collapse.vue'),
			uniCollapseItem: () => import('@dcloudio/uni-ui/lib/uni-collapse-item/uni-collapse-item.vue'),
			uniSwipeAction: () => import('@dcloudio/uni-ui/lib/uni-swipe-action/uni-swipe-action.vue'),
			uniSwipeActionItem: () => import('@dcloudio/uni-ui/lib/uni-swipe-action-item/uni-swipe-action-item.vue')
		},
		methods: {
			onClick(e){
				// console.log(e);
				if (e.index == 0) {
					// console.log('当前点击的是第'+e.index+'个按钮，点击内容是'+e.content.text)
					uni.navigateTo({
						url: '../declare/declare?detailData=' + encodeURIComponent(JSON.stringify(this.detail))
					})
				}				
			},
			change(item){
				let  project = {
					"id" : item.id,
					"name"  : item.name,
					"contactNo" : item.contactNo,
					"deviceNo" : item.deviceNo
				}
				 this.detail = project;
			}
		},
		computed:{
			formatTime(dateTime,prop) {
				return (dateTime,prop) =>{
					return formatDate(dateTime,prop)
				}
			},
			time(time){
				return time =>{
					return calculationTime(time);
				}
			}
		}
	}
</script>

<style scoped>
	.uni-card {
		margin: 0 !important;
	}
	uni-textarea {
	    width: 100%;
	    height: 150px;
	    display: block;
	    position: relative;
	    font-size: 16px;
	    line-height: normal;
	}
	.gridContent {
		display: grid;
		grid-template-columns: 50% 50%;
	}
	.box{
		margin: 10rpx 0;
	}
</style>
