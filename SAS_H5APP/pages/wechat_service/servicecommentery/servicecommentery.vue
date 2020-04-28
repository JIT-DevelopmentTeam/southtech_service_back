<template>
	<view>
		<view class="uni-title">
			<text>服务评分</text>
		</view>
		<uni-list>
			<uni-card v-for="(workOrder,index) in workOrderList" :key="index">
				<uni-list-item title="工单编号:" :showArrow="true" :rightText="workOrder.number"></uni-list-item>
				<uni-list-item title="满意度评分:" :showArrow="false"></uni-list-item>
				<uni-rate max="5" @change="setScore($event.value,index)"></uni-rate>
				<uni-list-item title="服务评价:" :showArrow="false"></uni-list-item>
				<textarea @blur="setEvaluation($event.target.value,index)" auto-height placeholder="请输入服务评价"/>
			</uni-card>
		</uni-list>
		
		<footer class="footer">
			<button type="primary" @click="submit">提交</button>
		</footer>
	</view>
</template>

<script>
	import uniList from "@dcloudio/uni-ui/lib/uni-list/uni-list"
	import uniListItem from "@dcloudio/uni-ui/lib/uni-list-item/uni-list-item"
	import uniRate from '@dcloudio/uni-ui/lib/uni-rate/uni-rate'
	import uniCard from '@dcloudio/uni-ui/lib/uni-card/uni-card'
	import {listWorkOrderByComment,batchSaveServiceCommentery,getClientByOpenId} from '@/api/Ticket.js'
	
	export default {
		components:{
			uniList,
			uniListItem,
			uniCard,
			uniRate
		},
		name:'ServiceCommentery',
		data() {
			return {
				client:{},
				workOrderList:[],
				serviceCommenteryList:[],
				model:{
					evaluation:''
				}
			}
		},
		async onLoad(option) {
			if (option.workOrder) {
				let workOrder = JSON.parse(decodeURIComponent(option.workOrder));
				this.workOrderList.push(workOrder);
			} else {
				this.wechatOpenId = this.$store.getters['getWeChatOpenId'];
				let that = this;
				if (this.wechatOpenId) {
					await getClientByOpenId(this.wechatOpenId).then((res) => {
						if (res.data.success) {
							that.client = res.data.result;
						}
					});
					if (that.client) {
						await listWorkOrderByComment(that.client.id).then((res) => {
							if (res.data.success) {
								that.workOrderList = res.data.result;
							}
						})
					}
				}
			}
		},
		methods: {
			setScore(val,index) {
				if (this.serviceCommenteryList[index]) {
					this.serviceCommenteryList[index].score = val;
				} else {
					this.serviceCommenteryList.push({workOrderId: this.workOrderList[index].id , score:val});
				}
			},
			setEvaluation(val,index) {
				if (this.serviceCommenteryList[index]) {
					this.serviceCommenteryList[index].evaluation = val;
				} else {
					this.serviceCommenteryList.push({workOrderId: this.workOrderList[index].id , evaluation:val});
				}
			},
			submit() {
				if (this.serviceCommenteryList.length === 0) {
					uni.showModal({
						title:'提示',
						content:'请先进行评价!',
						showCancel:false,
						success:function() {
						}
					});
					return;
				}
				let that = this;
				uni.showModal({
					title:'提示',
					content:'您确定要提交服务评价吗?',
					showCancel:true,
					success:function() {
						uni.request({
							header:{
								'content-type':'application/json'
							},
							url:that.$IP+'/mobile/serviceCommentery/batchSave',
							data:JSON.stringify(that.serviceCommenteryList),
							method:'POST',
							success: (res) => {
								if (res.data.success) {
									uni.showToast({
									    title: res.data.message,
									    duration: 2000,
										success:function() {
											setTimeout(function(){
												uni.navigateBack({
												    delta: 2
												});
											},3000);
										}
									});
								}
							}
						});
					}
				});
			}
		}
	}
</script>

<style>
	.footer {
		width: 100%;
		position: fixed;
		bottom: 0;
	}
</style>
