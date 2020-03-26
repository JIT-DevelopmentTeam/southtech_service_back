<template>
	<view @click="clickItem">
		<uni-grid-item :hor="0" :ver="0" marker="badge" type="error" text="12">
			<!-- <image class="image" src="/static/c1.png" mode="aspectFill" /> -->
			<span :class="['iconfont image',icon]" :style="[{color: colorStyle}]"></span>
			<text class="text">{{ text }}</text>
		</uni-grid-item>
	</view>
</template>

<script>
	export default {
		name: "navIcon",
		data() {
			return {
				
			}
		},
		components: {
			uniGridItem: () => import('@dcloudio/uni-ui/lib/uni-grid-item/uni-grid-item.vue')
		},
		props: {
			'text': String,
			'icon': String,
			'colorStyle': String
		},
		methods: {
			clickItem(e) {
				this.$emit('clickItem')
			}
		}
	}
</script>

<style>
	.iconfont {
		font-family: "iconfont" !important;
		font-size: 2rem;
		font-style: normal;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
	}
	
	.image {
		width: 2rem;
		height: 2rem;
		margin: 0 auto;
	}
	
	.text {
		font-size: 25rpx;
		margin-top: 40rpx;
		text-align: center;
	}
</style>
