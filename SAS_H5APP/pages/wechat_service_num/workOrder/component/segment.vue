<template>
    <view>
        <uni-segmented-control 
		:values="items" 
		@clickItem="onClickItem" 
		:stickyTop="108"
		style-type="text" 
		active-color="#09a0f7">
		</uni-segmented-control>
        <view class="content">
			<view v-if="current == 0">
				<step-item :ticketId="ticketId" :ticketType="ticketType" :isNavigateTo="isNavigateTo"></step-item>
			</view>
			<view v-else-if="current == 1">
				
			</view>
			<view v-else>
				
			</view>
        </view>
    </view>
</template>

<script>
	export default {
		components: {
			uniSegmentedControl: () => import('@dcloudio/uni-ui/lib/uni-segmented-control/uni-segmented-control.vue'),
			stepItem: () => import('./swiper.vue')
		},
		props:{
			ticketId:{
				type: String,
				default () {
					return ""
				}
			},
			ticketType: {
				type: Number,
				default () {
					return ""
				}
			},
			isNavigateTo: {
				type: Boolean,
				default () {
					return true
				}
			},
		},
	    data() {
			return {
				items: ['项目阶段','项目团队','联系人'],
				current: 0,
			}
	    },
	    methods: {
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex;
				}
	        }
	    },
		comments:{
			/* dataList(){
				
			} */
		}
	}
</script>

<style lang="less">
.segmented-control {
  display: flex;
  background: #ffffff; 
  align-items: center;
  padding: 20upx;
  border-bottom: 1px solid #f6f6f6;
  height: 24px;
  .segmented-control-item {
    width: 178upx;
    font-size: 32upx;
    color: #999;
  }
  .active {
    color: #333;
  }
}
</style>
