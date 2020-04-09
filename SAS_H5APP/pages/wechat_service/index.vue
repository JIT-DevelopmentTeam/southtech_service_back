<template>
	<view>
		<view v-if="checkUser" class="example-body">
			<uni-grid :column="3" :highlight="true" @change="change">
				<uni-grid-item v-for="(item, index) in list" :index="index" :key="index">
					<view class="grid-item-box" style="background-color: #fff;">
						<span :class="['iconfont image',item.icon]" :style="[{color: item.color}]"/>
						<text class="text">{{ item.text }}</text>
					</view>
				</uni-grid-item>
			</uni-grid>
		</view>
	</view>
</template>

<script>
	import { getLocalWxUserInfoByOpenId } from '@/api/wechatapi.js'
	
	export default {
		components: {
			uniGrid: () => import("@dcloudio/uni-ui/lib/uni-grid/uni-grid.vue"),
			uniGridItem: () => import("@dcloudio/uni-ui/lib/uni-grid-item/uni-grid-item.vue")
		},
		data() {
			return {
				checkUser:false,
				url:{
					getWechatUserInfo:'mobile/index/getWxUserInfoByOpenId'
				},
				list: [
					{
						icon: 'icongongdan',
						text: '工单管理',
						color: "#79ddee",
						url: '/pages/wechat_service/workOrder/index'
					},
					{
						icon: 'iconrizhiyichang',
						text: '故障申报',
						color: "#be78f2",
						url: '/pages/failuredeclaration/FailureDeclaration'
					},
					{
						icon: 'iconfuwuqi',
						text: '我的设备',
						color: "#f28f79",
						url: '/pages/wechat_service/mydevice/mydevice'
					},
					{
						icon: 'icongongdan',
						text: '服务评价',
						color: "#79ddee",
						url: '/pages/wechat_service/servicecommentery/servicecommentery'
					},
				]
			}
		},
		methods: {
			change(e) {
				let list = this.list;
				let index = e.detail.index;
				uni.navigateTo({
					url: list[index].url
				})
			}
		},
		onLoad(option) {
			let that = this;
			this.$store.dispatch('GET_WECHAT_OPENID',option.code).then((res) => {
				if (res) {
					let clientId = res.clientId;
					if (clientId) {
						that.checkUser = true;
					} else {
						uni.showModal({
							title:'提示',
							content:'您的账号暂未通过审核,请联系客服!',
							showCancel:false
						})
					}
				} else {
					uni.showModal({
						title:'提示',
						content:'请关注公众号后联系客服审核信息!',
						showCancel:false
					})
				}
			});
		}
	}
</script>

<style>
	@import '../../common/uni-nvue.css';

	.iconfont {
		font-family: "iconfont" !important;
		font-size: 2rem;
		font-style: normal;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
	}
	
	.image {
		width: 2rem;
		height: 3rem;
		margin: 0 auto;
	}

	.text {
		font-size: 26rpx;
		margin-top: 10rpx;
	}

	.example-body {
		/* #ifndef APP-NVUE */
		display: block;
		/* #endif */
	}

	.grid-dynamic-box {
		margin-bottom: 15px;
	}

	.grid-item-box {
		flex: 1;
		// position: relative;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding: 15px 0;
	}

	.grid-dot {
		position: absolute;
		top: 5px;
		right: 15px;
	}

	.swiper {
		height: 420px;
	}
</style>
