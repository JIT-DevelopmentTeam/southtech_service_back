<template>
	<view class="content">
		<view class="body_view">
			<view class="calendar-content" v-if="showCalendar">
				<view>
					<uni-calendar @change="change" />
				</view>
			</view>
			<view class="calendar_list">
				<uni-section title="日程列表" type="line"></uni-section>
				<uni-list>
					<uni-list-item :show-extra-icon="true" :extra-icon="extraIcon01" :show-arrow="false" title="现场维修" note="2019-12-04" />
					<uni-list-item :show-extra-icon="true" :extra-icon="extraIcon02" :show-arrow="false" title="测试传输" note="2019-12-08" />
				</uni-list>
			</view>
		</view>	
	</view>
</template>

<script>
	import uniSection from '@dcloudio/uni-ui/lib/uni-section/uni-section.vue'
	import uniCalendar from '@dcloudio/uni-ui/lib/uni-calendar/uni-calendar.vue'
	import uniList from '@dcloudio/uni-ui/lib/uni-list/uni-list.vue'
	import uniListItem from '@dcloudio/uni-ui/lib/uni-list-item/uni-list-item.vue'
	
	export default {
		components: {
			uniSection,
			uniCalendar,
			uniList,
			uniListItem
		},
		data() {
			return {
				showCalendar: false,
				extraIcon01: {
					color: '#007aff',
					size: '22',
					type: 'gear-filled'
				},
				extraIcon02: {
					color: '#4cd964',
					size: '22',
					type: 'image'
				}
			}
		},
		onReady() {
			this.$nextTick(() => {
				this.showCalendar = true
			})
		},
		methods: {
			open() {
				this.$refs.calendar.open()
			},
			change(e) {
				console.log('change 返回:', e)
			}
		}
	}
</script>

<style>
	/* 头条小程序组件内不能引入字体 */
	/* #ifdef MP-TOUTIAO */
	@font-face {
		font-family: uniicons;
		font-weight: normal;
		font-style: normal;
		src: url('~@/static/uni.ttf') format('truetype');
	}

	/* #endif */

	/* #ifndef APP-NVUE */
	page {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		background-color: #efeff4;
		min-height: 100%;
		height: auto;
	}

	view {
		font-size: 28rpx;
		line-height: inherit;
	}

	.example {
		padding: 0 30rpx 30rpx;
	}

	.example-info {
		padding: 30rpx;
		color: #3b4144;
		background: #ffffff;
	}

	.example-body {
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: center;
		padding: 0;
		font-size: 14rpx;
		background-color: #ffffff;
	}

	/* #endif */
	.example {
		padding: 0 30rpx;
	}

	.example-info {
		/* #ifndef APP-NVUE */
		display: block;
		/* #endif */
		padding: 30rpx;
		color: #3b4144;
		background-color: #ffffff;
		font-size: 30rpx;
	}

	.example-info-text {
		font-size: 28rpx;
		line-height: 36rpx;
	}


	.example-body {
		flex-direction: column;
		padding: 30rpx;
		background-color: #ffffff;
	}

	.word-btn-white {
		font-size: 18px;
		color: #FFFFFF;
	}

	.word-btn {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
		justify-content: center;
		border-radius: 6px;
		height: 48px;
		margin: 15px;
		background-color: #007AFF;
	}

	.word-btn--hover {
		background-color: #4ca2ff;
	}


	.calendar-button {
		flex: 1;
		font-weight: bold;
		font-size: 32rpx;
	}
</style>