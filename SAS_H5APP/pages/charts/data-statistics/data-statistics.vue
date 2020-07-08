<template>
	<view class="content">
		<section class="Tabs-Page">
			<div class="article">
				<s-tabs class="custom-tabs">
					<s-tab title="功勋榜">
						<all></all>
					</s-tab>
					<s-tab title="我的">
						<my></my>
					</s-tab>
				</s-tabs>
			</div>
		</section>
	</view>
</template>

<script>
	export default {
		name: "dataStatistics",
		data() {
			return {
			}
		},
		components: {
			sTabs: () => import('@/components/s-ui/s-tabs/s-tabs.vue'),
			sTab: () => import('@/components/s-ui/s-tab/s-tab.vue'),
			all: () => import('../component/all.vue'),
			my: () => import('../component/my.vue')
		},
		methods: {
		},
	}
</script>

<style  lang="scss">
 .Tabs-Page {
   .article {
     &:not(:first-child) {
       margin-top: 40rpx;
     }
     .title {
       padding: 20rpx;
       font-size: 28rpx;
       text-align: center;
     }
     .desc {
       padding: 0 40rpx;
       font-size: 26rpx;
     }
   }
   /deep/.s-tabs {
     .s-tab-panel {
       padding: 30rpx 0;
	   background-color: #fff;
     }
   }
   .custom-tabs {
     /deep/.s-tab-nav-view {
       display: flex;
       justify-content: center;
       .s-tab-nav {
         &:not(:first-child) {
           margin-left: 40rpx;
         }
       }
     }
   }
 }
</style>
