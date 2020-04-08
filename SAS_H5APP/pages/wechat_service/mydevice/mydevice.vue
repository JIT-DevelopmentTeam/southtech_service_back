<template>
	<view class="uni-padding-wrap">
		<view class="uni-title">
			<text>我的设备</text>
		</view>
		<view class="flex-item flex-item-V">
			<uni-card title="设备档案" v-for="(deviceNumber,index) in deviceNumberList">
				<view class="uni-list">
					<uni-list>
					    <uni-list-item title="编码" :rightText="deviceNumber.number"></uni-list-item>
					</uni-list>
				</view>
				<view class="uni-list">
					<uni-list>
					    <uni-list-item title="名称" :rightText="deviceNumber.name"></uni-list-item>
					</uni-list>
				</view>
				<view class="uni-list">
					<uni-list>
					    <uni-list-item title="类型" :rightText="deviceNumber.type"></uni-list-item>
					</uni-list>
				</view>
				<view class="uni-list">
					<uni-list>
					    <uni-list-item title="保质期" :rightText="deviceNumber.qgp"></uni-list-item>
					</uni-list>
				</view>
				<view class="uni-list">
					<uni-list>
					    <uni-list-item title="签约日期" :rightText="deviceNumber.signing"></uni-list-item>
					</uni-list>
				</view>
				<view class="uni-list">
					<view class="uni-title uni-common-pl">描述</view>
					<textarea :disabled="true" :value="deviceNumber.description ? deviceNumber.description : ''"/>
				</view>
			</uni-card>
		</view>
	</view>
</template>

<script>
	import uniCard from "@dcloudio/uni-ui/lib/uni-card/uni-card"
	import uniList from "@dcloudio/uni-ui/lib/uni-list/uni-list"
	import uniListItem from "@dcloudio/uni-ui/lib/uni-list-item/uni-list-item"
	import { getClientByOpenId,listDeviceNumberByMainId } from '@/api/Ticket.js'
	

	
	export default {
		name:'MyDevice',
		components:{
			uniCard,
			uniList,
			uniListItem
		},
		data() {
			return {
				wechatOpenId:null,
				client:{},
				deviceNumberList:[]
			}
		},
	 	async mounted() {
			this.wechatOpenId = this.$store.getters['getWeChatUser'];
			if (this.wechatOpenId) {
				await getClientByOpenId(this.wechatOpenId).then((res) => {
					if (res.data.success) {
						this.client = res.data.result;
					} else {
						uni.showModal({
							title:'提示',
							content:'您的账户尚未通过审核,请联系工作人员!',
							showCancel:false,
							success:function() {
								window.location.reload();
							}
						});
						return;
					}
				});
				await listDeviceNumberByMainId({clientId:this.client.id}).then((res) => {
					if (res.data.success) {
						this.deviceNumberList = res.data.result;
					}
				});
			}
		},
		methods: {
			
		}
	}
</script>

<style>

</style>
