module.exports = {
	error: '',
	check: function(obj) {
		for (var i = 0; i < obj.length; i++) {
			switch(obj[i].checkType) {
				case 'String':
				 if (!obj[i].value) {this.error = obj[i].errorMsg; return false;}
				 break
				case 'length':
				 if (!obj[i].value.length) {this.error = obj[i].errorMsg; return false;}
				break
			}
		}
		return true
	}
}