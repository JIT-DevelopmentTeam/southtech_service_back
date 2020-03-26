<template>
	<view class="container">
		<uni-section class="title" :title="(required ? star : '') + title"></uni-section>
		<view class="example-body">
			<slot></slot>
		</view>
	</view>
</template>

<script>
	export default {
		name: "confDiv",
		data() {
			return {
				star: '* '
			}
		},
		components: {
			uniSection: () => import('@dcloudio/uni-ui/lib/uni-section/uni-section.vue')
		},
		props: {
			'title': String,
			required: Boolean
		}
	}
</script>

<style>
	.example-body {
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: center;
		padding: 0;
		font-size: 14rpx;
		background-color: #ffffff;
	}
	
	.example-body {
		flex-direction: column;
		padding: 30rpx;
		background-color: #ffffff;
	}
	
	.example-body {
		/* #ifndef APP-NVUE */
		display: block;
		/* #endif */
	}
	
	.image {
		width: 100rpx;
		height: 100rpx;
		margin: 0 auto;
	}
	
	.text {
		font-size: 26rpx;
		margin-top: 10rpx;
		text-align: center;
	}
	
	.title {
		background-color: #f5f6f8 !important;
		margin-top: 0 !important;
		height: auto !important;
		color: #09a0f7;
	}
</style>
