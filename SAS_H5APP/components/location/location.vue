<template>
	<view class="sameLine">
		<view v-if="left_right === '左'" class="sameLine">
			<span class="iconfont icondingwei"></span>
		</view>
		<view class="sameLine" :style="{'display': labelStyle.display, 'font-size': labelStyle.fontSize}">{{label}}</view>
		<view v-if="left_right === '右'" class="sameLine">
			<span class="iconfont icondingwei"></span>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'location',
		data() {
			return {}
		},
		components: {
		},
		props: {
			'labelStyle': Object,
			'label': String,
			'left_right': String
		},
		computed: {
		},
		methods: {
		}
	}
</script>

<style>
	.sameLine {
		display: inline;
		margin-right: 35upx;
	}
</style>
