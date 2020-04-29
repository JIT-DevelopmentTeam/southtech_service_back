<script>
	export default {
		data() {
			return {
				
			};
		},
		created() {
			this.isOauth();
		},
		methods: {
			isOauth(){
				var appid = this.$appId;
				var uri = encodeURIComponent(this.$oauthUrl);   // 回调接口
				var url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
				                "appid="+appid+"" +
				                "&redirect_uri="+uri+"" +
				                "&response_type=code" +
				                "&scope=snsapi_userinfo" +
				                "&state=STATE" +
				                "&connect_redirect=1"+
				                "#wechat_redirect";
				window.location.replace(url);
			},
		}
	}
</script>
