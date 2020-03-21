<template>
	<view>
		<view class="uni-margin-wrap">
			<swiper class="swiper" circular :indicator-dots="indicatorDots" :autoplay="autoplay" :interval="interval" :duration="duration">
				<swiper-item v-for="(item ,index) in imageList" :key="index">
					<view class="swiper-item">
						<image :src="item.content" mode=""></image>
					</view>
				</swiper-item>
			</swiper>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				indicatorDots: true,
				autoplay: true,
				interval: 3000,
				duration: 500,
				imageList: [{
					content: "../../static/images/round/sosuth.jpg"
				}, {
					content: '../../static/images/round/Technical_discussion.jpg'
				}, {          
					content: '../../static/images/round/customer_service.jpg'
				}, {          
					content: '../../static/images/round/equipment.jpg'
				}],
			}
		},
		methods: {
			changeAutoplay(e) {
				this.autoplay = !this.autoplay
			}
		}
	}
</script>

<style>
	.swiper {
		height: 360upx;
	}
	.swiper-item {
		display: block;
		height: 100%;
		line-height: 300upx;
		text-align: center;
	}
	.swiper-item image{
		width: 100%;
		height: 100%;
	}
	.swiper-list {
		margin-top: 40upx;
		margin-bottom: 0;
	}
	
	.uni-common-mt{
		margin-top:60upx;
		position:relative;
	}
	
	.info {
		position: absolute;
		right:20upx;
	}
</style>
